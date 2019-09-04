package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.Employee;
import team2.sofa.sofa.model.dao.ClientDao;
import team2.sofa.sofa.model.dao.EmployeeDao;
import team2.sofa.sofa.service.PasswordValidator;

@Controller
public class LoginController {

    @Autowired
    ClientDao clientDao;
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    PasswordValidator passwordValidator;


    @GetMapping(value = "login_employee")
    public String goTologinEmployeeHandler(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "login_employee";

    }

    @PostMapping(value = "loginClientHandler")
    public String loginClientHandler(@ModelAttribute Client client, Model model) {
        if (client.getUsername().isEmpty()){
            return "login";
        }
        if (client.getPassword().isEmpty()){
            return "login";
        }
        boolean loginOk = passwordValidator.validateClientPassword(client);
        if (loginOk) {
            Client loggedInClient = clientDao.findClientByUsername(client.getUsername());
            model.addAttribute("client", loggedInClient);
            return "client_view";
        } else return "login";
    }

    @PostMapping(value = "loginEmployeeHandler")
    public String loginEmployeeHandler(@ModelAttribute Employee employee, Model model) {
        if (employee.getUsername().isEmpty()){
            return "login_employee";
        }
        if (employee.getPassword().isEmpty()){
            return "login_employee";
        }
        boolean loginOk = passwordValidator.validateEmployeePassword(employee);
        if (loginOk) {
            Employee currentEmployee = employeeDao.findEmployeeByUsername(employee.getUsername());
            model.addAttribute("employee", currentEmployee);
            return "employee_view";
        } else return "login_employee";
    }
}
