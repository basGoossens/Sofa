package team2.sofa.sofa.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class TransactionForm {

    @NotBlank (message = "vul naam rekeninghouder in")
    String name;
    @Positive (message = "vul een positief bedrag in")
    double amount;
    @NotBlank (message = "vul een omschrijving in")
    String description;
    @NotBlank (message = "vul een geldig IBAN in behorend bij naam rekeninghouder")
    String creditAccount;
    @NotBlank (message = "vul eigen IBAN in")
    String debetAccount;

    public TransactionForm() {
        this("", 0, "", "", "");
    }

    public TransactionForm(String name, double amount, String description, String creditAccount, String debetAccount) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.creditAccount = creditAccount;
        this.debetAccount = debetAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
    }

    public String getDebetAccount() {
        return debetAccount;
    }

    public void setDebetAccount(String debetAccount) {
        this.debetAccount = debetAccount;
    }
}