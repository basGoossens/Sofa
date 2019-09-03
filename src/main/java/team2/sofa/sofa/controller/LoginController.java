package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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



    @GetMapping(value = "login_employee.html")
    public String goTologinEmployeeHandler(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "login_employee.html";

    }

    @PostMapping(value = "loginClientHandler")
    public String loginClientHandler(@ModelAttribute Client client, Model model) {
        PasswordValidator passwordValidator = new PasswordValidator();
        boolean loginOk = passwordValidator.validateClientPassword(client);
        if (loginOk)  {
            /*Client currentClient = clientDao.findByUsername(client.getUserName());*/
            return "client_view";
        }
        else return "login";
    }
    @PostMapping(value = "loginEmployeeHandler")
    public String loginEmployeeHandler(@ModelAttribute Employee employee, Model model) {
        PasswordValidator passwordValidator = new PasswordValidator();
        boolean loginOk = passwordValidator.validateEmployeePassword(employee);
        if (loginOk) {
            Employee currentEmployee = employeeDao.findByUsername(employee.getUserName());
            return "employee_view";
        } else return "login";
    }

}
