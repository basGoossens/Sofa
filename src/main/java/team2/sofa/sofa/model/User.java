package team2.sofa.sofa.model;

import java.time.LocalDate;

public abstract class User {

    //Username,Password
    private String firstName;
    private String prefix;
    private String lastName;
    private Address address;
    private String SSN;
    private String email;
    private String telephoneNr;
    private LocalDate birthday;
    private String gender;

    public User(String firstName, String prefix, String lastName, Address address, String SSN, String email,
                String telephoneNr, LocalDate birthday, String gender) {
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
