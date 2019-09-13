package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.service.Clientview;

@Controller
public class ClientViewController {

    @Autowired
    Clientview clientview;

    @PostMapping(value = "AccountListHandler")
    public String clientView(Account account, Model model) {
        return clientview.accountFinderClient(account.getId(), model);

    }

}
