package team2.sofa.sofa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class pdq {

    @Id
    @GeneratedValue
    private int id;
    private String pdqMachineName;
    private int uniqueNumber;
    @ManyToOne
    private Account coupledAccount;

    public pdq(String pdqMachineName, Account coupledAccount) {
        this();
        this.pdqMachineName = pdqMachineName;
        this.coupledAccount = coupledAccount;
    }

    public pdq() {
        super();
        this.id=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPdqMachineName() {
        return pdqMachineName;
    }

    public void setPdqMachineName(String pdqMachineName) {
        this.pdqMachineName = pdqMachineName;
    }

    public Account getCoupledAccount() {
        return coupledAccount;
    }

    public void setCoupledAccount(Account coupledAccount) {
        this.coupledAccount = coupledAccount;
    }

    public int getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(int uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }
}
