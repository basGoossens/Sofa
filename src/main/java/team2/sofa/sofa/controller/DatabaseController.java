package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import team2.sofa.sofa.service.DbInitializer;

@Controller
public class DatabaseController {

    @Autowired
    DbInitializer dbInitializer;

    @GetMapping(value = "initdb")
    public String indexInitdbHandler(Model model) {
        dbInitializer.makeClient();
        return "index";
    }
}
