package lk.sau.app.core.service;

import jakarta.ejb.Remote;

@Remote
public interface InterestService {
    void applyDailyInterest();
    void applyInterestManually();
}
