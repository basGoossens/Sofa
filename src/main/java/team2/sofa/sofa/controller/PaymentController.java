package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import team2.sofa.sofa.model.TransactionForm;
import team2.sofa.sofa.service.FundTransfer;

@Controller
@SessionAttributes("sessionclient")
public class PaymentController {

    @Autowired
    FundTransfer fundTransfer;

    @PostMapping(value = "transferMoneyHandler")
    public String transferMoneyHandler(TransactionForm transactionForm, Model model) {
        if (fundTransfer.insufficientBalance(transactionForm)){
            String saldo = "onvoldoende Saldo";
            model.addAttribute("saldo", saldo);
            model = fundTransfer.returnToTransaction(transactionForm, model);
            return "money_transfer";
        }
        if (!fundTransfer.checkToAccount(transactionForm.getCreditAccount())) {
            String fout = "ongeldig IBAN";
            model.addAttribute("fout", fout);
            model = fundTransfer.returnToTransaction(transactionForm, model);
            return "money_transfer";
        }
        if (!fundTransfer.nameCheckIban(transactionForm.getName(), transactionForm.getCreditAccount())) {
            String fout = "Achternaam en IBAN komen niet overeen";
            model.addAttribute("mismatch", fout);
            model = fundTransfer.returnToTransaction(transactionForm, model);
            return "money_transfer";
        }
        if (transactionForm.getCreditAccount().equals(transactionForm.getDebetAccount())) {
            String fout = "Je kan niet geld naar je eigen bankrekening overmaken";
            model.addAttribute("foei", fout);
            model = fundTransfer.returnToTransaction(transactionForm, model);
            return "money_transfer";
        }
        model = fundTransfer.prepareConfirmation(transactionForm, model);
        return "confirm_payment";
    }

    @PostMapping(value = "confirmPayment")
    public String handleTransfer(TransactionForm transactionForm, Model model) {
        fundTransfer.procesTransaction(transactionForm);
        model = fundTransfer.readyDashboard(transactionForm, model);
        return "dashboard_client";
    }

}

