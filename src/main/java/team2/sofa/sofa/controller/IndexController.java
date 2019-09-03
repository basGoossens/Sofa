package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.dao.ClientDao;


@Controller
public class IndexController {
    @Autowired
    ClientDao clientDao;

    @GetMapping(value = "login")
    public String indexHandler(Model model) {
        Client client = new Client();
        model.addAttribute("member", client);
        return "login";
    }
}
