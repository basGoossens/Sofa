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

    public Clientview() {
        super();
    }

    public Client findClientById(int id) {
        return clientDao.findClientById(id);
    }


    public Account FindAccountById(int id) {
        Account chosenAccount = accountDao.findAccountById(id);
        return chosenAccount;
    }

    //evt exception inbouwen voor als we nog andersoortige rekeningen krijgen.
    public Account FindPrivateOrBusinessAccountById(int id, boolean isBusiness) {
        Account chosenAccount = new Account();
        if (isBusiness) {
            chosenAccount = businessAccountDao.findAccountById(id);
        }
        if (!isBusiness) {
            chosenAccount = privateAccountDao.findAccountById(id);
        }
        return chosenAccount;
    }

    public String accountOverview(int id, Model model) {
        Account chosenAccount = accountDao.findAccountById(id);
        model.addAttribute("account", chosenAccount);
        return "dashboard_employee";
    }

    public void createNewPrivate(int id, Model model) {
        Client c = clientDao.findClientById(id);
        Account a = makeAccount(c);
        c.addAccount(a);
        clientDao.save(c);
        accountDao.save(a);
        model.addAttribute("sessionclient", c);
        model.addAttribute("nrBusiness", login.countBusinessAccounts(c));
        model.addAttribute("nrPrivate", login.countPrivateAccounts(c));
    }


    public Account makeAccount(Client client) {
        IBANGenerator ibanGenerator = new IBANGenerator();
        String iban = ibanGenerator.ibanGenerator();
        Account a = new PrivateAccount(iban, new BigDecimal(0.00));
        a.addClient(client);
        return a;
    }

    public Account procesNewBusinessAccount(Business business) {
        IBANGenerator ibanGenerator = new IBANGenerator();
        String iban = ibanGenerator.ibanGenerator();
        businessDao.save(business);
        Client c = business.getOwner();
        BusinessAccount a = new BusinessAccount();
        a.addClient(c);
        a.setIban(iban);
        a.setBusiness(business);
        a.setBalance(new BigDecimal(0.00));
        c.addAccount(a);
        clientDao.save(c);
        accountDao.save(a);
        return a;
    }

}


