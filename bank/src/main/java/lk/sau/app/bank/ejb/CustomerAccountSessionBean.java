package lk.sau.app.bank.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lk.sau.app.core.model.Account;
import lk.sau.app.core.service.AccountService;

@Stateless
public class CustomerAccountSessionBean implements AccountService {
    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Override
    public void creditToAccount(String accountNo, double amount) {
        try {
            Account account = em.createNamedQuery("Account.findByAccountNo", Account.class)
                    .setParameter("accountNo", accountNo)
                    .getSingleResult();

            if (amount > 0){
                account.setBalance(account.getBalance() + amount);
                em.merge(account);
            }
        }catch (NoResultException e){
            e.printStackTrace();
        }
    }

    @Override
    public void debitFromAccount(String accountNo, double amount) {
        try {
            Account account = em.createNamedQuery("Account.findByAccountNo", Account.class)
                    .setParameter("accountNo", accountNo)
                    .getSingleResult();

            if (account.getBalance() >= amount){
                account.setBalance(account.getBalance() - amount);
                em.merge(account);
            }
        }catch (NoResultException e){
            e.printStackTrace();
        }
    }

    @Override
    public Account getAccountByEmail(String email) {
        return em.createNamedQuery("Account.getAccountByEmail", Account.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
