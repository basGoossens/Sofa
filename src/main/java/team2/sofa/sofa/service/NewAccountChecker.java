package team2.sofa.sofa.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Address;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.AddressDao;
import team2.sofa.sofa.model.dao.ClientDao;

@Service
public class NewAccountChecker {

    @Autowired
    ClientDao clientDao;

    @Autowired
    AddressDao addressDao;

    @Autowired
    AccountDao accountDao;

    public NewAccountChecker() {
        super();
    }

    /**
     * verwerkt de input van het formulier en indien alles correct wordt de nieuwe klant opgeslagen in DB
     *
     * @param client op basis van ingevulde velden in formulier nieuwe klant is client object gemaakt
     * @return een String die in de NewClientController wordt gebruikt als verwijzer naar de juiste handler/vervolgscherm
     */
    public String processApplication(Client client) {
        String type = checkData(client);
        switch (type) {
            case "username":
                System.out.println("kies andere username");

                return "new_account";
            case "ssn":
                System.out.println("BSN al in gebruik, log gewoon in");
                return "login";
            case "ssnBad":
                System.out.println("ongeldig BSN");
                return "new_account";
            case "address":
                System.out.println("tweede client op zelfde adres");
                Address a = AddressExists(client.getAddress());
                client.setAddress(a);
                makeNewAccount(client);
                return "login";
            case "ok":
                System.out.println("geheel nieuwe klant");
                makeNewAccount(client);
                return "login";
        }
        return "new_account";
    }

    /**
     * checkt inhoudelijk de data die is meegegeven in formulier
     *
     * @param newclient
     * @return String die wordt gebruikt in processApplication om
     * case switch statement af te handelen en correcte vervolgstap te bepalen
     */
    private String checkData(Client newclient) {
        String ssn = newclient.getSsn();
        if (usernameExistsChecker(newclient)) {
            return "username";
        } else if (SSNNameExistsChecker(newclient)) {
            return "ssn";
        } else if (!SSNFunctionality.ssnCheck(ssn)) {
            return "ssnBad";
        } else if (AddressExistsChecker(newclient)) {
            return "address";
        }
        return "ok";
    }

    /**
     * Slaat nieuwe klant daadwerkelijk op in DB
     *
     * @param client
     */
    private void makeNewAccount(Client client) {
        addressDao.save(client.getAddress());
        clientDao.save(client);
        Client savedClient = clientDao.findClientByUsername(client.getUsername());
        IBANGenerator newIBAN = new IBANGenerator();
        Account newAccount = new Account();
        newAccount.setIban(newIBAN.getIBAN());
        newAccount.addClient(savedClient);
        savedClient.addAccount(newAccount);
        clientDao.save(savedClient);
        accountDao.save(newAccount);
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
    private boolean AddressExistsChecker(Client newClient) {
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

    /**
     * helper methode voor in geval van bestaand adress het juiste adressobject
     * op te halen inclusief het bestaande ID om toe te voegen aan de nieuwe klant
     *
     * @param newAddress adresgegevens zoals ingevuld op site zonder id.
     * @return Adress object inclusief id zoals reeds bekend in DB
     */
    private Address AddressExists(Address newAddress) {
        Address tempAddress;
        tempAddress = addressDao.findAddressByZipCodeAndHouseNumber(newAddress.getZipCode(), newAddress.getHouseNumber());
        return tempAddress;
    }
}
