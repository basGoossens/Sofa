package team2.sofa.sofa.model.dao;

import org.springframework.data.repository.CrudRepository;
import team2.sofa.sofa.model.Address;

public interface AddressDao extends CrudRepository<Address, Integer> {

    Address findAddressByZipCode(String zipCode);
    Address findAddressByHouseNumber(int houseNumber);
    boolean existsAddressByZipCodeAndHouseNumber(String zipCode, int houseNumber);
    Address findAddressByZipCodeAndHouseNumber(String zipCode, int houseNumber);

}
