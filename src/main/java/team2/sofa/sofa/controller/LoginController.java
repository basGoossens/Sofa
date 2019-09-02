package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.Global;
import team2.sofa.sofa.model.User;
import team2.sofa.sofa.model.dao.ClientDao;
import team2.sofa.sofa.service.PasswordValidator;

@Controller
public class LoginController {

    @Autowired
    ClientDao clientDao;

    @GetMapping(value = "login")
    public String indexHandler(Model model) {
        User user = new Client();
        model.addAttribute("member", user);
        return "login";
    }

    @PostMapping(value = "loginHandler")
    public String loginHandler(@ModelAttribute User user, Model model) {
        PasswordValidator passwordValidator = new PasswordValidator();
        boolean loginOk = passwordValidator.validateClientPassword(user);
        if (loginOk)  {
            Global.setCurrentUser(clientDao.findByName(user.getUserName()));
            return "client_view";
        }
        else return "login_fail";
    }

}
