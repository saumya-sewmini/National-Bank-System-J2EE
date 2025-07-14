package lk.sau.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.sau.app.core.service.InterestService;

import java.io.IOException;

@WebServlet("/admin/test-interest")
public class InterestTest extends HttpServlet {
    @EJB
    private InterestService interestService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        interestService.applyInterestManually();
        response.getWriter().write("<h1>Interest applied manually. Check balances and accrual logs.</h1>");
    }
}
