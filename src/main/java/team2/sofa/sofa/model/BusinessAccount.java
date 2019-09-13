package team2.sofa.sofa.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BusinessAccount extends Account {

    @OneToMany
    private List<pdq> coupledPdqMachines;
    @ManyToOne
    private Business business;

    public BusinessAccount() {
        super();
        this.coupledPdqMachines = new ArrayList<>();
        this.business = null;
        super.setBusinessAccount(true);
    }

    public BusinessAccount(List<pdq> coupledPdqMachines, Business business) {
        this();
        this.coupledPdqMachines = coupledPdqMachines;
        this.business = business;
        super.setBusinessAccount(true);
    }

    public List<pdq> getCoupledPdqMachines() {
        return coupledPdqMachines;
    }

    public void setCoupledPdqMachines(List<pdq> coupledPdqMachines) {
        this.coupledPdqMachines = coupledPdqMachines;
    }

    public void addPdqMachine(pdq pdqMachine) {
        this.coupledPdqMachines.add(pdqMachine);
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

}
