package team2.sofa.sofa.controller;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.service.*;


import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes({"sessionclient", "connect", "nrBusiness", "nrPrivate", "sessionemployee"})
public class LoginController {
    @Autowired
    Login login;
    @Autowired
    PasswordValidator passwordValidator;
    @Autowired
    TopTenHighestBalanceFinder topTenHighestBalanceFinder;
    @Autowired
    TopTenMostActiveClient topTenMostActiveClient;
    @Autowired
    SectorAnalyzer sectorAnalyzer;

    @GetMapping(value = "login")
    public String indexHandler(Model model) {
        model.addAttribute("client", new Client());
        return "login";
    }


    //logoutHandler toegevoegd
    @GetMapping(value = "logout")
    public String logoutHandler(Model model) {
        model.addAttribute("sessionclient", "");
        model.addAttribute("client", new Client());
        return "login";
    }


    @GetMapping(value = "login_employee")
    public String goTologinEmployeeHandler(Model model) {
        model.addAttribute("employee", new Employee());
        return "login_employee";
    }

    @RequestMapping(value = "logoutClientHandler")
    public String logOutClientHandler() {
        return "login";
    }

    @PostMapping(value = "loginClientHandler")
    public String loginClientHandler(Client client, Model model) {
        boolean loginOk = passwordValidator.validateClientPassword(client);
        if (loginOk) {
            Client loggedInClient = login.clientLogin(client, model);
            model.addAttribute("sessionclient", loggedInClient);
            login.checkAndLoadConnector(loggedInClient, model);
            model.addAttribute("nrBusiness", login.countBusinessAccounts(loggedInClient));
            model.addAttribute("nrPrivate", login.countPrivateAccounts(loggedInClient));
            Hibernate.initialize(loggedInClient.getAccounts());
            return "redirect:/loadClientView";
        } else {
            model.addAttribute("fout", "Gebruikersnaam en/of wachtwoord zijn niet juist");
            return "login";
        }
    }


    @PostMapping(value = "loginEmployeeHandler")
    public String loginEmployeeHandler(Employee employee, Model model) {

        Employee currentEmployee = new Employee();
        currentEmployee.setUsername(employee.getUsername());
        currentEmployee.setPassword(employee.getPassword());
        boolean loginOK = passwordValidator.validateEmployeePassword(currentEmployee);
        if (loginOK) {
            currentEmployee = login.employeeLogin(currentEmployee, model);

            if (currentEmployee.getRole().equals(EmployeeRole.HOOFD_PARTICULIEREN)) {
                return "redirect:/loginEmployeePrivateSuccess";

            } if (currentEmployee.getRole().equals(EmployeeRole.ACCOUNTMANAGER)){
                return "account_manager_view";
            }

            else {
                return "redirect:/loginEmployeeBusinessSuccess";
            }

        } else {
            model.addAttribute("Fout", "Gebruikersnaam en/of wachtword zijn niet juist");
            return "login_employee";
        }
    }
}