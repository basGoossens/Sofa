package team2.sofa.sofa.controller;


import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Address;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.AddressDao;
import team2.sofa.sofa.model.dao.ClientDao;
import team2.sofa.sofa.service.IBANGenerator;
import team2.sofa.sofa.service.NewAccountChecker;

@Controller
public class NewClientController {

    @Autowired
    AccountDao accountDao;

    @Autowired
    ClientDao clientDao;

    @Autowired
    AddressDao addressDao;

    @Autowired
    NewAccountChecker newAccountChecker;

    @GetMapping(value = "new_account")
    public String newClientHandler(Model model){
    Client client = new Client();
    model.addAttribute("client", client);
        return "new_account";
    }


    @PostMapping(value = "newAccountHandler")
    public String loginHandler(@ModelAttribute Client client, Model model) {
        Address tempAddress = client.getAddress();
        newAccountChecker.AddressExistsChecker(tempAddress);
        client = newAccountChecker.SSNNameExistsChecker(client);
        newAccountChecker.usernameExistsChecker(client);

        Client savedClient = clientDao.findClientByUsername(client.getUsername());
        IBANGenerator newIBAN = new IBANGenerator();
        Account newAccount = new Account();
        newAccount.setIBAN(newIBAN.getIBAN());
        newAccount.addClient(savedClient);
        savedClient.addAccount(newAccount);
        clientDao.save(savedClient);
        accountDao.save(newAccount);
        return "login";
    }
    }