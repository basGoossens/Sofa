package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.Global;
import team2.sofa.sofa.model.User;
import team2.sofa.sofa.model.dao.ClientDao;
import team2.sofa.sofa.service.PasswordValidator;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    ClientDao clientDao;

    @RequestMapping("login")
    public String loginHandler(Model model, Client client) {
        return "login";
    }

    @PostMapping(value = "loginHandler")
    public String loginHandler(@ModelAttribute Client client, Model model) {
    Client formClient = new Client();
        PasswordValidator passwordValidator = new PasswordValidator();
        boolean loginOk = passwordValidator.validateClientPassword(client);
        if (loginOk)  {
            Client currentClient = clientDao.findByUsername(client.getUsername());
            return "client_view";
        }
        else return "login_fail";
    }
}
