
package team2.sofa.sofa.service;

import com.sun.source.tree.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.Transaction;
import team2.sofa.sofa.model.dao.ClientDao;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class TopTenMostActiveClient {

    @Autowired
    ClientDao clientDao;

    public List<Client> getTopTenMostActiveClients(){

        return clientDao.findTop10ByOrderByTotalNumberOfTransactionsDesc();
    }

    public String mostActiveClientBuilder(int id, Model model) {
        Client chosenClient = clientDao.findClientById(id);
        //            Accounts van klant scheiden in business en private
        ArrayList<Account> listPrivateAccounts = new ArrayList<>();
        ArrayList<Account> listBusinessAccounts = new ArrayList<>();
        for (Account a:chosenClient.getAccounts()
        ) { if (a.isBusinessAccount()) {listBusinessAccounts.add(a);}
        else {listPrivateAccounts.add(a);}
        }
        model.addAttribute("listPrivateAccounts", listPrivateAccounts);
        model.addAttribute("listBusinessAccounts", listBusinessAccounts);
        model.addAttribute("client", chosenClient);
        return "client_view_for_employee";
    }

}

