package team2.sofa.sofa.service;

import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Global;

public class BalanceChecker {

    public boolean BalanceCheck(Account account, double bedrag){
        return (account.getBalance() >= bedrag);
    }
}
