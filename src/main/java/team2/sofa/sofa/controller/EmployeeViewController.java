package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.service.TopTenHighestBalanceFinder;

import java.util.List;

@Controller
public class EmployeeViewController {

    @Autowired
    TopTenHighestBalanceFinder topTenHighestBalanceFinder;


    @GetMapping(value = "employee_view")
    public String EmployeeViewHandler(Model model) {
        List<Account> topTenHighest = topTenHighestBalanceFinder.getTopTenHighestBalance();
        model.addAttribute("tenHighestBalance", topTenHighest);
        return "employee_view";
    }

}
