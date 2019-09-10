package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.LoginForm;
import team2.sofa.sofa.model.dao.ClientDao;


@Controller
public class IndexController {
    @Autowired
    ClientDao clientDao;

    @GetMapping(value = "")
    public String startHandler() {
        return "index";
    }

    @GetMapping(value = "index")
    public String homeHandler() {
        return "index";
    }

    @GetMapping(value = "login")
    public String indexHandler(Model model) {
        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);

        return "login";

    }
}

