package team2.sofa.sofa.controller;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.service.Login;
import team2.sofa.sofa.service.PasswordValidator;


import javax.validation.Valid;

@Controller
@SessionAttributes({"sessionclient", "connect", "nrBusiness", "nrPrivate"})
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


    //logoutHandler toegevoegd
    @GetMapping(value = "logout")
    public String logoutHandler(Model model) {
        model.addAttribute("sessionclient", "");
        model.addAttribute("client", new Client());
        return "login";
    }


    @GetMapping(value = "login_employee")
    public String goTologinEmployeeHandler(Model model) {
        LoginForm loginEmpForm = new LoginForm();
        model.addAttribute("loginEmpForm", loginEmpForm);
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
            Client loggedInClient = login.clientLogin(client, model);
            model.addAttribute("sessionclient", loggedInClient);
            login.checkAndLoadConnector(loggedInClient, model);
            model.addAttribute("nrBusiness", login.countPrivateAccounts(loggedInClient));
            model.addAttribute("nrPrivate", login.countPrivateAccounts(loggedInClient));
            Hibernate.initialize(loggedInClient.getAccounts());
            return "redirect:/clientLoginSuccess";
    } else {
            model.addAttribute("fout", "Gebruikersnaam en/of wachtwoord zijn niet juist");
            return "login";
        }
    }

    @GetMapping(value="clientLoginSuccess")
    public String clientLoginSuccess(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        return "client_view";
    }



    @PostMapping(value = "loginEmployeeHandler")
    public String loginEmployeeHandler(@ModelAttribute @Valid LoginForm loginEmpForm, Model model, Errors error, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("error", error);
            return "login_employee";
        }
        Employee employee1 = new Employee();
        employee1.setUsername(loginEmpForm.getUsername1());
        employee1.setPassword(loginEmpForm.getPassword1());
        boolean loginOK = passwordValidator.validateEmployeePassword(employee1);
        if (loginOK) {
            return login.employeeLogin(employee1, model);
        } else return "login_employee";
    }
}
