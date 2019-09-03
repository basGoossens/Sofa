package team2.sofa.sofa.service;

import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Global;

public class BalanceChecker {

    public boolean BalanceCheck(double bedrag){
        //Haal current account uit Global
        Account account = Global.getCurrentAccount();
        return (account.getBalance() >= bedrag);
    }
}
