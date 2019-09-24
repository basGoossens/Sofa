package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.Address;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.AddressDao;
import team2.sofa.sofa.model.dao.ClientDao;

import java.util.Map;

@Service
public class UpdateClient {

    @Autowired
    ClientDao clientDao;
    @Autowired
    AddressDao addressDao;
    @Autowired
    AccountDao accountDao;
    @Autowired
    NewAccountChecker checker;

    public UpdateClient() {
        super();
    }

    public Address findAddress(int id) {
        return addressDao.findById(id).get();
    }

    public Client findClient(int id) {
        return clientDao.findClientById(id);
    }

    public boolean usernameExists(String newUsername) {
        return clientDao.existsClientByUsername(newUsername);
    }

    public Client processChanges(Client client, Map<String, Object> input) {
        client.setFirstName(input.get("firstName").toString());
        client.setPrefix(input.get("prefix").toString());
        client.setLastName(input.get("lastName").toString());
        client.setEmail(input.get("email").toString());
        client.setTelephoneNr(input.get("telephoneNr").toString());
        client.setPassword(input.get("password").toString());
        clientDao.save(client);
        return client;
    }

    public Client changeAddress(Client client, Address address, Map<String, String> input) {
        int count = clientDao.countClientsByAddressId(address.getId());
        if (count > 1) {
            Address changed = new Address(0, input.get("street"), Integer.valueOf(input.get("houseNumber")), input.get("zipCode"), input.get("city"));
            if (checker.AddressExistsChecker(changed)) {
                Address exists = checker.AddressExists(changed);
                client.setAddress(exists);
                clientDao.save(client);
                return client;
            }
            addressDao.save(changed);
            client.setAddress(changed);
            clientDao.save(client);
            return client;
        }
        address.setStreet(input.get("street"));
        address.setHouseNumber(Integer.valueOf(input.get("houseNumber")));
        address.setZipCode(input.get("zipCode"));
        address.setCity(input.get("city"));
        if (checker.AddressExistsChecker(address)) {
            Address exists = checker.AddressExists(address);
            client.setAddress(exists);
            addressDao.delete(address);
            clientDao.save(client);
            return client;
        }
        addressDao.save(address);
        return client;
    }
}
