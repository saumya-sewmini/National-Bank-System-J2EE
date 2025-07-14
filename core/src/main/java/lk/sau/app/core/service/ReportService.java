package lk.sau.app.core.service;

import jakarta.ejb.Remote;

@Remote
public interface ReportService {
    long getTotalAccounts();
    long getTotalTransactions();
    long getTotalScheduledTransfers();
    double getTotalInterestAccrued();
}
