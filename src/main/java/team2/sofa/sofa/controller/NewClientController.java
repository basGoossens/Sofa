package team2.sofa.sofa.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import team2.sofa.sofa.model.Client;

@Controller
public class NewClientController {

    @GetMapping(value = "new_account")
    public String newClientHandler(Model model){
    Client client = new Client();
    model.addAttribute("client", client);
        return "new_account";
    }


    @PostMapping(value = "newAccountHandler")
    public String loginHandler(@ModelAttribute Client client, Model model) {
        return "login";
    }
    }