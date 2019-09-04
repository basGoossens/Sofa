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

    public boolean usernameExistsChecker(String newUsername) {
       if(clientDao.findClientByUsername(newUsername).getUsername().equals(newUsername)) {
           return false;}
       else {
           return true;
       }
    }

    public boolean SSNNameExistsChecker(String newSsn) {
        if(clientDao.findClientBySsn(newSsn).getSsn().equals(newSsn)) {
            return false;
        } else {
            return true;
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
