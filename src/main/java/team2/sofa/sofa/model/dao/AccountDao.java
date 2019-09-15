package team2.sofa.sofa.model.dao;

import org.springframework.data.repository.CrudRepository;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Client;

import java.awt.*;
import java.util.List;

public interface AccountDao extends CrudRepository<Account, Integer> {

    Account findAccountById(int id);

    Account findAccountByIban(String iban);

    boolean existsAccountByIban(String iban);

}
