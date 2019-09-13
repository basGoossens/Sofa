package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.model.dao.ClientDao;
import team2.sofa.sofa.model.dao.EmployeeDao;

import java.util.ArrayList;
import java.util.List;


@Service
public class Login {

    @Autowired
    ClientDao clientDao;
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    TopTenHighestBalanceFinder topTenHighestBalanceFinder;
    @Autowired
    TopTenMostActiveClient topTenMostActiveClient;


    public Login() {
        super();
    }

    public String clientLogin(Client client, Model model) {
        Client loggedInClient = clientDao.findClientByUsername(client.getUsername());
        model.addAttribute("client", loggedInClient);
        Account account = new Account();
//            Accounts van klant scheiden in business en private
        splitPrivateAndBusiness(loggedInClient, model);
        model.addAttribute("account", account);
        return "client_view";
    }

    static void splitPrivateAndBusiness(Client loggedInClient, Model model) {
        ArrayList<Account> listPrivateAccounts = new ArrayList<>();
        ArrayList<Account> listBusinessAccounts = new ArrayList<>();
        for (Account a : loggedInClient.getAccounts()
        ) {
            if (a.isBusinessAccount()) {
                listBusinessAccounts.add(a);
            } else {
                listPrivateAccounts.add(a);
            }
        }
        model.addAttribute("listPrivateAccounts", listPrivateAccounts);
        model.addAttribute("listBusinessAccounts", listBusinessAccounts);
    }

    public String employeeLogin(Employee employee, Model model) {
        Employee currentEmployee = employeeDao.findEmployeeByUsername(employee.getUsername());
        model.addAttribute("employee", currentEmployee);

        if (currentEmployee.getRole().equals(EmployeeRole.HOOFD_PARTICULIEREN)) {
            List<PrivateAccount> topTenHighest;
            topTenHighest = topTenHighestBalanceFinder.getTopTenHighestBalance();
            model.addAttribute("tenHighestBalance", topTenHighest);
            return "employee_view_particulieren";

        } else {
            List<Client> topTenMostActive;
            List<BusinessAccount> topTenHighest;
            topTenHighest = topTenHighestBalanceFinder.getTopTenHighestBalanceBusiness();
            topTenMostActive = topTenMostActiveClient.getTopTenMostActiveClients();
            model.addAttribute("tenMostActive", topTenMostActive);
            model.addAttribute("tenHighestBalance", topTenHighest);
            return "employee_view_mkb";
        }
    }
}
