package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.BusinessDao;
import team2.sofa.sofa.model.dao.ClientDao;
import team2.sofa.sofa.service.Clientview;
import team2.sofa.sofa.service.IBANGenerator;
import team2.sofa.sofa.service.Login;

import java.math.BigDecimal;

@Controller
@SessionAttributes({"sessionclient", "connect", "nrBusiness", "nrPrivate"})
public class ClientViewController {

    @Autowired
    Clientview clientview;
    @Autowired
    Login login;
    @Autowired
    ClientDao clientDao;

    @PostMapping(value = "AccountListHandler")
    public String clientView(Account account, Model model) {
        return clientview.accountFinderClient(account.getId(), model);
    }

    @PostMapping(value = "AddNewAccountHandler")
    public String addNewAccount(@RequestParam int id, Model model){
    Client client = clientDao.findClientById(id);
        return clientview.createNewPrivate(client, model);
    }

    @PostMapping(value = "AddNewBusinessAccountHandler")
    public String addNewBusinessAccountHandler(@RequestParam int id, Model model){
        Client client2 = clientDao.findClientById(id);
        Business business = new Business();
        business.setOwner(client2);
        model.addAttribute("business", business);
        return "add_business_account";
    }

    @PostMapping(value = "NewBusiness")
    public String newBAccount(Business business, Model model){
        clientview.procesNewBusinessAccount(business);
        Client c = business.getOwner();
        model.addAttribute("sessionclient", c);
        model.addAttribute("nrBusiness", login.countBusinessAccounts(c));
        model.addAttribute("nrPrivate", login.countPrivateAccounts(c));
        return "client_view";
    }

    @GetMapping(value="loadClientView")
    public String loadClientView(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        return "client_view";
    }

}
