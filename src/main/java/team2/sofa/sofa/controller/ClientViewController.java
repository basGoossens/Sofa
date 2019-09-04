package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.dao.AccountDao;

import java.util.List;
import java.util.Optional;


@Controller
public class ClientViewController {

    @Autowired
    AccountDao accountDao;

    @GetMapping(value = "clientViewHandler")
    public String clientViewHandler(@RequestParam(name = "id") int id,Account account, Model model){
        System.out.println("HEY!" + id);
        System.out.println("HEY" + account);
        Account chosenAccount = accountDao.findAccountById(id);
        model.addAttribute("account", chosenAccount);
        return "dashboard_client";
    }

}
