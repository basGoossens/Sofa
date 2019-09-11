package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.PrivateAccountDao;
import team2.sofa.sofa.service.FundTransfer;

import javax.validation.Valid;

@Controller
public class PaymentController {

    @Autowired
    AccountDao accountDao;

    @Autowired
    PrivateAccountDao privateAccountDao;

    @Autowired
    FundTransfer fundTransfer;

    @PostMapping(value = "transferMoneyHandler")
    public String transferMoneyHandler (@ModelAttribute @Valid TransactionForm transactionForm, BindingResult result, Model model, Errors error){
        if (result.hasErrors()){
            model.addAttribute("error", error);
            return "money_transfer";
        }
        fundTransfer.procesTransaction(transactionForm);
        Account a = accountDao.findAccountByIban(transactionForm.getDebetAccount());
        model.addAttribute("account", a);
        return "dashboard_client";
    }
}
