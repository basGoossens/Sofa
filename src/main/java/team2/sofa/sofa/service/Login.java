package team2.sofa.sofa.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.ClientDao;
import team2.sofa.sofa.model.dao.ConnectorDao;
import team2.sofa.sofa.model.dao.EmployeeDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
public class Login {

    @Autowired
    AccountDao accountDao;
    @Autowired
    ClientDao clientDao;
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    TopTenHighestBalanceFinder topTenHighestBalanceFinder;
    @Autowired
    TopTenMostActiveClient topTenMostActiveClient;
    @Autowired
    SectorAnalyzer sectorAnalyzer;
    @Autowired
    ConnectorDao connectorDao;



    public Login() {
        super();
    }

    public String backFromDashboard(Account account, Model model){
        Account a = accountDao.findAccountById(account.getId());
        Client c = a.getOwners().get(0);
        model.addAttribute("client", c);
        model.addAttribute("account", a);
        return "client_view";
    }

    //model.addAttribute uitgecomment
    public String clientLogin(Client client, Model model) {
        Client loggedInClient = clientDao.findClientByUsername(client.getUsername());
        //als er een connector is aangemaakt in de database die overeenkomt met de gebruikersnaam van de ingelogde klant
        if (connectorDao.existsConnectorByUsername(loggedInClient.getUsername())){
            model.addAttribute("connect", connectorDao.findConnectorByUsername(loggedInClient.getUsername()));
        }
//        model.addAttribute("client", loggedInClient);
        model.addAttribute("sessionclient", loggedInClient);
        Hibernate.initialize(loggedInClient.getAccounts());
        return "redirect:/clientLoginSuccess";
    

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
            List<BalancePerSectorData> balancePerSector;
            topTenHighest = topTenHighestBalanceFinder.getTopTenHighestBalanceBusiness();
            topTenMostActive = topTenMostActiveClient.getTopTenMostActiveClients();
            balancePerSector = sectorAnalyzer.getAverageBalancePerSector();
            model.addAttribute("tenMostActive", topTenMostActive);
            model.addAttribute("tenHighestBalance", topTenHighest);
            model.addAttribute("balancePerSector", balancePerSector);
            return "employee_view_mkb";
        }
    }
}
