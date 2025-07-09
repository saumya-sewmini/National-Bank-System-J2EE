package lk.sau.app.core.service;

import jakarta.ejb.Remote;

@Remote
public interface TransferService {
    void transferAmount(String sourceAccountNo, String destinationAccountNo, double amount);
}
