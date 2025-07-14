package lk.sau.app.core.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "accounts")
@NamedQueries({
        @NamedQuery(
                name = "Account.findByAccountNo",
                query = "select a from Account a where a.accountNumber=:accountNo"
        ),
        @NamedQuery(
                name = "Account.getAccountByEmail",
                query = "select a from Account a where a.user.email =:email"
        ),
        @NamedQuery(
                name = "Account.getAccountStatus",
                query = "select a from Account a where a.status = :status"
        )
})
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "account_number", unique = true)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private Double balance = 0.0;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    private AccountStatus status = AccountStatus.ACTIVE;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime created = LocalDateTime.now();

    public Account() {}

    public Account(User user, String accountNumber, Double balance, BigDecimal interestRate, AccountStatus status, LocalDateTime created) {
        this.user = user;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.interestRate = interestRate;
        this.status = status;
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
