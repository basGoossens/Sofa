package team2.sofa.sofa.model;

import javax.persistence.Entity;

@Entity
public class PrivateAccount extends Account {


    public PrivateAccount(){
        super();
        super.setBusinessAccount(false);
    }

}
