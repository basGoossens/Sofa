package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Connector;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.ConnectorDao;
import team2.sofa.sofa.service.FundTransfer;
import team2.sofa.sofa.service.Login;

import java.util.Map;

@Controller
public class DashboardClientController {

    @Autowired
    FundTransfer fundTransfer;
    @Autowired
    Login login;
    @Autowired
    AccountDao accountDao;
    @Autowired
    ConnectorDao connectorDao;

    @PostMapping(value = "TransferHandler")
    public String transfer(Account account, Model model) {
        return fundTransfer.readyTransaction(account.getId(), model);
    }

    @PostMapping(value = "ConnectAccount")
    public String connectAccounts(@RequestParam int id, Model model){
        Account account = accountDao.findAccountById(id);
        model.addAttribute("account", account);
        return "connect_accounts";
    }
    @PostMapping(value = "ConnectAccount/form")
    public String connectHandeler(@RequestParam Map<String, Object> body, Model model){
        String iban = body.get("bankaccount").toString();
        String user = body.get("newuser").toString();
        String accescode = body.get("accesscode").toString();
        Connector connector = new Connector(user,accescode,iban);
        connectorDao.save(connector);
        model.addAttribute("account", accountDao.findAccountByIban(iban));
        return "dashboard_client";
    }

    @PostMapping(value = "backToOverview")
    public String backToOverview(Account account, Model model) {
        return login.backFromDashboard(account, model);
    }
}
