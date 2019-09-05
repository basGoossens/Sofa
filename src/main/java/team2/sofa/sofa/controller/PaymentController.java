package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.PrivateAccount;
import team2.sofa.sofa.model.Transaction;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.PrivateAccountDao;
import team2.sofa.sofa.service.FundTransfer;

@Controller
public class PaymentController {

    @Autowired
    AccountDao accountDao;

    @Autowired
    PrivateAccountDao privateAccountDao;

    @Autowired
    FundTransfer fundTransfer;

    @GetMapping(value = "TransferHandler")
    public String transfer(@RequestParam(name = "id") int id, PrivateAccount account, Model model){
        Transaction t = new Transaction();
        PrivateAccount a = privateAccountDao.findAccountById(id);
        model.addAttribute("transaction", t);
        model.addAttribute("account", a);
        model.addAttribute("client", a.getOwners().get(0));
        return "money_transfer";
    }


    @PostMapping(value = "transferMoneyHandler")
    public String transferMoneyHandler (Model model, Transaction transaction){
        fundTransfer.procesTransaction(transaction);
        PrivateAccount a = privateAccountDao.findAccountByIban(transaction.getDebitAccount().getIban());
        model.addAttribute("account", a);
        return "dashboard_client";
    }
}
