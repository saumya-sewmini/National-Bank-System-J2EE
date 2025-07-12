package lk.sau.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.sau.app.core.model.Account;
import lk.sau.app.core.model.UserType;
import lk.sau.app.core.service.AccountService;

import java.io.IOException;

@WebServlet("/customer-login")
public class CustomerLogin extends HttpServlet {

    @Inject
    private SecurityContext securityContext;

    @EJB
    private AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("email: " + email);
        System.out.println("password: " + password);

        AuthenticationParameters parameters = AuthenticationParameters.withParams()
                .credential(new UsernamePasswordCredential(email, password));

        AuthenticationStatus status = securityContext.authenticate(request, response, parameters);

        System.out.println("Auth Status : " + status);

        if (status == AuthenticationStatus.SUCCESS) {
            HttpSession session = request.getSession();
            session.setAttribute("user", email);

            Account account = accountService.getAccountByEmail(email);

            if (account != null) {
                session.setAttribute("accountNumber", account.getAccountNumber());
                System.out.println("Stored Account Number : " + account.getAccountNumber());

                if (account.getUser().getUserType() == UserType.CUSTOMER) {
                    response.sendRedirect(request.getContextPath() + "/customer");
                } else if (account.getUser().getUserType() == UserType.ADMIN) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                }

            }
        }else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }

    }
}
