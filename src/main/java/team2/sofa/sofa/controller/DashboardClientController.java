package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.Transaction;
import team2.sofa.sofa.model.dao.TransactionDao;

@Controller
public class DashboardClientController {

    @Autowired
    TransactionDao transactionDao;


    /*@GetMapping(value = "clientViewHandler")
    public String clientViewHandler(@RequestParam(name = "id") int id, Account account, Model model) {
        System.out.println("HEY!" + id);
        System.out.println("HEY" + account);
        Account chosenAccount = accountDao.findAccountById(id);
        model.addAttribute("account", chosenAccount);
        return "dashboard_client"*/

    //Ga terug naar de overview pagina van Client//
    @GetMapping(value = "backToClientView")
    public String backToClientView(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);
        return "client-view";

    }

}
