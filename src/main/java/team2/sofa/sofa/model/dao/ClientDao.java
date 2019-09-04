package team2.sofa.sofa.model.dao;

import org.springframework.data.repository.CrudRepository;
import team2.sofa.sofa.model.Client;

public interface ClientDao extends CrudRepository<Client, Integer> {

    Client findClientByUsername(String username);
    Client findClientBySsn(String ssn);

}
