package team2.sofa.sofa.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Account {

    @Id
    @GeneratedValue (generator = "ACC_SEQ")
    private int id;
    private String iban;
    private double balance;
    @ManyToMany
    private List<Client> owners;
    @ManyToMany (fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    private boolean isBusinessAccount;

    public Account(String iban, double balance, List<Transaction> transactions){
        this();
        this.iban = iban;
        this.balance = balance;
        this.transactions = transactions;
        this.isBusinessAccount = false;
    }

    public Account(){
        super();
        this.id = 0;
        this.owners = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.isBusinessAccount = false;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
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

    public void lowerBalance(double amount){
        this.balance -= amount;
    }
    public void raiseBalance(double amount){
        this.balance += amount;
    }

    public boolean isBusinessAccount() {
        return isBusinessAccount;
    }

    public void setBusinessAccount(boolean businessAccount) {
        isBusinessAccount = businessAccount;
    }
}
