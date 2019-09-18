package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.service.TopTenMostActiveClient;

@Controller
public class EmployeeViewController {


    @Autowired
    TopTenMostActiveClient topTen;


    @GetMapping(value = "TenMostActiveHandler")
    public String TenMostActiveHandler(@RequestParam(name = "id") int id, Model model) {
        return topTen.mostActiveClientBuilder(id, model);
    }

}
