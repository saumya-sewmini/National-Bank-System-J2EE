package lk.sau.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.sau.app.core.model.Account;
import lk.sau.app.core.model.AccountStatus;
import lk.sau.app.core.model.User;
import lk.sau.app.core.service.AccountService;
import lk.sau.app.core.service.UserService;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;

@WebServlet("/customer-register")
public class CustomerRegister extends HttpServlet {

    @EJB
    private UserService userService;

    @EJB
    private AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String contact = request.getParameter("contact");

        String balanceStr = request.getParameter("balance");
        Double balance = Double.parseDouble(balanceStr);

        User user = new User(name, password, email, contact);

        userService.registerUser(user);
        User managedUser = userService.getUserByEmail(email);

        // Create Account
        Account account = new Account();
        account.setUser(managedUser);
        account.setAccountNumber(generateAccountNumber()); // helper
        account.setBalance(balance);
        account.setInterestRate(BigDecimal.valueOf(1));
        account.setStatus(AccountStatus.ACTIVE);

        accountService.createAccount(account);

        System.out.println("User and account created successfully");

        response.sendRedirect(request.getContextPath() + "/admin");
    }

    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis();
    }

}
