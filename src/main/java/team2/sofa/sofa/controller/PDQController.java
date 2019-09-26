package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.PaymentData;
import team2.sofa.sofa.model.Transaction;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.BusinessAccountDao;
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

    @Autowired
    BusinessAccountDao businessAccountDao;

    @PostMapping("/paymentmachine/payment/")
    public String TransactionPostController(@RequestBody PaymentData paymentData){
        String returnJson;
        Account creditAccount = businessAccountDao.findBusinessAccountByIban(paymentData.getCreditAccount());
        Account debitAccount = accountDao.findAccountByIban(paymentData.getDebitAccount());
        if (fundTransfer.insufficientBalance(paymentData.getAmount(), debitAccount)){
            returnJson = "Failed";
        } else {
            try {
                Transaction transaction = new Transaction(paymentData.getAmount(), paymentData.getDescription(),
                        paymentData.getDate(), creditAccount, debitAccount);
                fundTransfer.storeTransaction(debitAccount, creditAccount, transaction);
                returnJson = "Approved";
            } catch (Exception e) {
                System.out.println(e);
                returnJson = "Failed";
            }
        }
        return returnJson;
    }

}
