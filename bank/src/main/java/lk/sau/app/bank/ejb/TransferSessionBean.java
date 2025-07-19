package lk.sau.app.bank.ejb;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.sau.app.bank.interceptor.TransferLoggingInterceptor;
import lk.sau.app.core.model.Account;
import lk.sau.app.core.model.Transaction;
import lk.sau.app.core.model.TransactionType;
import lk.sau.app.core.service.AccountService;
import lk.sau.app.core.service.TransactionService;
import lk.sau.app.core.service.TransferService;

import java.time.LocalDateTime;

@Stateless
public class TransferSessionBean implements TransferService {
    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @EJB
    private AccountService accountService;

    @EJB
    private TransactionService transactionService;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Interceptors({TransferLoggingInterceptor.class})
    public void transferAmount(String sourceAccountNo, String destinationAccountNo, double amount) {
        //load accounts
        Account sourceAccount = em.createNamedQuery("Account.findByAccountNo", Account.class)
                .setParameter("accountNo", sourceAccountNo).getSingleResult();

        Account destinationAccount = em.createNamedQuery("Account.findByAccountNo", Account.class)
                .setParameter("accountNo", destinationAccountNo).getSingleResult();

        // Debit source
        accountService.debitFromAccount(sourceAccountNo, amount);

        // Credit destination
        accountService.creditToAccount(destinationAccountNo, amount);

        //Recode transaction
        Transaction debitTxn = new Transaction();
        debitTxn.setAccount(sourceAccount);
        debitTxn.setTransactionDate(LocalDateTime.now());
        debitTxn.setAmount(amount);
        debitTxn.setType(TransactionType.TRANSFER);
        debitTxn.setDescription("Transfer to " + destinationAccountNo);
        debitTxn.setDestinationAccount(destinationAccount);

        Transaction creditTxn = new Transaction();
        creditTxn.setAccount(destinationAccount);
        creditTxn.setTransactionDate(LocalDateTime.now());
        creditTxn.setAmount(amount);
        creditTxn.setType(TransactionType.DEPOSIT);
        creditTxn.setDescription("Receive from " + sourceAccountNo);
        creditTxn.setDestinationAccount(sourceAccount);

        transactionService.recordTransaction(debitTxn);
        transactionService.recordTransaction(creditTxn);
    }
}
