package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Pdq;
import team2.sofa.sofa.model.dao.AccountDao;

import java.util.Random;

@Service
public class RequestPDQ {

    @Autowired
    AccountDao accountDao;

    public Account getAccount(String iban) {
        return accountDao.findAccountByIban(iban);
    }

    public String generateFiveDigit() {
        Random random = new Random();
        int num = random.nextInt(100000);
        String fiveDigitcode = String.format("%05d", num);

        return fiveDigitcode;
    }

    public String generateEightDigit() {
        Random random = new Random();
        int num = random.nextInt(100000000);
        String eightDigitcode = String.format("%08d", num);
        return eightDigitcode;

    }
}