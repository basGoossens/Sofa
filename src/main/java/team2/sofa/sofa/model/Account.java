package team2.sofa.sofa.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue (generator = "ACC_SEQ")
    private int id;
    private String iban;
    private double balance;
    @ManyToMany
    private List<Client> owners;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public Account(String iban, double balance, List<Transaction> transactions){
        this();
        this.iban = iban;
        this.balance = balance;
        this.transactions = transactions;
    }

    public Account(){
        super();
        this.id = 0;
        this.owners = new ArrayList<>();
        this.transactions = new ArrayList<>();
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
}
