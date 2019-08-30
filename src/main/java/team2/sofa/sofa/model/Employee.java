package team2.sofa.sofa.model;

import java.time.LocalDate;

public class Employee extends User {

    private String username;
    private String password;

    public Employee(){
        this(0,"","", "", null, "", "",
                "",null, "" ,"" , "");
    }

    public Employee(int id, String firstName, String prefix, String lastName, Address address,
                    String SSN, String email, String telephoneNr, LocalDate birthday, String gender,
                    String username, String password) {
        super(id, firstName, prefix, lastName, address, SSN, email, telephoneNr, birthday, gender);
        this.username = username;
        this.password = password;
    }
}
