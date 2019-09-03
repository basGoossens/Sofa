package team2.sofa.sofa.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.Employee;

import javax.persistence.Entity;

@Entity
public class DashboardEmployeeController {


    //Ga terug naar de overview pagina van Employee//
    @GetMapping(value = "employee_view")
    public String backToEmployeeView(Model model) {
        return "employee-view";
    }
}
