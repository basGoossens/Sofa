
package team2.sofa.sofa.service;

import com.sun.source.tree.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.Transaction;
import team2.sofa.sofa.model.dao.ClientDao;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class TopTenMostActiveClientFinder {

    @Autowired
    ClientDao clientDao;

    public List<Client> getTopTenMostActiveClients(){

        return clientDao.findTop10ByOrderByTotalNumberOfTransactionsDesc();
    }

}

