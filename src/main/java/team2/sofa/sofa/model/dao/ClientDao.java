package team2.sofa.sofa.model.dao;

import org.springframework.data.repository.CrudRepository;
import team2.sofa.sofa.model.Client;

import java.util.List;

public interface ClientDao extends CrudRepository <Client, Integer> {

    public Client findByUsername(String username);
}
