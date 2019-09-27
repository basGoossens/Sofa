package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.ConnectorDao;
import team2.sofa.sofa.service.Clientview;
import team2.sofa.sofa.service.FundTransfer;
import team2.sofa.sofa.service.Login;

@Controller
@SessionAttributes({"sessionclient", "connect", "nrBusiness", "nrPrivate", "account"})
public class DashboardClientController {

    @Autowired
    FundTransfer fundTransfer;
    @Autowired
    Login login;
    @Autowired
    AccountDao accountDao;
    @Autowired
    ConnectorDao connectorDao;
    @Autowired
    ClientViewController clientViewController;
    @Autowired
    Clientview clientview;

    @PostMapping(value = "TransferHandler")
    public String transfer(@RequestParam int id, Model model) {
        model = fundTransfer.readyTransaction(id, model);
        return "money_transfer";
    }

    @PostMapping(value = "backToOverview")
    public String backToOverview(Account account, Model model) {
        return login.backFromDashboard(account, model);
    }

    @PostMapping(value="backToClientView")
    public String backToClientView(@RequestParam int id, Model model) {
        clientViewController.fillClientView(clientview.findClientById(id), model);
        return "redirect:/loadClientView";
    }
}
