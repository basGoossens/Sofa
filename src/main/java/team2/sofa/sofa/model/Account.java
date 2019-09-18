package team2.sofa.sofa.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Account {

    @Id
    @GeneratedValue (generator = "ACC_SEQ")
    private int id;
    @Column(unique = true)
    private String iban;
    private BigDecimal balance;
    private String pin;
    @ManyToMany
    private List<Client> owners;

    @ManyToMany (fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    private boolean isBusinessAccount;

    public Account(int id, String iban, BigDecimal balance, List<Client> owners, List<Transaction> transactions, boolean isBusinessAccount, String pin) {
        this.id = id;
        this.iban = iban;
        this.balance = balance;
        this.owners = owners;
        this.transactions = transactions;
        this.isBusinessAccount = isBusinessAccount;
    }
    public Account(String iban, BigDecimal balance, List<Transaction> transactions){
        this(0, iban, balance, null, transactions, false);
    }

    public Account (String iban, BigDecimal balance){
        this(0, iban,balance, new ArrayList<>(),new ArrayList<>(),false);
    }

    public Account(){
        this(0,"", null, new ArrayList<>(), new ArrayList<>(), false);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Client> getOwners() {
        return owners;
    }

    public void setOwners(List<Client> owners) {
        this.owners = owners;
    }

    public void addClient(Client client){
        this.owners.add(client);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);
    }

    public void lowerBalance(BigDecimal amount){
        BigDecimal balance = this.balance;
        BigDecimal lower = balance.subtract(amount);
        this.balance = lower;
    }
    public void raiseBalance(BigDecimal amount){
        BigDecimal balance = this.balance;
        BigDecimal higher = balance.add(amount);
        this.balance = higher;
    }

    public boolean isBusinessAccount() {
        return isBusinessAccount;
    }

    public void setBusinessAccount(boolean businessAccount) {
        isBusinessAccount = businessAccount;
    }

    public boolean getIsBusinessAccount(){
        return isBusinessAccount;
    }
}
