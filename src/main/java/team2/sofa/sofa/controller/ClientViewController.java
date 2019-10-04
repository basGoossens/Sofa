package team2.sofa.sofa.controller;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.model.dao.ClientDao;
import team2.sofa.sofa.service.Clientview;
import team2.sofa.sofa.service.Login;


@Controller
@SessionAttributes({"sessionclient", "connect", "nrBusiness", "nrPrivate", "newaccountid", "account"})
public class ClientViewController {

    @Autowired
    Clientview clientview;
    @Autowired
    Login login;
    @Autowired
    ClientDao clientDao;


    /** methode om elementen voor client_view.html te vullen.
     *
     * @param client
     * @param model
     */
    public void fillClientView(Client client, Model model){
        Client loggedInClient = clientDao.findClientByUsername(client.getUsername());
        model.addAttribute("sessionclient", loggedInClient);
        login.checkAndLoadConnector(loggedInClient, model);
        model.addAttribute("nrBusiness", login.countBusinessAccounts(loggedInClient));
        model.addAttribute("nrPrivate", login.countPrivateAccounts(loggedInClient));
        Hibernate.initialize(loggedInClient.getAccounts());
    }

    @GetMapping(value="loadClientView")
    public String loadClientView(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        return "client_view";
    }

    @PostMapping(value = "accountListHandler")
    public String accountListHandler(Account account, Model model) {
        account = clientview.FindAccountById(account.getId());
        model.addAttribute("account", account);
        Hibernate.initialize(account.getTransactions());
        return "redirect:/loadDashboardClient";
    }

    @PostMapping(value = "addNewAccountHandler")
    public String addNewAccount(@RequestParam int id, Model model){
        clientview.createNewPrivate(id, model);
        return "redirect:/loadClientView";
    }

    @PostMapping(value = "addNewBusinessAccountHandler")
    public String addNewBusinessAccountHandler(@RequestParam int id, Model model){
        Client client = clientview.findClientById(id);
        Business business = new Business();
        business.setOwner(client);
        model.addAttribute("business", business);
        return "add_business_account";
    }

    @PostMapping(value = "newBusiness")
    public String newBAccount(Business business, Model model){
        clientview.procesNewBusinessAccount(business);
        Client c = business.getOwner();
        model.addAttribute("sessionclient", c);
        model.addAttribute("nrBusiness", login.countBusinessAccounts(c));
        model.addAttribute("nrPrivate", login.countPrivateAccounts(c));
        return "redirect:/loadClientView";
    }



}
