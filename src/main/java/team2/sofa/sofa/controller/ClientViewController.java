package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.PrivateAccount;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.ClientDao;
import team2.sofa.sofa.service.Clientview;
import team2.sofa.sofa.service.IBANGenerator;

import java.math.BigDecimal;

@Controller
public class ClientViewController {

    @Autowired
    Clientview clientview;

    @Autowired
    ClientDao clientDao;

    @Autowired
    AccountDao accountDao;

    @PostMapping(value = "AccountListHandler")
    public String clientView(Account account, Model model) {
        return clientview.accountFinderClient(account.getId(), model);
    }

    @PostMapping(value = "AddNewAccountHandler")
    public String addNewAccount(@ModelAttribute Client client, Model model){
        Account a = new PrivateAccount();
        IBANGenerator ibanGenerator = new IBANGenerator();
        String iban = ibanGenerator.ibanGenerator();
        a.setIban(iban);
        a.setBalance(new BigDecimal(0));
        Client c = clientDao.findClientById(client.getId());
        a.addClient(c);
        c.addAccount(a);
        clientDao.save(c);
        accountDao.save(a);
        model.addAttribute("client", c);
        model.addAttribute("account", a);
        return "client_view";
    }

}
