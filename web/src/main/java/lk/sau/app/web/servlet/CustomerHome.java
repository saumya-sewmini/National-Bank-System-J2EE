package lk.sau.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.sau.app.core.model.ScheduledTransfer;
import lk.sau.app.core.model.Transaction;
import lk.sau.app.core.service.ScheduledTransferService;
import lk.sau.app.core.service.TransactionService;

import java.io.IOException;
import java.util.List;

@WebServlet("/customer")
public class CustomerHome extends HttpServlet {
    @EJB
    private ScheduledTransferService scheduledTransferService;

    @EJB
    private TransactionService transactionService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = (String) request.getSession().getAttribute("accountNumber");

        System.out.println("Account Number : " + accountNumber);

        List<ScheduledTransfer> scheduledTransfers = scheduledTransferService.getScheduledTransfersBySourceAccount(accountNumber);
        System.out.println("Scheduled Transfers : " + scheduledTransfers.size());
        request.setAttribute("scheduledTransfers", scheduledTransfers);

        List<Transaction> transactions = transactionService.getTransactionsByAccount(accountNumber);
        request.setAttribute("transactions", transactions);

        request.getRequestDispatcher("/customer/index.jsp").forward(request, response);
    }
}
