package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.TransactionForm;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.PrivateAccountDao;
import team2.sofa.sofa.service.FundTransfer;

import javax.validation.Valid;

@Controller
public class PaymentController {

    @Autowired
    FundTransfer fundTransfer;

    @PostMapping(value = "transferMoneyHandler")
    public String transferMoneyHandler(@ModelAttribute @Valid TransactionForm transactionForm, BindingResult result, Model model, Errors error) {
        if (result.hasErrors()) {
            model.addAttribute("error", error);
            return "money_transfer";
        }
        if (fundTransfer.checkBalance(transactionForm)){
            String saldo = "onvoldoende Saldo";
            model.addAttribute("saldo", saldo);
            return "money_transfer";
        }
        if (!fundTransfer.checkToAccount(transactionForm.getCreditAccount())) {
            String fout = "ongeldig IBAN";
            model.addAttribute("fout", fout);
            return "money_transfer";
        }
        if (!fundTransfer.nameCheckIban(transactionForm.getName(), transactionForm.getCreditAccount())) {
            String fout = "Achternaam en IBAN komen niet overeen";
            model.addAttribute("mismatch", fout);
            return "money_transfer";
        }
        fundTransfer.procesTransaction(transactionForm);
        return fundTransfer.readyDashboard(transactionForm, model);
    }
}

