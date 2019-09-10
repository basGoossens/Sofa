package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.PrivateAccountDao;
import team2.sofa.sofa.model.dao.TransactionDao;
import team2.sofa.sofa.service.BalanceChecker;

import java.util.List;

@Service
public class FundTransfer {

    @Autowired
    TransactionDao transactionDao;

    @Autowired
    PrivateAccountDao privateAccountDao;

    public FundTransfer(){super();}

    public String readyTransaction(int id, Model model) {
        Transaction t = new Transaction();
        PrivateAccount a = privateAccountDao.findAccountById(id);
        model.addAttribute("transaction", t);
        model.addAttribute("account", a);
        model.addAttribute("client", a.getOwners().get(0));
        return "money_transfer";
    }

    public void procesTransaction(Transaction transaction){
        double amount = transaction.getAmount();
        String ibanDebit = transaction.getDebitAccount().getIban();
        String ibanCredit = transaction.getCreditAccount().getIban();
        PrivateAccount debit = privateAccountDao.findAccountByIban(ibanDebit);
        PrivateAccount credit = privateAccountDao.findAccountByIban(ibanCredit);
        if (checkBalance(transaction)){
            transaction.setDebitAccount(debit);
            transaction.setCreditAccount(credit);
            debit.addTransaction(transaction);
            credit.addTransaction(transaction);
            debit.lowerBalance(amount);
            credit.raiseBalance(amount);
            transactionDao.save(transaction);
            privateAccountDao.save(debit);
            privateAccountDao.save(credit);
        }
    }

    public boolean checkBalance(Transaction transaction) {
        double balance = transaction.getDebitAccount().getBalance();
        double amount = transaction.getAmount();
        return (balance - amount) < 0;
    }

    public void fundTransfer(Account fromAccount, Account toAccount, Client toClient, double bedrag) {
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
