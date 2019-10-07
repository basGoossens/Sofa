package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team2.sofa.sofa.model.PaymentData;
import team2.sofa.sofa.model.PaymentMachineConnectionData;
import team2.sofa.sofa.service.FundTransfer;
import team2.sofa.sofa.service.PdqInteractionService;

@RestController
public class PDQController {

    @Autowired
    PdqInteractionService pdqInteractionService;

    @Autowired
    FundTransfer fundTransfer;

    @PostMapping("/paymentmachine/payment/")
    public String TransactionPostController(@RequestBody PaymentData paymentData){
        String returnJson;
        try {
            int transactionId = pdqInteractionService.doPdqTransaction(paymentData);
            returnJson ="Approved" + transactionId;
        } catch (Exception e){
            returnJson = "Failed " + e.getMessage();

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
        String returnJson;
        try {
            String eightDigitCode = pdqInteractionService.doPdqCoupling(paymentMachineConnectionData);
            returnJson = eightDigitCode;
        } catch (Exception e){
            returnJson = "Failed " + e;

        }
/*        try {
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
        }*/
        return returnJson;
    }

}
