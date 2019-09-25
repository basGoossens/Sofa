package team2.sofa.sofa.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client extends User {


    private String username;
    private String password;
    @ManyToMany(mappedBy = "owners")
    private List<Account> accounts;

//    //attributen om business en private te scheiden -  @Transient geeft aan dat deze niet worden gemapt door Hibernate.
//    @Transient
//    private List<PrivateAccount> privateAccounts;
//    @Transient
//    private List<BusinessAccount> businessAccounts;

    private int totalNumberOfTransactions;

    public Client() {
        this(0, "", "", "", null, "", "", "", null, "" );
    }

    public Client(int id, String firstName, String prefix, String lastName, Address address, String SSN, String email, String telephoneNr, String birthday, String gender) {
        super(id, firstName, prefix, lastName, address, SSN, email, telephoneNr, birthday, gender);
        this.accounts = new ArrayList<>();
        this.totalNumberOfTransactions = calculateTotalNumberOfTransactions();
    }

    public Client(int id, String firstName, String prefix, String lastName, Address address, String SSN, String email, String telephoneNr, String birthday, String gender, String username, String password, List<Account> accounts) {
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

    public int getTotalNumberOfTransactions() {
        return totalNumberOfTransactions;
    }

    public void setTotalNumberOfTransactions(int totalNumberOfTransactions) {
        this.totalNumberOfTransactions = totalNumberOfTransactions;
    }


    /** Methode om het totaal aantal transacties van alle rekeningen van een klant te berekenen */
    private int calculateTotalNumberOfTransactions(){
        Client client = this;
        int totalNumberOfTransactions = 0;
        for (Account a: client.getAccounts()
        ) {totalNumberOfTransactions += a.getTransactions().size();
        }
        return totalNumberOfTransactions;
    }
}
