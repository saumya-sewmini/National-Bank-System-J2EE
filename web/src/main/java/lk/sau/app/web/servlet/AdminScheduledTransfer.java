package lk.sau.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.sau.app.core.model.Account;
import lk.sau.app.core.model.Frequency;
import lk.sau.app.core.model.ScheduleStatus;
import lk.sau.app.core.model.ScheduledTransfer;
import lk.sau.app.core.service.AccountService;
import lk.sau.app.core.service.ScheduledTransferService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/admin/schedule")
public class AdminScheduledTransfer extends HttpServlet {

    @EJB
    private ScheduledTransferService scheduledTransferService;

    @EJB
    private AccountService accountService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ScheduledTransfer> transfers = scheduledTransferService.getAllScheduledTransfers();
        System.out.println("Found Scheduled Transfers: " + transfers.size());

        request.setAttribute("transfers", transfers);

        request.getRequestDispatcher("/admin/scheduled_transfers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            handleAdd(request);
        } else if ("update".equals(action)) {
            handleUpdate(request);
        } else if ("cancel".equals(action)) {
            handleCancel(request);
        }

        response.sendRedirect(request.getContextPath() + "/admin/schedule");

    }

    private void handleAdd(HttpServletRequest request) {
        String sourceNo = request.getParameter("sourceAccount");
        String targetNo = request.getParameter("targetAccount");
        double amount = Double.parseDouble(request.getParameter("amount"));
        Frequency frequency = Frequency.valueOf(request.getParameter("frequency"));
        LocalDate nextDate = LocalDate.parse(request.getParameter("nextExecutionDate"));

        Account source = accountService.getAccountByAccountNo(sourceNo);
        Account target = accountService.getAccountByAccountNo(targetNo);

        if (source != null && target != null) {
            ScheduledTransfer st = new ScheduledTransfer();
            st.setSourceAccount(source);
            st.setTargetAccount(target);
            st.setAmount(amount);
            st.setFrequency(frequency);
            st.setNextExecutionDate(nextDate);
            st.setStatus(ScheduleStatus.ACTIVE);

            scheduledTransferService.addScheduledTransfer(st);
            System.out.println("Scheduled transfer added successfully!");
        } else {
            System.out.println("Source or target account not found!");
        }
    }

    private void handleUpdate(HttpServletRequest request) {
        Long scheduleId = Long.parseLong(request.getParameter("scheduleId"));
        double amount = Double.parseDouble(request.getParameter("amount"));
        Frequency frequency = Frequency.valueOf(request.getParameter("frequency"));

        scheduledTransferService.updateScheduledTransfer(scheduleId, amount, frequency);
        System.out.println("Scheduled transfer updated!");
    }

    private void handleCancel(HttpServletRequest request) {
        Long scheduleId = Long.parseLong(request.getParameter("scheduleId"));
        scheduledTransferService.cancelScheduledTransfer(scheduleId);
        System.out.println("Scheduled transfer cancelled!");
    }
}
