package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team2.sofa.sofa.model.PaymentData;
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






/*    @RequestMapping(value = "/paymentmachine/payment/", method = RequestMethod.POST)
    public String pinTransactionHandler(@RequestBody PaymentData paymentData){
        return "Approved";
    }*/

    @PostMapping("/paymentmachine/payment/")
    public String postController(@RequestBody PaymentData paymentData){
        System.out.println(paymentData.getDescription());
        return "Approved";
    }




/*    @PostMapping(value = "/paymentmachine/payment/{json}")
    public String checkTransactionHandler(@RequestBody String json) {
        PaymentData paymentData = serializationService.deserializePaymentData(json);
        System.out.println(json);
        return "Approved";*/
      /*  PaymentData paymentData = serializationService.deserializePaymentData(json);
        Account creditAccount = accountDao.findAccountByIban(paymentData.getCreditAccount());
        Account debitAccount = accountDao.findAccountByIban(paymentData.getDebitAccount());
        String returnJson;
        if (fundTransfer.insufficientBalance(paymentData.getAmount(), debitAccount)){
            returnJson = "Failed";
        } else {
            returnJson = "Approved";
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                LocalDate date = LocalDate.parse(paymentData.getDate(), formatter);
                Transaction transaction = new Transaction(paymentData.getAmount(), paymentData.getDescription(),
                        date, creditAccount, debitAccount);
            } catch (Exception e) {
                System.out.println(e);
            }

            //fundTransfer.storeTransaction(debitAccount, creditAccount, transaction);
        }
        return returnJson;*/



}
