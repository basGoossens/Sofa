package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.PrivateAccount;
import team2.sofa.sofa.model.Transaction;
import team2.sofa.sofa.model.TransactionForm;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.PrivateAccountDao;
import team2.sofa.sofa.model.dao.TransactionDao;

@Controller
public class DashboardClientController {

    @Autowired
    TransactionDao transactionDao;
    @Autowired
    PrivateAccountDao privateAccountDao;
    @Autowired
    AccountDao accountDao;

    @GetMapping(value = "TransferHandler")
    public String transfer(@RequestParam(name = "id") int id, Model model){
        TransactionForm t = new TransactionForm();
        Account a = accountDao.findAccountById(id);
        model.addAttribute("form", t);
        model.addAttribute("account", a);
        model.addAttribute("client", a.getOwners().get(0));
        return "money_transfer";
    }

    /*@GetMapping(value = "clientViewHandler")
    public String clientViewHandler(@RequestParam(name = "id") int id, Account account, Model model) {
        System.out.println("HEY!" + id);
        System.out.println("HEY" + account);
        Account chosenAccount = accountDao.findAccountById(id);
        model.addAttribute("account", chosenAccount);
        return "dashboard_client"*/

}
