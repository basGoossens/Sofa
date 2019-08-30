package team2.sofa.sofa.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue
    private int id;
    private String IBAN;
    private double balance;
    @ManyToMany
    private List<Client> owners;
}
