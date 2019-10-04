package team2.sofa.sofa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.service.*;


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
    @Autowired
    Clientview clientview;
    @Autowired
    ClientViewController clientViewController;

    @GetMapping(value = "login")
    public String indexHandler(Model model) {
        model.addAttribute("client", new Client());
        return "login";
    }

    @GetMapping(value = "logout")
    public String logoutHandler(Model model) {
        model.addAttribute("sessionclient", "");
        model.addAttribute("connect", "");
        model.addAttribute("client", new Client());
        return "index";
    }


    @GetMapping(value = "login_employee")
    public String goToLoginEmployeeHandler(Model model) {
        model.addAttribute("employee", new Employee());
        return "login_employee";
    }

    @PostMapping(value = "loginClientHandler")
    public String loginClientHandler(Client client, Model model) {
        boolean loginOk = passwordValidator.validateClientPassword(client);
        if (loginOk) {
            clientViewController.fillClientView(client, model);
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
            Employee fullemployee = login.employeeLogin(currentEmployee, model);

            if (fullemployee.getRole().equals(EmployeeRole.HOOFD_PARTICULIEREN)) {
                return "redirect:/loadEmployeeViewPrivate";

            } if (fullemployee.getRole().equals(EmployeeRole.ACCOUNTMANAGER)){
                return "account_manager_view";
            }

            else {
                return "redirect:/loadEmployeeViewBusiness";
            }

        } else {
            model.addAttribute("Fout", "Gebruikersnaam en/of wachtword zijn niet juist");
            return "login_employee";
        }
    }
}
