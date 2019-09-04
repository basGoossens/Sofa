package team2.sofa.sofa.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.Address;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.dao.AddressDao;
import team2.sofa.sofa.model.dao.ClientDao;

@Service
public class NewAccountChecker {

    @Autowired
    ClientDao clientDao;

    @Autowired
    AddressDao addressDao;

    public NewAccountChecker() {super();}

    public void usernameExistsChecker(Client newClient) {
       if(clientDao.findClientByUsername(newClient.getUsername()).getUsername().equals(newClient.getUsername())) {
           clientDao.save(clientDao.findClientByUsername(newClient.getUsername()));}
       else {
           clientDao.save(newClient);
       }
    }

    public Client SSNNameExistsChecker(Client newClient) {
        if(clientDao.findClientBySsn(newClient.getSsn()).getSsn().equals(newClient.getSsn())) {
            return clientDao.findClientBySsn(newClient.getSsn());
        } else {
            return newClient;
        }
    }


    public void AddressExistsChecker(Address checkAddress) {

        if(addressDao.findAddressByZipCode(checkAddress.getZipCode()).getZipCode().equals(checkAddress.getZipCode()) &&
                addressDao.findAddressByHouseNumber(checkAddress.getHouseNumber()).getHouseNumber() == (checkAddress.getHouseNumber())) {
            Address newAddress;
            newAddress = AddressExists(checkAddress);
            addressDao.save(newAddress);
        } else {
            addressDao.save(checkAddress);
        }
    }

    public Address AddressExists(Address newAddress) {
        Address tempAddress;
        tempAddress = addressDao.findAddressByZipCodeAndHouseNumber(newAddress.getZipCode(), newAddress.getHouseNumber());
        return tempAddress;
    }



}
