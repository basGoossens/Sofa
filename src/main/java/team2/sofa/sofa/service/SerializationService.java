package team2.sofa.sofa.service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.PaymentData;
import team2.sofa.sofa.model.PaymentMachineConnectionData;

@Service
public class SerializationService {

    public PaymentData deserializePaymentData(String json) {
        Gson gson = new Gson();
        PaymentData paymentData = gson.fromJson(json, PaymentData.class);
        return paymentData;
    }

    public PaymentMachineConnectionData deserializePaymentMachineConnectionData(String json) {
        Gson gson = new Gson();
        PaymentMachineConnectionData pmcd = gson.fromJson(json, PaymentMachineConnectionData.class);
        return pmcd;
    }
}
