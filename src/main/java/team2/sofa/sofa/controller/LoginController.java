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
import team2.sofa.sofa.model.dao.ClientDao;
import team2.sofa.sofa.model.dao.EmployeeDao;
import team2.sofa.sofa.service.PasswordValidator;
import team2.sofa.sofa.service.TopTenHighestBalanceFinder;
import team2.sofa.sofa.service.TopTenMostActiveClientFinder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


@Controller
public class LoginController {

    @Autowired
    ClientDao clientDao;
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    PasswordValidator passwordValidator;
    @Autowired
    TopTenHighestBalanceFinder topTenHighestBalanceFinder;
    @Autowired
    TopTenMostActiveClientFinder topTenMostActiveClientFinder;


    @GetMapping(value = "login_employee")
    public String goTologinEmployeeHandler(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "login_employee";
    }

    @GetMapping(value = "login_client")
    public String goTologinClientHandler(Model model) {
        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);
        return "login";
    }

    @RequestMapping(value = "logoutClientHandler")
    public String logOutClientHandler(){
        return "login";
    }

    @PostMapping(value = "loginClientHandler")
    public String loginClientHandler(@ModelAttribute @Valid LoginForm loginForm, Model model, Errors error, BindingResult result) {
        if (result.hasErrors()){
            model.addAttribute("error", error);
            return "login";
        }
        Client client = new Client();
        client.setUsername(loginForm.getUsername1());
        client.setPassword(loginForm.getPassword1());
        boolean loginOk = passwordValidator.validateClientPassword(client);
        if (loginOk) {
            Client loggedInClient = clientDao.findClientByUsername(client.getUsername());
            model.addAttribute("client", loggedInClient);
//            Accounts van klant scheiden in business en private
            ArrayList<Account> listPrivateAccounts = new ArrayList<>();
            ArrayList<Account> listBusinessAccounts = new ArrayList<>();
            for (Account a:loggedInClient.getAccounts()
                 ) { if (a.isBusinessAccount()) {listBusinessAccounts.add(a);}
                 else {listPrivateAccounts.add(a);}
            }
            model.addAttribute("listPrivateAccounts", listPrivateAccounts);
            model.addAttribute("listBusinessAccounts", listBusinessAccounts);
            return "client_view";
        } else return "login";
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
            Employee currentEmployee = employeeDao.findEmployeeByUsername(employee.getUsername());
            model.addAttribute("employee", currentEmployee);



            if (currentEmployee.getRole().equals(EmployeeRole.HOOFD_PARTICULIEREN)) {
                List<PrivateAccount> topTenHighest;
                topTenHighest = topTenHighestBalanceFinder.getTopTenHighestBalance();
                model.addAttribute("tenHighestBalance", topTenHighest);
                return "employee_view_particulieren";

            } else {
                List<Client> topTenMostActive;
                List<BusinessAccount> topTenHighest;
                topTenHighest = topTenHighestBalanceFinder.getTopTenHighestBalanceBusiness();
                topTenMostActive = topTenMostActiveClientFinder.getTopTenMostActiveClients();
                model.addAttribute("tenMostActive", topTenMostActive);
                model.addAttribute("tenHighestBalance", topTenHighest);
                return "employee_view_mkb";
            }





        } else return "login_employee";
    }
}
