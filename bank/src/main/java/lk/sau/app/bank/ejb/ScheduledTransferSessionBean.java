package lk.sau.app.bank.ejb;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lk.sau.app.core.model.*;
import lk.sau.app.core.service.ScheduledTransferService;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class ScheduledTransferSessionBean implements ScheduledTransferService {
    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Override
    public List<ScheduledTransfer> getScheduledTransfersBySourceAccount(String accountNo) {
        TypedQuery<ScheduledTransfer> query = em.createNamedQuery("ScheduledTransfer.findBySourceAccount", ScheduledTransfer.class);
        query.setParameter("accountNo", accountNo);
        return query.getResultList();
    }

    @Override
    @Schedule(hour = "0", minute = "0", second = "0", persistent = false)
    public void processDueTransfers() {
        List<ScheduledTransfer> dueTransfers = em.createNamedQuery("ScheduledTransfer.findBySourceAccount", ScheduledTransfer.class)
                .getResultList();

        for (ScheduledTransfer st : dueTransfers) {
            Account source = st.getSourceAccount();
            Account target = st.getTargetAccount();
            double amount = st.getAmount();

            if (source.getBalance() >= amount) {
                source.setBalance(source.getBalance() - amount);
                target.setBalance(target.getBalance() + amount);

                em.merge(target);
                em.merge(source);

                Transaction txn = new Transaction();
                txn.setAccount(source);
                txn.setDestinationAccount(target);
                txn.setType(TransactionType.TRANSFER);
                txn.setAmount(amount);
                txn.setDescription("Scheduled Transfer");
                txn.setTransactionDate(LocalDateTime.now());
                em.persist(txn);

                if (st.getFrequency() == Frequency.MONTHLY){
                    st.setNextExecutionDate(st.getNextExecutionDate().plusMonths(1));
                } else if (st.getFrequency() == Frequency.WEEKLY) {
                    st.setNextExecutionDate(st.getNextExecutionDate().plusWeeks(1));
                } else if (st.getFrequency() == Frequency.DAILY) {
                    st.setNextExecutionDate(st.getNextExecutionDate().plusDays(1));
                }
                em.persist(txn);
            }else {
                System.out.println("Insufficient funds for scheduled transfer ID: " + st.getScheduleId());
            }
        }
        System.out.println("Scheduled transfer processing done: " + dueTransfers.size() + " items.");
    }
}
