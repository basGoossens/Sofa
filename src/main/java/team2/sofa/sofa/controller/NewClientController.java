package team2.sofa.sofa.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewClientController {

    @GetMapping(value = "new_account")
    public String newClientHandler(Model model){
        return "new_account";
    }
}
