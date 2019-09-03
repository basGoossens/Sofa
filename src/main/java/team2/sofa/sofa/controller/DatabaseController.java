package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import team2.sofa.sofa.model.EmployeeRole;
import team2.sofa.sofa.service.DbInitializer;

@Controller
public class DatabaseController {

    @Autowired
    DbInitializer dbInitializer;

    @GetMapping(value = "initdb")
    public String indexInitdbHandler(Model model) {
        dbInitializer.makeClient();
        dbInitializer.fillAccounts();
        dbInitializer.makeEmployee(4000, EmployeeRole.HOOFD_PARTICULIEREN);
        dbInitializer.makeEmployee(4001, EmployeeRole.HOOFD_MKB);
        dbInitializer.makeEmployee(4002, EmployeeRole.ACCOUNTMANAGER);
        return "index";
    }
}
