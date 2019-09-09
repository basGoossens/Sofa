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
    // samen nooit meer dan hoeveelheid in dataset ;)
    private final int CLIENTS = 50;
    private final int BUSINESS = 20;


    @Autowired
    DbInitializer dbInitializer;

    @GetMapping(value = "initdb")
    public String indexInitdbHandler(Model model) {
        dbInitializer.makeClient(CLIENTS);
        dbInitializer.fillPrivateAccounts();
        dbInitializer.makeEmployee(EmployeeRole.HOOFD_PARTICULIEREN, 1);
        dbInitializer.makeEmployee(EmployeeRole.HOOFD_MKB,2);
        dbInitializer.makeEmployee(EmployeeRole.ACCOUNTMANAGER, 3);
        dbInitializer.makeBusiness(BUSINESS, CLIENTS);
        return "index";
    }
}
