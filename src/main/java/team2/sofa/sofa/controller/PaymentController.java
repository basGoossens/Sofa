package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import team2.sofa.sofa.service.FundTransfer;

@Controller
public class PaymentController {


    @PostMapping(value = "transferMoneyHandler")
    public String transferMoneyHandler (@ModelAttribute Model model){
        return null;
    }
}
