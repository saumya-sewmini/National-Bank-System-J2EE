package lk.sau.app.bank.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.sau.app.core.model.Transaction;
import lk.sau.app.core.service.TransactionService;

@Stateless
public class TransactionSessionBean implements TransactionService {
    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Override
    public void recordTransaction(Transaction txn) {
        em.persist(txn);
    }
}
