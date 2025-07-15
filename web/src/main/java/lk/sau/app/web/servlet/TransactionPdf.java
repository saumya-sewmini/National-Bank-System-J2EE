package lk.sau.app.web.servlet;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.sau.app.core.model.Transaction;
import lk.sau.app.core.service.TransactionService;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/download-transactions")
public class TransactionPdf extends HttpServlet {
    @EJB
    private TransactionService transactionService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String accountNumber = (String) request.getSession().getAttribute("accountNumber");

        List<Transaction> transactions = transactionService.getTransactionsByAccount(accountNumber);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=transaction_history.pdf");

        try (OutputStream out = response.getOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("Transaction History for Account: " + accountNumber));
            document.add(new Paragraph("Generated on: " + LocalDateTime.now()));
            document.add(Chunk.NEWLINE);

            for (Transaction t : transactions) {
                document.add(new Paragraph(
                        "Type: " + t.getType() +
                                ", Amount: Rs " + t.getAmount() +
                                ", Date: " + t.getTransactionDate() +
                                ", To: " + (t.getDestinationAccount() != null ? t.getDestinationAccount().getAccountNumber() : "N/A")
                ));
            }

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

    }
}
