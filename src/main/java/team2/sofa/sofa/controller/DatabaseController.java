package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import team2.sofa.sofa.model.EmployeeRole;
import team2.sofa.sofa.service.DbInitializer;

/**
 * basale controller puur voor het vullen van de database met data uit .CSV bestand
 * maakt zodoende gebruik van de service dbInitializer.
 */
@Controller
public class DatabaseController {

    @Autowired
    DbInitializer dbInitializer;

    @GetMapping(value = "initdb")
    public String indexInitdbHandler(Model model) {
        dbInitializer.makeClient(20);
        dbInitializer.fillPrivateAccounts();
        dbInitializer.makeEmployee(EmployeeRole.HOOFD_PARTICULIEREN);
        dbInitializer.makeEmployee(EmployeeRole.HOOFD_MKB);
        dbInitializer.makeEmployee(EmployeeRole.ACCOUNTMANAGER);
        dbInitializer.makeBusiness(5);
        dbInitializer.makeBusinessAccount(5);
        return "index";
    }
}
