package team2.sofa.sofa.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class User {

    //Username,Password
    @Id
    @GeneratedValue(generator = "USER_SEQ")
    private int id;
    private String firstName;
    private String prefix;
    private String lastName;
    @ManyToOne
    private Address address;
    private String SSN;
    private String email;
    private String telephoneNr;
    private String birthday;
    private String gender;

    public User() {
        super();
    }

    public User(int id, String firstName, String prefix, String lastName, Address address, String SSN, String email,
                String telephoneNr, String birthday, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.prefix = prefix;
        this.lastName = lastName;
        this.address = address;
        this.SSN = SSN;
        this.email = email;
        this.telephoneNr = telephoneNr;
        this.birthday = birthday;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNr() {
        return telephoneNr;
    }

    public void setTelephoneNr(String telephoneNr) {
        this.telephoneNr = telephoneNr;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public abstract void setUsername(String s);

    public abstract void setPassword(String s);

}
