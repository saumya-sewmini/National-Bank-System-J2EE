package lk.sau.app.bank.ejb;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.sau.app.core.model.*;
import lk.sau.app.core.service.InterestService;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class InterestProcessorSessionBean implements InterestService {
    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Override
    @Schedule(hour = "0", minute = "0", second = "0", persistent = false)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void applyDailyInterest() {

        System.out.println("Scheduled interest processor triggered at: " + LocalDateTime.now());

        try {

            List<Account> accounts = em.createNamedQuery("Account.getAccountStatus", Account.class)
                    .setParameter("status", AccountStatus.ACTIVE)
                    .getResultList();

            int count = 0;

            for (Account a : accounts) {
                double interest = 1;

                a.setBalance(a.getBalance() + interest);
                em.merge(a);

                // Save in InterestAccrual
                InterestAccrual accrual = new InterestAccrual();
                accrual.setAccount(a);
                accrual.setAmount(interest);
                accrual.setTransactionDate(LocalDateTime.now());
                em.persist(accrual);

                //Add a transaction log
                Transaction txn = new Transaction();
                txn.setAccount(a);
                txn.setAmount(interest);
                txn.setType(TransactionType.INTEREST);
                txn.setDescription("Daily interest credit");
                txn.setTransactionDate(LocalDateTime.now());
                em.persist(txn);

                count++;
            }

            System.out.println("Daily interest applied to " + count + " accounts");

        }catch (Exception e) {
            System.out.println("Error during scheduled interest processing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Manual trigger (for testing)
    @Override
    public void applyInterestManually() {
        applyDailyInterest(); // reuse logic
    }

}
