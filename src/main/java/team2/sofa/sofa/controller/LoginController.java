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
import team2.sofa.sofa.service.Login;
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
    Login login;
    @Autowired
    PasswordValidator passwordValidator;



    @GetMapping(value = "login")
    public String indexHandler(Model model) {
        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);
        return "login";
    }

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
            return login.clientLogin(client, model);
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
            return login.employeeLogin(employee, model);
        } else return "login_employee";
    }
}
