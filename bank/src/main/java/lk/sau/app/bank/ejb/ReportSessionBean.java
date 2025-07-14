package lk.sau.app.bank.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lk.sau.app.core.service.ReportService;

@Stateless
public class ReportSessionBean implements ReportService {
    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Override
    public long getTotalAccounts() {
        return (long) em.createQuery("SELECT COUNT(a) FROM Account a").getSingleResult();
    }

    @Override
    public long getTotalTransactions() {
        return (long) em.createQuery("SELECT COUNT(t) FROM Transaction t").getSingleResult();
    }

    @Override
    public long getTotalScheduledTransfers() {
        return (long) em.createQuery("SELECT COUNT(s) FROM ScheduledTransfer s").getSingleResult();
    }

    @Override
    public double getTotalInterestAccrued() {
        Query q = em.createQuery("SELECT SUM(i.amount) FROM InterestAccrual i");
        Double result = (Double) q.getSingleResult();
        return result != null ? result : 0.0;
    }
}
