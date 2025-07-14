package lk.sau.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.sau.app.core.service.ReportService;

import java.io.IOException;

@WebServlet("/admin/report")
public class AdminReport extends HttpServlet {
    @EJB
    private ReportService reportService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("totalAccounts", reportService.getTotalAccounts());
        request.setAttribute("totalTransactions", reportService.getTotalTransactions());
        request.setAttribute("totalScheduledTransfers", reportService.getTotalScheduledTransfers());
        request.setAttribute("totalInterestAccrued", reportService.getTotalInterestAccrued());

        request.getRequestDispatcher("/admin/report.jsp").forward(request, response);

    }
}
