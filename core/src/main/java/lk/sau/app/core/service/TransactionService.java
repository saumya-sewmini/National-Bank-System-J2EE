package lk.sau.app.core.service;

import jakarta.ejb.Remote;
import lk.sau.app.core.model.Transaction;

@Remote
public interface TransactionService {
    void recordTransaction(Transaction txn);
}
