package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.dao.AccountDao;

import java.util.List;

@Service
public class TopTenHighestBalanceFinder {

    public TopTenHighestBalanceFinder(){super();}

    @Autowired
    AccountDao accountDao;

    public List<Account> getTopTenHighestBalance(){
        List<Account> topTen = accountDao.findTop10ByOrderByBalanceDesc();
        return topTen;
    }
}
