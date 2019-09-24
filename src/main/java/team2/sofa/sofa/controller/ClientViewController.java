package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.BusinessDao;
import team2.sofa.sofa.model.dao.ClientDao;
import team2.sofa.sofa.service.Clientview;
import team2.sofa.sofa.service.IBANGenerator;
import team2.sofa.sofa.service.Login;

import java.math.BigDecimal;

@Controller
public class ClientViewController {

    @Autowired
    Clientview clientview;
    @Autowired
    Login login;

    @PostMapping(value = "AccountListHandler")
    public String clientView(Account account, Model model) {
        return clientview.accountFinderClient(account.getId(), model);
    }

    @PostMapping(value = "AddNewAccountHandler")
    public String addNewAccount(Client client, Model model){
        return clientview.createNewPrivate(client, model);
    }

    @PostMapping(value = "AddNewBusinessAccountHandler")
    public String addNewBusinessAccountHandler(Client client,Model model){
        Client client2 = clientview.getClient(client.getId());
        Business business = new Business();
        business.setOwner(client2);
        model.addAttribute("business", business);
        return "add_business_account";
    }

    @PostMapping(value = "NewBusiness")
    public String newBAccount(Business business, Model model){
        clientview.procesNewBusinessAccount(business);
        Client c = business.getOwner();
        model.addAttribute("client", c);
        model.addAttribute("nrBusiness", login.countBusinessAccounts(c));
        model.addAttribute("nrPrivate", login.countPrivateAccounts(c));
        return "client_view";
    }


}
