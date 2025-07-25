package lk.sau.app.core.service;

import lk.sau.app.core.model.Account;

public interface AccountService {
    void creditToAccount(String accountNo, double amount);
    void debitFromAccount(String accountNo, double amount);
    Account getAccountByEmail(String email);
    Account getAccountByAccountNo(String accountNo);
    void createAccount(Account account);
}
