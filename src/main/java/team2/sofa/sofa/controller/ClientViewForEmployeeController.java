package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.service.Clientview;



@Controller

public class ClientViewForEmployeeController {

    @Autowired
    Clientview clientview;

//hoort eigenlijk in EmployeeViewController?
    @GetMapping(value = "AccountOverviewHandler")
    public String AccountOverviewHandler(@RequestParam(name = "id") int id, Model model){
        return clientview.accountOverview(id, model);
    }

    @GetMapping(value = "PrivateAccountListHandlerForEmployee")
    public String PrivateAccountListHandlerForEmployee(@RequestParam(name = "id") int id, Model model){
        return clientview.accountFinderEmployee(id, model, false);

    }

    @GetMapping(value = "BusinessAccountListHandlerForEmployee")
    public String BusinessAccountListHandlerForEmployee(@RequestParam(name = "id") int id, Model model){
        return clientview.accountFinderEmployee(id, model, true);
    }


}
