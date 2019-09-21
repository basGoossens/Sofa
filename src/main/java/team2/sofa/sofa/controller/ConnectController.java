package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.Connector;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.ClientDao;
import team2.sofa.sofa.model.dao.ConnectorDao;
import team2.sofa.sofa.service.ConnectingService;

import java.util.Map;

@Controller
public class ConnectController {

    @Autowired
    ConnectingService cs;

    @Autowired
    ConnectorDao connectorDao;
    @Autowired
    AccountDao accountDao;
    @Autowired
    ClientDao clientDao;

    @PostMapping(value = "ConnectAccount")
    public String connectAccounts(@RequestParam int id, Model model){
        Account account = cs.getAccount(id);
        model.addAttribute("account", account);
        return "connect_accounts";
    }
    @PostMapping(value = "ConnectForm")
    public String connectHandeler(@RequestParam Map<String, Object> body, Model model){
        Account account = cs.saveCoupling(body);
        model.addAttribute("account", account);
        return "dashboard_client";
    }

    @PostMapping(value = "NewConnection")
    public String matchAccounts(@RequestParam int id, Model model){
        Connector connector = cs.getConnection(id);
        model.addAttribute("connection", connector);
        return "connect_accounts";
    }

    @PostMapping(value = "ConnectValidate")
    public String checkMatch(@RequestParam Map<String, Object> body, Model model){
        int id = Integer.valueOf(body.get("idconnect").toString());
        Connector connector = cs.getConnection(id);
        if (connector.getSecurityCode().equals(body.get("accesscode").toString())){
            model = cs.processCoupling(connector, model);
            return "client_view";
        }
        else {
            model.addAttribute("connection",connector);
            model.addAttribute("wrong", "accescode is niet juist");
            return "connect_accounts";
        }
    }
}
