package team2.sofa.sofa.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BusinessAccount extends Account{

    @OneToMany
    private List<PDQ> coupledPdqMachines;

    public BusinessAccount() {
        super();
        this.coupledPdqMachines = new ArrayList<>();
    }

    public BusinessAccount(List<PDQ> coupledPdqMachines) {
        this();
        this.coupledPdqMachines = coupledPdqMachines;
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
}
