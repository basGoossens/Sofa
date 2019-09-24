package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import team2.sofa.sofa.model.*;
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
    @Autowired
    BusinessDao businessDao;
    @Autowired
    Login login;

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
        model.addAttribute("sessionclient", c);
        model.addAttribute("nrBusiness", login.countBusinessAccounts(c));
        model.addAttribute("nrPrivate", login.countPrivateAccounts(c));
        return "client_view";
    }

    public Account makeAccount(Client client){
        IBANGenerator ibanGenerator = new IBANGenerator();
        String iban = ibanGenerator.ibanGenerator();
        Account a = new PrivateAccount(iban, new BigDecimal(0));
        a.addClient(client);
        return a;
    }
    public Client getClient(int id){
        return clientDao.findClientById(id);
    }

    public Account procesNewBusinessAccount(Business business){
        IBANGenerator ibanGenerator = new IBANGenerator();
        String iban = ibanGenerator.ibanGenerator();
        businessDao.save(business);
        Client c = business.getOwner();
        BusinessAccount a = new BusinessAccount();
        a.addClient(c);
        a.setIban(iban);
        a.setBusiness(business);
        a.setBalance(new BigDecimal(0));
        c.addAccount(a);
        clientDao.save(c);
        accountDao.save(a);
        return a;
    }
}
