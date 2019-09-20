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

import java.util.Map;

@Controller
public class ConnectController {

    @Autowired
    ConnectorDao connectorDao;
    @Autowired
    AccountDao accountDao;
    @Autowired
    ClientDao clientDao;

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

    @PostMapping(value = "NewConnection")
    public String matchAccounts(@RequestParam int id, Model model){
        model.addAttribute("connection", connectorDao.findById(id).get());
        return "connect_accounts";
    }

    @PostMapping(value = "ConnectAccount/validate")
    public String checkMatch(@RequestParam Map<String, Object> body, Model model){
        Connector connector = connectorDao.findById((Integer.valueOf(body.get("idconnect").toString()))).get();
        if (connector.getSecurityCode().equals(body.get("accesscode").toString())){
            Account account = accountDao.findAccountByIban(connector.getIban());
            Client client = clientDao.findClientByUsername(connector.getUsername());
            account.addClient(client);
            client.addAccount(account);
            clientDao.save(client);
            accountDao.save(account);
            connectorDao.delete(connector);
            model.addAttribute("client", client);
            model.addAttribute("account", new Account());
            return "client_view";
        }
        else {
            model.addAttribute("wrong", "accescode is niet juist");
            return "connect_accounts";
        }
    }
}
