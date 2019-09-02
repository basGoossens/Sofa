package team2.sofa.sofa.controller;

import org.hibernate.dialect.hint.IndexQueryHintHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.Employee;
import team2.sofa.sofa.model.User;

@Controller
public class IndexController {

    @GetMapping(value = login_client)
    public String indexHandler(Model model) {
        User user = new Client();
        model.addAttribute("member", user);
        return "login_client";
    }

    @GetMapping(value = login_employee)
    public String indexHandler(Model model){
        User user = new Employee();
        model.addAttribute("employee", user);
        return "login_employee";
    }
}
