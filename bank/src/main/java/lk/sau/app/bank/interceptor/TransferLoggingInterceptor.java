package lk.sau.app.bank.interceptor;

import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import lk.sau.app.core.model.Account;
import lk.sau.app.core.service.AccountService;

import javax.naming.InitialContext;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Interceptor
public class TransferLoggingInterceptor {
    private static final String LOG_FILE = "transfer_logs.txt";

    @AroundInvoke
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object logTransfer(InvocationContext ic) throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        System.out.println("Start " + startTime);

        Object result = null;
        Exception error = null;

        try{
            result = ic.proceed();
        }catch(Exception e){
            error = e;
        }

        LocalDateTime endTime = LocalDateTime.now();

        StringBuilder logEntry = new StringBuilder();
        logEntry.append("=== Fund Transfer Log ===\n");
        logEntry.append("Timestamp: ").append(startTime).append("\n");
        logEntry.append("Method: ").append(ic.getMethod().getName()).append("\n");

        Object[] params = ic.getParameters();
        if (params != null && params.length == 3) {
            logEntry.append("Source Account: ").append(params[0]).append("\n");
            logEntry.append("Destination Account: ").append(params[1]).append("\n");
            logEntry.append("Amount: ").append(params[2]).append("\n");
        }

        if (error != null) {
            logEntry.append("Exception: ").append(error.getMessage()).append("\n");
        }
        logEntry.append("-----------------------------\n");

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(LOG_FILE, true)))) {
            out.print(logEntry.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (error != null) {
            throw error; // Rethrow exception after logging
        }

        return result;
    }
}
