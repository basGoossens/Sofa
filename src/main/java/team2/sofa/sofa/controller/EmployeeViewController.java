package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    TopTenHighestBalanceFinder topTenHighestBalanceFinder;

    @Autowired
    TopTenMostActiveClientFinder topTenMostActiveClientFinder;


    @GetMapping(value = "employee_view")
    public String EmployeeViewHandler(Model model) {
        List<Account> topTenHighest = topTenHighestBalanceFinder.getTopTenHighestBalance();
        Map<Client, Integer> topTenMostActive = topTenMostActiveClientFinder.getTopTenMostActiveClients();
        model.addAttribute("tenHighestBalance", topTenHighest);
        model.addAttribute("tenMostActive", topTenMostActive);
        return "employee_view";
    }

}
