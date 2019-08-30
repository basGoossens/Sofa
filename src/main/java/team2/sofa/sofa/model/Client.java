package team2.sofa.sofa.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Client extends User {

//    Username,Password
    private int id;
    private String username;
    private String password;
    private List<Account> accounts;

    public Client(String firstName, String prefix, String lastName, Address address, String SSN, String email,
                  String telephoneNr, LocalDate birthday, String gender, int Id, String username, String password){
        super(firstName, prefix, lastName, address, SSN, email, telephoneNr, birthday, gender);
        this.id = id;
        this.username = username;
        this.password = password;
        this.accounts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    public void addAccount(Account account){
        accounts.add(account);
    }
}
