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

    public BusinessAccount() {
        super();
        this.coupledPdqMachines = new ArrayList<>();
        this.business = null;
        super.setBusinessAccount(true);
    }

    public BusinessAccount(List<PDQ> coupledPdqMachines, Business business) {
        this();
        this.coupledPdqMachines = coupledPdqMachines;
        this.business = business;
        super.setBusinessAccount(true);
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

}
