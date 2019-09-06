package team2.sofa.sofa.model;

import javax.persistence.Entity;

@Entity
public class PrivateAccount extends Account {

    private boolean isBusinessAccount;

    public PrivateAccount(){
        super();
        this.isBusinessAccount = false;
    }

    public boolean isBusinessAccount() {
        return isBusinessAccount;
    }

    public void setBusinessAccount(boolean businessAccount) {
        isBusinessAccount = businessAccount;
    }
}
