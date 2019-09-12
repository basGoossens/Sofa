package team2.sofa.sofa.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class TransactionForm {

    @NotBlank (message = "vul naam rekeninghouder in")
    String name;
    @Positive (message = "vul een positief bedrag in")
    BigDecimal amount;
    @NotBlank (message = "vul een omschrijving in")
    String description;
    @NotBlank (message = "vul een geldig IBAN in behorend bij naam rekeninghouder")
    String creditAccount;
    @NotBlank (message = "vul eigen IBAN in")
    String debetAccount;

    public TransactionForm() {
        this("", null, "", "", "");
    }

    public TransactionForm(String name, BigDecimal amount, String description, String creditAccount, String debetAccount) {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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
