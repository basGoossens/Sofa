package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.PaymentData;
import team2.sofa.sofa.model.Transaction;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.BusinessAccountDao;
import team2.sofa.sofa.model.dao.PdqDao;

@Service
public class SerializationService {


    @Autowired
    AccountDao accountDao;

    @Autowired
    BusinessAccountDao businessAccountDao;

    @Autowired
    PdqDao pdqDao;

    @Autowired
    FundTransfer fundTransfer;

/*    public PaymentData deserializePaymentData(String json) {
        Gson gson = new Gson();
        PaymentData paymentData = gson.fromJson(json, PaymentData.class);
        return paymentData;
    }

    public PaymentMachineConnectionData deserializePaymentMachineConnectionData(String json) {
        Gson gson = new Gson();
        PaymentMachineConnectionData pmcd = gson.fromJson(json, PaymentMachineConnectionData.class);
        return pmcd;
    }*/

    public void doRestTransaction(PaymentData paymentData) throws Exception {
        Account creditAccount = accountDao.findAccountByIban(paymentData.getCreditAccount());
        Account debitAccount = accountDao.findAccountByIban(paymentData.getDebitAccount());
        if (creditAccount == null || debitAccount == null) {
            throw new Exception("ongeldig rekeningnummer");
        } else if (fundTransfer.insufficientBalance(paymentData.getAmount(), debitAccount)) {
            throw new Exception("balans onvoldoende");
        } else {
            try {
                Transaction transaction = new Transaction(paymentData.getAmount(), paymentData.getDescription(),
                        String.valueOf(paymentData.getDate()), creditAccount, debitAccount);
                fundTransfer.storeTransaction(debitAccount, creditAccount, transaction);
            } catch (Exception e) {
                throw new Exception("intern probleem bij de bank");
            }
        }
    }

}
