package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.service.TopTenHighestBalanceFinder;
import team2.sofa.sofa.service.TopTenMostActiveClientFinder;

import java.util.List;
import java.util.Map;

@Controller
public class EmployeeViewController {

    @Autowired
    AccountDao accountDao;


    @GetMapping(value = "EmployeeViewHandler")
    public String EmployeeViewHandler(@RequestParam(name = "id") int id, Account account, Model model) {
        System.out.println("HEY!" + id);
        System.out.println("HEY" + account);
        Account chosenAccount = accountDao.findAccountById(id);
        model.addAttribute("account", chosenAccount);
        return "dashboard_employee";
    }

}
