package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.Transaction;
import team2.sofa.sofa.model.TransactionForm;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.ClientDao;
import team2.sofa.sofa.model.dao.TransactionDao;

import java.util.List;

@Service
public class FundTransfer {

    @Autowired
    TransactionDao transactionDao;

    @Autowired
    AccountDao accountDao;

    @Autowired
    ClientDao clientDao;

    public FundTransfer() {
        super();
    }

    /**
     * wordt gebruikt om form in Money_transfer voor te bereiden
     *
     * @param id
     * @param model
     * @return
     */
    public String readyTransaction(int id, Model model) {
        TransactionForm t = new TransactionForm();
        Account a = accountDao.findAccountById(id);
        t.setDebetAccount(a.getIban());
        model.addAttribute("transactionForm", t);
        return "money_transfer";
    }

    /**
     * helper methode om juiste data in te laden in model voor dashboard na uitvoeren van betaling
     *
     * @param transactionForm
     * @param model
     * @return
     */
    public String readyDashboard(TransactionForm transactionForm, Model model) {
        Account a = accountDao.findAccountByIban(transactionForm.getDebetAccount());
        model.addAttribute("account", a);
        return "dashboard_client";
    }

    /**
     * verwerkt transactie nadat alles is gecontroleerd op juiste input
     *
     * @param transactionForm
     */
    public void procesTransaction(TransactionForm transactionForm) {
        double amount = transactionForm.getAmount();
        Account debit = accountDao.findAccountByIban(transactionForm.getDebetAccount());
        Account credit = accountDao.findAccountByIban(transactionForm.getCreditAccount());
        Transaction transaction = new Transaction(amount, transactionForm.getDescription(), credit, debit);
        storeTransaction(debit, credit, transaction);
    }

    /**
     * helper methode die wordt gebruikt door procesTransaction() om de Transactie daadwerkelijk in DB op te slaan
     * en de balance in debit en credit rekening aan te passen en te updaten in DB
     *
     * @param debit
     * @param credit
     * @param transaction
     */
    private void storeTransaction(Account debit, Account credit, Transaction transaction) {
        debit.addTransaction(transaction);
        credit.addTransaction(transaction);
        debit.lowerBalance(transaction.getAmount());
        credit.raiseBalance(transaction.getAmount());
        transactionDao.save(transaction);
        accountDao.save(debit);
        accountDao.save(credit);
    }

    /**
     * methode die vanuit controller wordt aangeroepen voor controle op voldoende saldo op debit rekening
     *
     * @param transactionForm afkomstig vanuit controller PaymentController
     * @return
     */
    public boolean checkBalance(TransactionForm transactionForm) {
        double amount = transactionForm.getAmount();
        Account account = accountDao.findAccountByIban(transactionForm.getDebetAccount());
        return checkBalance(amount, account);
    }

    /**
     * methode die daadwerkelijk controleert of er voldoende saldo is op debitrekening
     *
     * @param amount  bedrag dat is ingevoerd in formulier money_transfer
     * @param account het debitAccount dat gebruikt is in het formulier money_transfer
     * @return
     */
    private boolean checkBalance(double amount, Account account) {
        double balance = account.getBalance();
        return (balance - amount) < 0;
    }

    /**
     * controle of ingevoerd IBAN creditAccount een bestaand IBAN is
     *
     * @param creditIban het IBAN dat is ingevoerd in money_transfer form in veld CreditAccount
     * @return
     */
    public boolean checkToAccount(String creditIban) {
        return accountDao.existsAccountByIban(creditIban);
    }

    public boolean nameCheckIban(String lastName, String creditIban) {
        Account creditAccount = accountDao.findAccountByIban(creditIban);
        List<Client> owners = creditAccount.getOwners();
        for (Client owner : owners) {
            if (owner.getLastName().equals(lastName)) {
                return true;
            }
        }
        return false;
    }
}
