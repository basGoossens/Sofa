package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import team2.sofa.sofa.model.Address;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.LoginForm;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.AddressDao;
import team2.sofa.sofa.model.dao.ClientDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UpdateClient {

    @Autowired ClientDao clientDao;
    @Autowired AddressDao addressDao;
    @Autowired AccountDao accountDao;
    List<String> errorList;

    public UpdateClient() {
        super();
    }

    public List<String> processUpdate(Client client, Map<String, Object> body){
        if (body.get("firstName").toString() != "" && !body.get("firstName").equals(client.getFirstName()))
            client.setFirstName(body.get("firstName").toString());
        if (body.get("prefix").toString() != "" && !body.get("prefix").equals(client.getPrefix()))
            client.setPrefix(body.get("prefix").toString());
        if (body.get("lastName").toString() != "" && !body.get("lastName").equals(client.getLastName()))
            client.setLastName(body.get("lastName").toString());
        if (body.get("zipCode").toString() != "" && body.get("houseNumber").toString() != "")
            if (!body.get("zipCode").equals(client.getAddress().getZipCode()) || !body.get("houseNumber").equals(client.getAddress().getHouseNumber())){
                checkDeleteAddress(client.getAddress()); //method checks and deletes if no other occupants on address
                int addNr = Integer.parseInt(body.get("zipCode").toString());
                String addStreet = body.get("street").toString();
                String addZip = body.get("zipCode").toString();
                String addCity = body.get("city").toString();
                if (addressDao.findAddressByZipCodeAndHouseNumber(addZip, addNr) != null) {
                    Address newAdd = new Address(0, addStreet, addNr, addZip, addCity);
                    addressDao.save(newAdd);
                    client.setAddress(newAdd);
                } else client.setAddress(addressDao.findAddressByZipCodeAndHouseNumber(addZip, addNr));
            }
        if (body.get("ssn").toString() != "" && !body.get("ssn").equals(client.getSsn()))
            if (checkSSN(body.get("ssn").toString())) client.setSsn(body.get("ssn").toString());
        if (body.get("email").toString() != "" && !body.get("email").equals(client.getEmail()))
            client.setEmail(body.get("email").toString());
        if (body.get("telephoneNr").toString() != "" && !body.get("telephoneNr").equals(client.getTelephoneNr()))
            client.setTelephoneNr(body.get("telephoneNr").toString());
        if (body.get("birthday").toString() != "" && !body.get("birthday").equals(client.getBirthday()))
            client.setBirthday(body.get("birthday").toString());
        if (body.get("gender").toString() != "" && !body.get("gender").equals(client.getBirthday()))
            client.setGender(body.get("gender").toString());
        if (body.get("username").toString() != "" && !body.get("username").equals(client.getUsername()))
            client.setUsername(body.get("username").toString());
        if (body.get("password").toString() != "" && !body.get("password").equals(client.getPassword()))
            client.setPassword(body.get("password").toString());
        return errorList;
    }

    public boolean checkSSN(String ssn) {
        if (clientDao.findClientBySsn(ssn) != null) {
            errorList.add("Gebruiker met dit BSN bestaat al.");
            return false;
        }
        if (ssn.length() != 9) {
            errorList.add("BSN heeft geen 9 cijfers.");
            return false;
        }
        if (!SSNFunctionality.ssnCheck(ssn)) {
            errorList.add("Geen geldig BSN ingevoerd.");
            return false;
        }
        return true;
    }

    public void checkDeleteAddress (Address adres){
        Address currentAddress = addressDao.findAddressByZipCodeAndHouseNumber(adres.getZipCode(), adres.getHouseNumber());
        List<Client> allOccupants = clientDao.findAllByAddress_Id(currentAddress.getId());
        if (allOccupants.size() < 2) addressDao.delete(adres);
    }

    public boolean checkPassword(String password){
        if (password.length() < 8) {
            errorList.add("Geen geldig wachtwoord. Onvoldoende lengte.");
            return false;
        }
        int countSymbols = 0;
        int countInts = 0;
        int countLetters = 0;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (c >= '0' && c <= '9') countInts ++;
            if (c >= 'A' && c <= 'Z') countLetters ++;
            if (c >= 'a' && c <= 'z') countLetters ++;
            else countSymbols ++;
        }
        if (countSymbols > 0 && countInts > 0 && countLetters > 0) return true;
        else {
            errorList.add("Geen geldig wachtwoord. Een geldig wachtwoord bestaat uit cijfers, letters en symbolen.");
            return false;
        }
    }

}
