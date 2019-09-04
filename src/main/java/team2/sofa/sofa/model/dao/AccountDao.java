package team2.sofa.sofa.model.dao;

import org.springframework.data.repository.CrudRepository;
import team2.sofa.sofa.model.Account;

import java.awt.*;
import java.util.List;

public interface AccountDao extends CrudRepository<Account, Integer> {

    public List<Account> findTop10ByOrderByBalanceDesc();

}
