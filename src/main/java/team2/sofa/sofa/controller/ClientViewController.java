package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.service.Clientview;

@Controller
public class ClientViewController {

    @Autowired
    Clientview clientview;

//    @GetMapping(value = "PrivateAccountListHandler")
//    public String clientViewHandler(@RequestParam(name = "id") int id,  Model model){
//        return clientview.accountFinderClient(id, model, false);
//    }
//
//    @GetMapping(value = "BusinessAccountListHandler")
//    public String BusinessAccountListHandler(@RequestParam(name = "id") int id, Model model){
//        return clientview.accountFinderClient(id, model, true);
//    }

    @PostMapping(value = "AccountListHandler")
    public String clientView(Account account, Model model){
        return clientview.accountFinderClient(account.getId(), model);

    }

}
