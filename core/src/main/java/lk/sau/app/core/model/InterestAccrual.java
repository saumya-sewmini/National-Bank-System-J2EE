package lk.sau.app.core.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "interest_accrual")
public class InterestAccrual implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long accrualId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable = false, name = "amount")
    private Double amount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate = LocalDateTime.now();

    public InterestAccrual() {}

    public InterestAccrual(Account account, Double amount, LocalDateTime transactionDate) {
        this.account = account;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Long getAccrualId() {
        return accrualId;
    }

    public void setAccrualId(Long accrualId) {
        this.accrualId = accrualId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
