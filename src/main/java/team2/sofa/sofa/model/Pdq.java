package team2.sofa.sofa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Random;

@Entity
public class Pdq {

    @Id
    @GeneratedValue
    private int id;


    private String fiveDigitcode;
    private int eightDigitcode;


    @ManyToOne
    private Account coupledAccount;

    public Pdq(String fiveDigitcode, int eightDigitcode, Account coupledAccount) {
        this();
        this.fiveDigitcode = fiveDigitcode;
        this.eightDigitcode = eightDigitcode;
        this.coupledAccount = coupledAccount;
    }

    public Pdq() {
        super();
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFiveDigitcode() {
        return fiveDigitcode;
    }

    public void setFiveDigitcode(String fiveDigitcode) {
        this.fiveDigitcode = fiveDigitcode;
    }

    public int getEightDigitcode() {
        return eightDigitcode;
    }

    public void setEightDigitcode(int eightDigitcode) {
        this.eightDigitcode = eightDigitcode;
    }

    public Account getCoupledAccount() {
        return coupledAccount;
    }

    public void setCoupledAccount(Account coupledAccount) {
        this.coupledAccount = coupledAccount;
    }

}



