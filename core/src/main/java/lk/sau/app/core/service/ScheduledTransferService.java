package lk.sau.app.core.service;

import lk.sau.app.core.model.Frequency;
import lk.sau.app.core.model.ScheduledTransfer;

import java.util.List;

public interface ScheduledTransferService {
    List<ScheduledTransfer> getScheduledTransfersBySourceAccount(String accountNo);
    void processDueTransfers();
    void addScheduledTransfer(ScheduledTransfer transfer);
    void updateScheduledTransfer(Long id, double amount, Frequency frequency);
    void cancelScheduledTransfer(Long scheduleId);
    List<ScheduledTransfer> getAllScheduledTransfers();
}
