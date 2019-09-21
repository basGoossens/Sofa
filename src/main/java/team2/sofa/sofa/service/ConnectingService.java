package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.Connector;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.ClientDao;
import team2.sofa.sofa.model.dao.ConnectorDao;

import java.util.Map;

@Service
public class ConnectingService {

    @Autowired
    AccountDao accountDao;
    @Autowired
    ClientDao clientDao;
    @Autowired
    ConnectorDao connectorDao;

    public Connector getConnection(int id){
        return connectorDao.findById(id).get();
    }
    public Account getAccount(int id) {
        return accountDao.findAccountById(id);
    }
    public Account saveCoupling(Map body){
        String iban = body.get("bankaccount").toString();
        String user = body.get("newuser").toString();
        String accescode = body.get("accesscode").toString();
        Connector connector = new Connector(user,accescode,iban);
        connectorDao.save(connector);
        return accountDao.findAccountByIban(iban);
    }
    public Model processCoupling(Connector connector, Model model){
        Account account = accountDao.findAccountByIban(connector.getIban());
        Client client = clientDao.findClientByUsername(connector.getUsername());
        account.addClient(client);
        client.addAccount(account);
        clientDao.save(client);
        accountDao.save(account);
        connectorDao.delete(connector);
        model.addAttribute("client", client);
        model.addAttribute("account", new Account());
        return model;
    }
}
