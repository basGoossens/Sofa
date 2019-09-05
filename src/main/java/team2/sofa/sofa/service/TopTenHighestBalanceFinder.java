package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.BusinessAccountDao;

import java.util.List;

@Service
public class TopTenHighestBalanceFinder {

    public TopTenHighestBalanceFinder(){super();}

    @Autowired
    AccountDao accountDao;

    @Autowired
    BusinessAccountDao businessAccountDao;

    //de tien particuliere rekeningen met het hoogste saldo
    public List<Account> getTopTenHighestBalance(){
        List<Account> topTen = accountDao.findTop10ByOrderByBalanceDesc();
        return topTen;
    }


    //de tien zakelijke rekeningen met het hoogste saldo
    public List<Account> getTopTenHighestBalanceBusiness(){
        List<Account> topTen = businessAccountDao.findTop10ByBusinessIsNotNullOrderByBalanceDesc();
        return topTen;
    }
}
