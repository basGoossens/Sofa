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
        }
        return returnJson;
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
        return returnJson;
    }

}
