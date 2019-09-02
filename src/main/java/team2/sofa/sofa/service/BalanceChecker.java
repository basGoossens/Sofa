package team2.sofa.sofa.service;

import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Transaction;

public class BalanceChecker {

    public boolean sufficientBalance(Account debitAccount, Transaction transaction){
        if (debitAccount.getBalance() > transaction.getAmount()) {
            return true;
        } else {
            return false;
        }
    }

}
