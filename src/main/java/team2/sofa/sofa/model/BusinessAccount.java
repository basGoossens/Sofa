package team2.sofa.sofa.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BusinessAccount extends Account{

    @OneToMany
    private List<PDQ> coupledPdqMachines;
    @ManyToOne
    private Business business;
    private boolean isBusinessAccount;

    public BusinessAccount() {
        super();
        this.coupledPdqMachines = new ArrayList<>();
        this.business = null;
        this.isBusinessAccount = true;
    }

    public BusinessAccount(List<PDQ> coupledPdqMachines, Business business) {
        this();
        this.coupledPdqMachines = coupledPdqMachines;
        this.business = business;
        this.isBusinessAccount = true;
    }

    public List<PDQ> getCoupledPdqMachines() {
        return coupledPdqMachines;
    }

    public void setCoupledPdqMachines(List<PDQ> coupledPdqMachines) {
        this.coupledPdqMachines = coupledPdqMachines;
    }

    public void addPdqMachine(PDQ pdqMachine){
        this.coupledPdqMachines.add(pdqMachine);
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public boolean isBusinessAccount() {
        return isBusinessAccount;
    }

    public void setBusinessAccount(boolean businessAccount) {
        isBusinessAccount = businessAccount;
    }
}
