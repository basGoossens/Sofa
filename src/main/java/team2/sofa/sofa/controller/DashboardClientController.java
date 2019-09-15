package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.PrivateAccount;
import team2.sofa.sofa.model.Transaction;
import team2.sofa.sofa.model.dao.PrivateAccountDao;
import team2.sofa.sofa.model.dao.TransactionDao;
import team2.sofa.sofa.service.FundTransfer;
import team2.sofa.sofa.service.Login;

@Controller
public class DashboardClientController {

    @Autowired
    FundTransfer fundTransfer;
    @Autowired
    Login login;

    @GetMapping(value = "TransferHandler")
    public String transfer(@RequestParam(name = "id") int id, Model model){
        return fundTransfer.readyTransaction(id, model);
    }

    @PostMapping(value = "TransferHandler")
    public String transfer(Account account, Model model){
        return fundTransfer.readyTransaction(account.getId(), model);
    }
    @PostMapping(value = "backToOverview")
    public String backToOverviw(Account account, Model model){
        return login.backFromDashboard(account,model);
    }

    /*@GetMapping(value = "clientViewHandler")
    public String clientViewHandler(@RequestParam(name = "id") int id, Account account, Model model) {
        System.out.println("HEY!" + id);
        System.out.println("HEY" + account);
        Account chosenAccount = accountDao.findAccountById(id);
        model.addAttribute("account", chosenAccount);
        return "dashboard_client"*/

}
