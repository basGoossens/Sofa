package team2.sofa.sofa.service;

import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.User;
import team2.sofa.sofa.service.BalanceChecker;

import java.util.List;

public class FundTransfer {

    public FundTransfer(Account fromAccount, Account toAccount, Client toClient, double bedrag) {
        BalanceChecker checker = new BalanceChecker();
        boolean balanceOk = checker.BalanceCheck(fromAccount, bedrag);
        boolean toAccountOk = accountChecker(toAccount, toClient);

        if (!toAccountOk) { //report error en breek transactie af indien check niet OK
            reportError();
            return;
        }

        if (balanceOk) { //report error en breek transactie af indien check niet OK
            fromAccount.setBalance(fromAccount.getBalance() - bedrag);
            toAccount.setBalance(toAccount.getBalance() + bedrag);
        }
        else reportError();
    }

    private boolean accountChecker(Account toAccount, Client client){
        List<Account> accountList = client.getAccounts();
        boolean accountOk = false;
        for (Account item: accountList){
            if (toAccount.equals(item)) return accountOk = true;
        }
        return accountOk;
    }

    private void reportError() {
        //todo Report error to user
        System.out.print("Er is onvoldoende saldo voor een transfer.");
        System.out.print("Of kan niet overmaken vanwege ongeldig account");
    }
}
