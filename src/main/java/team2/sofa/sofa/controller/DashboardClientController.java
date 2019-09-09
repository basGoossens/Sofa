package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

}
