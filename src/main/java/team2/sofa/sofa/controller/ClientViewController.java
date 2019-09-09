package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.BusinessAccount;
import team2.sofa.sofa.model.PrivateAccount;
import team2.sofa.sofa.model.dao.BusinessAccountDao;
import team2.sofa.sofa.model.dao.PrivateAccountDao;
import team2.sofa.sofa.model.PrivateAccount;
import team2.sofa.sofa.model.Transaction;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.PrivateAccountDao;
import team2.sofa.sofa.model.dao.TransactionDao;

import java.util.List;
import java.util.Optional;


@Controller
public class ClientViewController {

    @Autowired
    PrivateAccountDao privateAccountDao;

    @Autowired
    TransactionDao transactionDao;

    @Autowired
    BusinessAccountDao businessAccountDao;

    @GetMapping(value = "clientViewHandler")
    public String clientViewHandler(@RequestParam(name = "id") int id, PrivateAccount account, Model model){
        System.out.println("HEY!" + id);
        System.out.println("HEY" + account);
        PrivateAccount chosenAccount = privateAccountDao.findAccountById(id);
//        List<Transaction> bij = transactionDao.findTransactionsByCreditAccount(id);
//        List<Transaction> af = transactionDao.findTransactionsByDebitAccount(id);
        model.addAttribute("account", chosenAccount);
//        model.addAttribute("bij", bij);
//        model.addAttribute("af", af);
        return "dashboard_client";
    }

    @GetMapping(value = "PrivateAccountListHandler")
    public String PrivateAccountListHandler(@RequestParam(name = "id") int id, Model model){
        PrivateAccount chosenAccount = privateAccountDao.findAccountById(id);
        model.addAttribute("account", chosenAccount);
        return "dashboard_client";
    }

    @GetMapping(value = "BusinessAccountListHandler")
    public String BusinessAccountListHandler(@RequestParam(name = "id") int id, Model model){
        BusinessAccount chosenAccount = businessAccountDao.findAccountById(id);
        model.addAttribute("account", chosenAccount);
        return "dashboard_client";
    }


}
