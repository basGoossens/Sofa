package team2.sofa.sofa.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(generator = "TRANS_SEQ")
    private int id;
    private double amount;
    private String description;
    private LocalDate date;
    @ManyToOne
    @Nullable
    private PrivateAccount creditAccount;
    @ManyToOne
    @Nullable
    private PrivateAccount debitAccount;

    public Transaction(double amount, String description, LocalDate date, PrivateAccount creditAccount, PrivateAccount debitAccount){
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.creditAccount = creditAccount;
        this.debitAccount = debitAccount;
    }

    public Transaction(){
        this(0,"",LocalDate.now(),null,null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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

    public void setCreditAccount(PrivateAccount creditAccount) {
        this.creditAccount = creditAccount;
    }

    public Account getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(PrivateAccount debitAccount) {
        this.debitAccount = debitAccount;
    }
}
