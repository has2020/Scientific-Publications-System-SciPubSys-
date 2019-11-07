package ma.reddit.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.reddit.DAO.CommunityDAO;
import ma.reddit.DAO.PostDAO;
import ma.reddit.DAO.UserDAO;
import ma.reddit.entities.Post;
import ma.reddit.entities.FilePost;
import ma.reddit.entities.User;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;

// regex doesn't work in servlet mapping/ even complicated wild cards
@MultipartConfig()
@WebServlet(name="postServlet", urlPatterns={"/posts/*"} ,loadOnStartup=1)
public class PostServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CommunityDAO communityDao = new CommunityDAO();

        try{
            request.setAttribute("communities", new ObjectMapper().writeValueAsString(communityDao.getAll()));
            request.getRequestDispatcher("/WEB-INF/views/post/create.jsp").forward(request, response);
            return;
        }catch(SQLException ex){
            response.sendError(404, ex.getMessage());
            return;
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        PostDAO postDao = new PostDAO();
        User user = (User)request.getSession().getAttribute("user");

        String path = request.getPathInfo();

        if(path.matches("^/[0-9]*/upvote$")){
            try{
                Integer id = Integer.parseInt(path.substring(1, path.length()-7));
                postDao.increment(id);
                response.setContentType("application/json");
                response.getWriter().write("{\"score\":" + postDao.getScore(id) + "}");
                return;
            }catch(SQLException ex){
                response.sendError(500, ex.getMessage());
                return;
            }
        }

        if(path.matches("^/[0-9]*/downvote$")){
            try{
                Integer id = (new Scanner(path)).useDelimiter("/").nextInt();
                postDao.decrement(id);
                response.setContentType("application/json");
                response.getWriter().write("{\"score\":" + postDao.getScore(id) + "}");
                return;
            }catch(SQLException ex){
                response.sendError(500, ex.getMessage());
                return;
            }
        }

        if(path.equals("/text")){
            Post post = new Post();
            post.setTitle(request.getParameter("title"));
            post.setContent(request.getParameter("content"));
            post.setType(1);
            post.setCommunity(request.getParameter("community"));
            post.setUsername(user.getUsername());

            try{
                postDao.create(post);
                response.sendRedirect("/reddit");
                return;
            }catch(SQLException ex){
                response.sendError(404, ex.getMessage());
                return;
            }
        }
        if(path.equals("/link")){
            Post post = new Post();
            post.setTitle(request.getParameter("title"));
            post.setContent(request.getParameter("content"));
            post.setType(4);
            post.setCommunity(request.getParameter("community"));
            post.setUsername(user.getUsername());

            try{
                postDao.create(post);
                response.sendRedirect("/reddit");

                return;
            }catch(SQLException ex){
                response.sendError(404, ex.getMessage());
                return;
            }
        }
        if(path.equals("/image")){
            FilePost post = new FilePost();

            Part image = request.getPart("content");

            try{
                post.setTitle(request.getParameter("title"));
                post.setContent(
                                request.getServletContext().getInitParameter("storage-url")
                                + "/images/"
                                + image.getSubmittedFileName()
                );
                post.setType(2);
                post.setCommunity(request.getParameter("community"));
                post.setUsername(user.getUsername());



                postDao.create(post);
                post.store(
                        image.getInputStream(),
                        image.getSize(),
                        request.getServletContext().getInitParameter("storage-path") + "\\images",
                        image.getSubmittedFileName()
                );
                response.sendRedirect("/reddit");

                return;
            }catch(SQLException ex){
                response.sendError(404, ex.getMessage());
                return;
            }catch(IOException ex){
                response.sendError(404, ex.getMessage());
                return;
            }
        }
        if(path.equals("/video")){
            FilePost post = new FilePost();

            Part image = request.getPart("content");

            try{
                post.setTitle(request.getParameter("title"));
                post.setContent(
                        request.getServletContext().getInitParameter("storage-url")
                                + "/videos/"
                                + image.getSubmittedFileName()
                );
                post.setType(3);
                post.setCommunity(request.getParameter("community"));
                post.setUsername(user.getUsername());



                postDao.create(post);
                post.store(
                        image.getInputStream(),
                        image.getSize(),
                        request.getServletContext().getInitParameter("storage-path") + "\\videos",
                        image.getSubmittedFileName()
                );
                response.sendRedirect("/reddit");

                return;
            }catch(SQLException ex){
                response.sendError(404, ex.getMessage());
                return;
            }catch(IOException ex){
                response.sendError(404, ex.getMessage());
                return;
            }
        }
    }

    public void init(){
        //we can set up a dao factory here
        System.out.println("Post Servlet has been initialized");
    }
}
