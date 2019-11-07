package ma.reddit.servlets;

import ma.reddit.DAO.AuthDAO;
import ma.reddit.DAO.UserDAO;
import ma.reddit.entities.Auth;
import ma.reddit.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AuthServlet", urlPatterns={"/login", "/logout"})
public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        AuthDAO authDao = new AuthDAO();

        if(path.equals("/login")){
            String loginRedirect = (String)request.getSession().getAttribute("loginRedirect");
//            once stored on a local variable we don't need it in the session anymore
            request.getSession().removeAttribute("loginRedirect");

            try{
                User user = authDao.check(new Auth(request.getParameter("username"), request.getParameter("password")));
//                non existant user account
                if(user == null){
                    response.sendError(500, "Incorrect username or password");
                    return;
                }
//                existant user account
                request.getSession().setAttribute("user", user);

//                conf redirection
                if(loginRedirect == null){
                    response.sendRedirect("/reddit");
                    return;
                }
                response.sendRedirect(loginRedirect);

                return;

            }catch(SQLException ex){
                response.sendError(500, ex.getMessage());
                return;
            }
        }
        if(path.equals("/logout")){
//            destroy the session
            request.getSession().invalidate();
            response.sendRedirect("/reddit");
            return;
        }

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }
}
