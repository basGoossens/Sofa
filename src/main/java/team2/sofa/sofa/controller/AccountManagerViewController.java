package team2.sofa.sofa.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountManagerViewController {

    @GetMapping(value = "requestPDQ")
    public String RequestPDQ(@RequestParam(name = "id") int id, Model model) {
        return RequestPDQ(id, model);

    }
}

