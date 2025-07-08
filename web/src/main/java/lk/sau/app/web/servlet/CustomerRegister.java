package lk.sau.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.sau.app.core.model.User;
import lk.sau.app.core.service.UserService;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/customer-register")
public class CustomerRegister extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String contact = request.getParameter("contact");

        User user = new User(name, password, email, contact);

        userService.registerUser(user);

        System.out.println("User " + name + " registered successfully");

        response.sendRedirect(request.getContextPath() + "/admin");
    }

}
