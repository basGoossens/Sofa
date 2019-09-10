package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.ClientDao;

import java.util.ArrayList;

@Controller
public class EmployeeViewController {


    @Autowired
    ClientDao clientDao;


    @GetMapping(value = "TenMostActiveHandler")
    public String TenMostActiveHandler(@RequestParam(name = "id") int id, Client client, Model model) {
        Client chosenClient = clientDao.findClientById(id);
        //            Accounts van klant scheiden in business en private
        ArrayList<Account> listPrivateAccounts = new ArrayList<>();
        ArrayList<Account> listBusinessAccounts = new ArrayList<>();
        for (Account a:chosenClient.getAccounts()
        ) { if (a.isBusinessAccount()) {listBusinessAccounts.add(a);}
        else {listPrivateAccounts.add(a);}
        }
        model.addAttribute("listPrivateAccounts", listPrivateAccounts);
        model.addAttribute("listBusinessAccounts", listBusinessAccounts);
        model.addAttribute("client", chosenClient);
        return "client_view_for_employee";
    }

}
