package team2.sofa.sofa.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client extends User {

    private String username;
    private String password;
    @ManyToMany(mappedBy = "owners")
    private List<Account> accounts;

    public Client() {
        this(0, "", "", "", null, "", "", "", null, "");
    }

    public Client(int id, String firstName, String prefix, String lastName, Address address, String SSN, String email, String telephoneNr, LocalDate birthday, String gender) {
        super(id, firstName, prefix, lastName, address, SSN, email, telephoneNr, birthday, gender);
        this.accounts = new ArrayList<>();
    }

    public Client(int id, String firstName, String prefix, String lastName, Address address, String SSN, String email, String telephoneNr, LocalDate birthday, String gender, String username, String password, List<Account> accounts) {
        super(id, firstName, prefix, lastName, address, SSN, email, telephoneNr, birthday, gender);
        this.username = username;
        this.password = password;
        this.accounts = accounts;
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

    public void addAccount(Account account) {
        this.accounts.add(account);
    }
}
