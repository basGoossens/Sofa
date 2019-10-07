package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.BusinessAccountDao;
import team2.sofa.sofa.model.dao.PdqDao;
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

    @Autowired
    PdqDao pdqDao;

    @PostMapping("/paymentmachine/payment/")
    public String TransactionPostController(@RequestBody PaymentData paymentData){
        String returnJson;
        try {
            serializationService.doRestTransaction(paymentData);
            returnJson ="Approved";
        } catch (Exception e){
            returnJson = "Failed" + e.getMessage();

/*        Account creditAccount = accountDao.findAccountByIban(paymentData.getCreditAccount());
        Account debitAccount = accountDao.findAccountByIban(paymentData.getDebitAccount());
        if (creditAccount == null || debitAccount == null){
            returnJson = "Failed";
        } else if (fundTransfer.insufficientBalance(paymentData.getAmount(), debitAccount)){
            returnJson = "Failed";
        } else {
            try {
                Transaction transaction = new Transaction(paymentData.getAmount(), paymentData.getDescription(),
                        String.valueOf(paymentData.getDate()), creditAccount, debitAccount);
                fundTransfer.storeTransaction(debitAccount, creditAccount, transaction);
                returnJson = "Approved";
            } catch (Exception e) {
                System.out.println(e);
                returnJson = "Failed";
            }*/
        }
        return returnJson;
        // make a function in service that returns void, but raises exceptions (multiple options) and catch them in controller
    }

    @PostMapping("/paymentmachine/coupling/")
    public String CouplingPostController(@RequestBody PaymentMachineConnectionData paymentMachineConnectionData){
        String returnJson = "";
        System.out.println(paymentMachineConnectionData.getAccount() + " " + paymentMachineConnectionData.getFiveDigitCode());
        try {
            Pdq pdq = pdqDao.findPdqByFiveDigitcode(paymentMachineConnectionData.getFiveDigitCode());
            if (pdq.getFiveDigitcode().equals(paymentMachineConnectionData.getFiveDigitCode()) ||
                    pdq.getCoupledAccount().getIban().equals(paymentMachineConnectionData.getAccount())){
                returnJson = pdq.getEightDigitcode();
            } else {
                returnJson = "Failed";
            }
        } catch (Exception e){
            System.out.println("five-digit-code not valid: " + e);
            returnJson = "Failed";
        }
        return returnJson;
    }

}
