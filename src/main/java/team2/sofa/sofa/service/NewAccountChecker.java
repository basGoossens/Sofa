package team2.sofa.sofa.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.model.dao.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NewAccountChecker {

    @Autowired
    ClientDao clientDao;

    @Autowired
    AddressDao addressDao;

    @Autowired
    AccountDao accountDao;

    @Autowired
    BusinessDao businessDao;

    @Autowired
    BusinessAccountDao businessAccountDao;

    public NewAccountChecker() {
        super();
    }

    /**
     * verwerkt de input van het formulier en indien alles correct wordt de nieuwe klant opgeslagen in DB
     *
     * @param client op basis van ingevulde velden in formulier nieuwe klant is client object gemaakt
     * @return een String die in de NewClientController wordt gebruikt als verwijzer naar de juiste handler/vervolgscherm
     */
    public String processApplication( Model model, Client client, Map map) {
        List <String> errorList = checkData(client);
        if (AddressExistsChecker(client)) {
            Address a = AddressExists(client.getAddress());
            client.setAddress(a); }
        if (errorList.isEmpty()) {
            if (accountTypeChecker(map)== false){
            makeNewPrivateAccount(makeNewAccount(client));
            model.addAttribute("client", client);
            return "login";}
            else {
                makeNewAccount(client);
                Business business = new Business();
                business.setOwner(client);
                model.addAttribute("business", business);
                return "new_business";
            }
        } else {
            model.addAttribute("errorList", errorList);
            for (int i = 0; i < errorList.size() ; i++) {
                System.out.println(errorList.get(i));
            }
            return "new_account";
        }
    }


    /**
     * checkt inhoudelijk de data die is meegegeven in formulier
     *
     * @param newClient
     * @return String die wordt gebruikt in processApplication om
     * case switch statement af te handelen en correcte vervolgstap te bepalen
     */
    private List <String> checkData(Client newClient) {
        String ssn = newClient.getSsn();
        List <String> errorList = new ArrayList<>();

        if (usernameExistsChecker(newClient)) {
            errorList.add("Gebruikersnaam bestaat al.");
        } if (SSNNameExistsChecker(newClient)) {
            errorList.add("Gebruiker met dit BSN bestaat al.");
        } if (ssn.length() != 9) { errorList.add("BSN heeft geen 9 cijfers."); }
           else if (!SSNFunctionality.ssnCheck(ssn)) {
                errorList.add("Geen geldig BSN ingevoerd.");
        } if (AddressExistsChecker(newClient)) {
            Address a = AddressExists(newClient.getAddress());
            newClient.setAddress(a);
        }
        return errorList;
    }

    /**
     * Slaat nieuwe klant daadwerkelijk op in DB
     *
     * @param client
     */
    private Client makeNewAccount(Client client) {
        addressDao.save(client.getAddress());
        clientDao.save(client);
        Client savedClient = clientDao.findClientByUsername(client.getUsername());
        return savedClient;
    }

    private void makeNewPrivateAccount(Client client) {
        IBANGenerator newIBAN = new IBANGenerator();
        Account newAccount = new PrivateAccount(newIBAN.getIBAN(), new BigDecimal(0));
        newAccount.addClient(client);
        client.addAccount(newAccount);
        clientDao.save(client);
        accountDao.save(newAccount);
    }

    public String makeNewBusinessAccount(Business business, Client client) {
        businessDao.save(business);
        IBANGenerator newIBAN = new IBANGenerator();
        String tempIBAN= newIBAN.ibanGenerator();
        BusinessAccount newBusinessAccount = new BusinessAccount(tempIBAN, new BigDecimal(0));
        newBusinessAccount.setBusiness(business);
        newBusinessAccount.addClient(business.getOwner());
        Client tempClient = clientDao.findClientById(business.getOwner().getId());
        client.addAccount(newBusinessAccount);
        clientDao.save(tempClient);
        businessAccountDao.save(newBusinessAccount);
        return "login";
    }

    /**
     * helper methode om na te gaan of gekozen username niet al in gebruik is.
     *
     * @param newClient
     * @return
     */
    private boolean usernameExistsChecker(Client newClient) {
        String username = newClient.getUsername();
        return clientDao.findClientByUsername(username) != null;
    }

    /**
     * helper methode om na te gaan of ingevoerde BSN niet al in gebruik is.
     *
     * @param newClient
     * @return
     */
    private boolean SSNNameExistsChecker(Client newClient) {
        String ssn = newClient.getSsn();
        return clientDao.findClientBySsn(ssn) != null;
    }

    /**
     * helper methode om na te gaan of gekozen adres niet al bekend is.
     *
     * @param newClient
     * @return
     */
    protected boolean AddressExistsChecker(Client newClient) {
        String Zip = newClient.getAddress().getZipCode();
        int number = newClient.getAddress().getHouseNumber();
        boolean zipcheck = false;
        boolean numbercheck = false;
        if (addressDao.findAddressByZipCode(Zip) != null) {
            zipcheck = addressDao.findAddressByZipCode(Zip).getZipCode().equals(Zip);
        }
        if (addressDao.findAddressByHouseNumber(number) != null) {
            numbercheck = addressDao.findAddressByHouseNumber(number).getHouseNumber() == number;
        }
        return zipcheck && numbercheck;
    }
    protected boolean AddressExistsChecker(Address address) {
        String zipCode = address.getZipCode();
        int number = address.getHouseNumber();
        return addressDao.existsAddressByZipCodeAndHouseNumber(zipCode,number);
    }

    /**
     * helper methode voor in geval van bestaand adress het juiste adressobject
     * op te halen inclusief het bestaande ID om toe te voegen aan de nieuwe klant
     *
     * @param newAddress adresgegevens zoals ingevuld op site zonder id.
     * @return Adress object inclusief id zoals reeds bekend in DB
     */
    protected Address AddressExists(Address newAddress) {
        Address tempAddress;
        tempAddress = addressDao.findAddressByZipCodeAndHouseNumber(newAddress.getZipCode(), newAddress.getHouseNumber());
        return tempAddress;
    }

    public boolean accountTypeChecker(Map <String,String> map) {
        if (map.get("accounttype").toString().equals("business")){
        return true;}
        else {return false;}
    }
}
