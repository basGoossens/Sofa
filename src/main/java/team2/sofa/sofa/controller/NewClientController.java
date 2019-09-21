package team2.sofa.sofa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import team2.sofa.sofa.model.Business;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.service.NewAccountChecker;

import javax.validation.Valid;
import java.util.Map;

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
    public String newAccountHandler(@RequestParam Map<String, String> accountType, Client client, Model model) {
            return newAccountChecker.processApplication(model, client, accountType);
        }


    @PostMapping(value = "NewBusiness2")
    public String newBusinessHandler(Business business, Model model, Client client) {
        return newAccountChecker.makeNewBusinessAccount(business, client);
    }

}