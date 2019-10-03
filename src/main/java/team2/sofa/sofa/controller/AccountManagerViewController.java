package team2.sofa.sofa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Pdq;
import team2.sofa.sofa.model.dao.PdqDao;
import team2.sofa.sofa.service.RequestPDQ;

@Controller
public class AccountManagerViewController {

    @Autowired
    RequestPDQ rp;

    @Autowired
    PdqDao pdqDao;


    @PostMapping(value = "ConnectPDQ")
    public String requestPDQ(@RequestParam String iban, Model model) {
        Account account = rp.getAccount(iban);
        String code = rp.generateFiveDigit();
        String eightcode = rp.generateEightDigit();
        Pdq temp = new Pdq();
        temp.setFiveDigitcode(code);
        temp.setEightDigitcode(eightcode);
        temp.setCoupledAccount(account);
        pdqDao.save(temp);
        model.addAttribute("fiveDigits", code);
        model.addAttribute("account", account);
        return "receive_connecting_code";
    }

    @PostMapping(value = "ConnectEightPDQ")
    public String requestEightPDQ(@RequestParam String iban, Model model) {
        Account account = rp.getAccount(iban);
        String code = rp.generateEightDigit();
        model.addAttribute("eightDigit", code);
        model.addAttribute("account", account);
        return "receive_connecting_code";
    }
}

