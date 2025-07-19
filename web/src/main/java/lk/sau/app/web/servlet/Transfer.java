package lk.sau.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.sau.app.core.model.Account;
import lk.sau.app.core.service.TransferService;

import java.io.IOException;

@WebServlet("/transfer")
public class Transfer extends HttpServlet {
    @EJB
    private TransferService transferService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String sourceAccount = (String) request.getSession().getAttribute("accountNumber");
        String destinationAccount = request.getParameter("toAccount");
        double amount = Double.parseDouble(request.getParameter("amount"));

        try {
            transferService.transferAmount(sourceAccount, destinationAccount, amount);
            System.out.println("Transfer successful");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Transfer failed");
        }

        response.sendRedirect(request.getContextPath() + "/customer");

    }
}
