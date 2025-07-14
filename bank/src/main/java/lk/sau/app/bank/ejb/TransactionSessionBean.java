package lk.sau.app.bank.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.sau.app.core.model.Transaction;
import lk.sau.app.core.service.TransactionService;

import java.util.List;

@Stateless
public class TransactionSessionBean implements TransactionService {
    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Override
    public void recordTransaction(Transaction txn) {
        em.persist(txn);
    }

    @Override
    public List<Transaction> getTransactionsByAccount(String accountNumber) {
        return em.createNamedQuery("Transaction.findByAccountNumber", Transaction.class)
                .setParameter("accountNo", accountNumber)
                .getResultList();
    }
}
