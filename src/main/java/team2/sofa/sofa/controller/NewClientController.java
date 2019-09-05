package team2.sofa.sofa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.service.NewAccountChecker;

import javax.validation.Valid;

@Controller
public class NewClientController {

    @Autowired
    NewAccountChecker newAccountChecker;

    @GetMapping(value = "new_account")
    public String newClientHandler(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);
        return "new_account";
    }


    @PostMapping(value = "newAccountHandler")
    public String newAccountHandler(@ModelAttribute @Valid Client client, BindingResult result, Model model, Errors error) {
        if (result.hasErrors()){
            model.addAttribute("error", error);
            return "new_account";
        }
        return newAccountChecker.processApplication(client);
    }
}