package ma.reddit.servlets;

import ma.reddit.DAO.RegistrationDAO;
import ma.reddit.entities.Registration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RegisterServlet", urlPatterns={"/register"})
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RegistrationDAO registrationDao = new RegistrationDAO();

        Registration registration = new Registration();
        registration.setUsername(request.getParameter("username"));
        registration.setPassword(request.getParameter("password"));
        registration.setEmail(request.getParameter("email"));

        try{
            String error = registrationDao.validate(registration);
            if(error != null){
                response.sendError(404, error);
                return;
            }
            registrationDao.create(registration);
            request.getRequestDispatcher("").forward(request, response);
            return;
        }catch(SQLException ex){
            response.sendError(404, ex.getMessage());
            return;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
    }
}
