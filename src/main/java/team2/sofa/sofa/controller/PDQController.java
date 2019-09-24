package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.PaymentData;
import team2.sofa.sofa.model.Transaction;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.service.FundTransfer;
import team2.sofa.sofa.service.SerializationService;

@RestController
public class PDQController {

    @Autowired
    SerializationService serializationService;

    @Autowired
    FundTransfer fundTransfer;

    @Autowired
    AccountDao accountDao;


    @GetMapping(value = "/paymentmachine/payment")
    public void checkTransaction(@PathVariable String json) {
        PaymentData paymentData = serializationService.deserializePaymentData(json);
        Account creditAccount = accountDao.findAccountByIban(paymentData.getCreditAccount());
        Account debitAccount = accountDao.findAccountByIban(paymentData.getDebitAccount());
        fundTransfer.checkBalance(paymentData.getAmount(), debitAccount);
        Transaction transaction = new Transaction(paymentData.getAmount(), paymentData.getDescription(),
                paymentData.getDateTime().toLocalDate(), creditAccount, debitAccount);

    }



/*
    @GetMapping(value = "/members/{id}")
    public String getMember(@PathVariable int id) {
        Member member = memberService.findById(id);
        String json = memberService.serialize(member);
        return json;
    }
*/


}
