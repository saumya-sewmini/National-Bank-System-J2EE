package lk.sau.app.core.service;

import jakarta.ejb.Remote;
import lk.sau.app.core.model.Transaction;

import java.util.List;

@Remote
public interface TransactionService {
    void recordTransaction(Transaction txn);
    List<Transaction> getTransactionsByAccount(String accountNumber);
}
