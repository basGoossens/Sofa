package team2.sofa.sofa.model.dao;

import org.springframework.data.repository.CrudRepository;
import team2.sofa.sofa.model.Transaction;

import java.util.List;

public interface TransactionDao extends CrudRepository<Transaction, Integer> {

    List<Transaction> findTransactionsByCreditAccount(int idcredit);

    List<Transaction> findTransactionsByDebitAccount(int iddebit);
}
