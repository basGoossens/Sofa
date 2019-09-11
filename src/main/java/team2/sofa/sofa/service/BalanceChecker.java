package team2.sofa.sofa.service;

import team2.sofa.sofa.model.Account;

public class BalanceChecker {

    public static boolean BalanceCheck(Account account, double bedrag){
        return (account.getBalance() >= bedrag);
    }
}
