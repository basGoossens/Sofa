package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.BusinessAccount;
import team2.sofa.sofa.model.PrivateAccount;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.BusinessAccountDao;
import team2.sofa.sofa.model.dao.PrivateAccountDao;
import team2.sofa.sofa.model.dao.TransactionDao;

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
}
