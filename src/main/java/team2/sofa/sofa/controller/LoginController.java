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
    public String loginEmployeeHandler(@ModelAttribute @Valid LoginForm loginEmpForm, Model model, Errors error, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("error", error);
            return "login_employee";
        }
        Employee employee1 = new Employee();
        employee1.setUsername(employee1.getUsername());
        employee1.setPassword(employee1.getPassword());
        boolean loginOK = passwordValidator.validateEmployeePassword(employee1);
        if (loginOK) {
            return login.employeeLogin(employee1, model);
        } else return "login_employee";
    }
}
