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
       if(clientDao.findClientByUsername(newClient.getUsername()) != null) {
           if (clientDao.findClientByUsername(newClient.getUsername()).getUsername().equals(newClient.getUsername())) {
               clientDao.save(clientDao.findClientByUsername(newClient.getUsername()));
           } else {
               clientDao.save(newClient);
           }
       }  else {
           clientDao.save(newClient);
           }
    }

    public Client SSNNameExistsChecker(Client newClient) {
        if(clientDao.findClientBySsn(newClient.getSsn()) != null){
            if (clientDao.findClientBySsn(newClient.getSsn()).getSsn().equals(newClient.getSsn())){
                return clientDao.findClientBySsn(newClient.getSsn());
            };
        } else {
            return newClient;
        }
        return newClient;
    }


    public void AddressExistsChecker(Client client) {
        String Zip = client.getAddress().getZipCode();
        int number = client.getAddress().getHouseNumber();
        boolean zipcheck = false;
        boolean numbercheck = false;
        if (addressDao.findAddressByZipCode(Zip) != null){
            zipcheck = addressDao.findAddressByZipCode(Zip).getZipCode().equals(Zip);
        }
        if (addressDao.findAddressByHouseNumber(number) != null){
            numbercheck = addressDao.findAddressByHouseNumber(number).getHouseNumber() == number;
        }
        if(zipcheck && numbercheck){
            Address newAddress;
            newAddress = AddressExists(client.getAddress());
            addressDao.save(newAddress);
        } else {
            addressDao.save(client.getAddress());
        }
    }

    public Address AddressExists(Address newAddress) {
        Address tempAddress;
        tempAddress = addressDao.findAddressByZipCodeAndHouseNumber(newAddress.getZipCode(), newAddress.getHouseNumber());
        return tempAddress;
    }



}
