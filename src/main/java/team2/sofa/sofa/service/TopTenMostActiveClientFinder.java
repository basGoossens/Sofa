
package team2.sofa.sofa.service;

import com.sun.source.tree.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.Transaction;
import team2.sofa.sofa.model.dao.ClientDao;

import java.util.*;

@Service
public class TopTenMostActiveClientFinder {

        @Autowired
        ClientDao clientDao;


    public Map<Client, Integer> getTopTenMostActiveClients() {
        //lijst maken met klanten en hun totaal aantal transacties
        LinkedHashMap<Client, Integer> totalNumberTransactionsPerClient = new LinkedHashMap<>();

        for (Client client : clientDao.findAll()
        ) { totalNumberTransactionsPerClient.put(client, client.getTotalNumberOfTransactions());

        }
        //de lijst sorteren op aantal transacties en teruggeven
        totalNumberTransactionsPerClient.entrySet().stream().sorted(Map.Entry.comparingByValue());

        return totalNumberTransactionsPerClient;



        }
    }

