package team2.sofa.sofa.model.dao;

import org.springframework.data.repository.CrudRepository;
import team2.sofa.sofa.model.Account;

public interface AccountDao extends CrudRepository<Account, Integer> {

}
