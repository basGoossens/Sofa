package team2.sofa.sofa.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(generator = "TRANS_SEQ")
    private int id;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    @ManyToOne
    @Nullable
    private Account creditAccount;
    @ManyToOne
    @Nullable
    private Account debitAccount;

    public Transaction(BigDecimal amount, String description, LocalDate date, Account creditAccount, Account debitAccount){
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.creditAccount = creditAccount;
        this.debitAccount = debitAccount;
    }

    public Transaction(BigDecimal amount, String description, @Nullable Account creditAccount, @Nullable Account debitAccount) {
        this (amount,description, LocalDate.now(), creditAccount, debitAccount);
    }

    public Transaction(){
        this(null,"",LocalDate.now(),null,null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Account getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(Account creditAccount) {
        this.creditAccount = creditAccount;
    }

    public Account getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(Account debitAccount) {
        this.debitAccount = debitAccount;
    }
}
