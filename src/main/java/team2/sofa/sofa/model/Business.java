package team2.sofa.sofa.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Business {
    @Id
    @GeneratedValue
    private int id;
    private String businessName;
    @Enumerated(EnumType.STRING)
    private BusinessSector sector;
    //We chose at this time not to include a list of associated accounts here, as not current User-stories require it.
    //Connections run via account <-> client

    public Business() {
        this.id = 0;
    }

    public Business(String businessName, BusinessSector sector) {
        this();
        this.businessName = businessName;
        this.sector = sector;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public BusinessSector getSector() {
        return sector;
    }

    public void setSector(BusinessSector sector) {
        this.sector = sector;
    }
}
