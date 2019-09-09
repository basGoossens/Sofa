package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.BusinessAccount;
import team2.sofa.sofa.model.PrivateAccount;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.BusinessAccountDao;
import team2.sofa.sofa.model.dao.PrivateAccountDao;

import java.util.List;
import java.util.Optional;


@Controller
public class ClientViewForEmployeeController {

    @Autowired
    AccountDao accountDao;

    @Autowired
    PrivateAccountDao privateAccountDao;

    @Autowired
    BusinessAccountDao businessAccountDao;

    @GetMapping(value = "AccountOverviewHandler")
    public String AccountOverviewHandler(@RequestParam(name = "id") int id,Account account, Model model){
        System.out.println("HEY!" + id);
        System.out.println("HEY" + account);
        Account chosenAccount = accountDao.findAccountById(id);
        model.addAttribute("account", chosenAccount);
        return "dashboard_employee";
    }

    @GetMapping(value = "PrivateAccountListHandlerForEmployee")
    public String PrivateAccountListHandlerForEmployee(@RequestParam(name = "id") int id, Model model){
        PrivateAccount chosenAccount = privateAccountDao.findAccountById(id);
        model.addAttribute("account", chosenAccount);
        return "dashboard_employee";
    }

    @GetMapping(value = "BusinessAccountListHandlerForEmployee")
    public String BusinessAccountListHandlerForEmployee(@RequestParam(name = "id") int id, Model model){
        BusinessAccount chosenAccount = businessAccountDao.findAccountById(id);
        model.addAttribute("account", chosenAccount);
        return "dashboard_employee";
    }


}
