package team2.sofa.sofa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.service.Login;
import team2.sofa.sofa.service.PasswordValidator;


import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    Login login;
    @Autowired
    PasswordValidator passwordValidator;

    @GetMapping(value = "login")
    public String indexHandler(Model model) {
        model.addAttribute("client", new Client());
        return "login";
    }

    @GetMapping(value = "login_employee")
    public String goTologinEmployeeHandler(Model model) {
        model.addAttribute("employee", new Employee());
        return "login_employee";
    }

    @RequestMapping(value = "logoutClientHandler")
    public String logOutClientHandler(){
        return "login";
    }

    @PostMapping(value = "loginClientHandler")
    public String loginClientHandler(Client client, Model model) {
        boolean loginOk = passwordValidator.validateClientPassword(client);
        if (loginOk) {
            return login.clientLogin(client, model);
        } else {
            model.addAttribute("fout", "Gebruikersnaam en/of wachtwoord zijn niet juist");
            return "login";
        }
    }

    @PostMapping(value = "loginEmployeeHandler")
    public String loginEmployeeHandler(@ModelAttribute Employee employee, Model model) {
        if (employee.getUsername().isEmpty()) {
            return "login_employee";
        }
        if (employee.getPassword().isEmpty()) {
            return "login_employee";
        }
        boolean loginOk = passwordValidator.validateEmployeePassword(employee);
        if (loginOk) {
            return login.employeeLogin(employee, model);
        } else return "login_employee";
    }
}
