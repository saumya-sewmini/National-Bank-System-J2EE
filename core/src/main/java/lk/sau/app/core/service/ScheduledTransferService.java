package lk.sau.app.core.service;

import lk.sau.app.core.model.ScheduledTransfer;

import java.util.List;

public interface ScheduledTransferService {
    List<ScheduledTransfer> getScheduledTransfersBySourceAccount(String accountNo);
    void processDueTransfers();
}
