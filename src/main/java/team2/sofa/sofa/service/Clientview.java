package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.BusinessAccount;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.PrivateAccount;
import team2.sofa.sofa.model.dao.*;

import java.math.BigDecimal;

@Service
public class Clientview {

    @Autowired
    PrivateAccountDao privateAccountDao;

    @Autowired
    TransactionDao transactionDao;

    @Autowired
    BusinessAccountDao businessAccountDao;

    @Autowired
    AccountDao accountDao;
    @Autowired
    ClientDao clientDao;

    public Clientview() { super();
    }

    public String accountFinderClient(int id,  Model model) {
        Account chosenAccount = accountDao.findAccountById(id);
        model.addAttribute("account", chosenAccount);
        return "dashboard_client";
    }

    public String accountFinderEmployee(int id,  Model model, boolean business) {
        if (business == false){
            PrivateAccount chosenAccount = privateAccountDao.findAccountById(id);
            model.addAttribute("account", chosenAccount);
            return "dashboard_employee";}
        else {
            BusinessAccount chosenAccount = businessAccountDao.findAccountById(id);
            model.addAttribute("account", chosenAccount);
            return "dashboard_employee";
        }
    }

    public String accountOverview(int id, Model model) {
        Account chosenAccount = accountDao.findAccountById(id);
        model.addAttribute("account", chosenAccount);
        return "dashboard_employee";
    }
    public String createNewPrivate(Client client, Model model){
        Client c = clientDao.findClientById(client.getId());
        Account a = makeAccount(c);
        c.addAccount(a);
        clientDao.save(c);
        accountDao.save(a);
        model.addAttribute("client", c);
        model.addAttribute("account", a);
        return "client_view";
    }

    public Account makeAccount(Client client){
        IBANGenerator ibanGenerator = new IBANGenerator();
        String iban = ibanGenerator.ibanGenerator();
        Account a = new PrivateAccount(iban, new BigDecimal(0));
        a.addClient(client);
        return a;
    }
}
