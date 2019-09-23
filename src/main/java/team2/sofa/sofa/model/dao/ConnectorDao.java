package team2.sofa.sofa.model.dao;

import org.springframework.data.repository.CrudRepository;
import team2.sofa.sofa.model.Connector;

public interface ConnectorDao extends CrudRepository<Connector, Integer> {

    boolean existsConnectorByUsername(String username);

    Connector findConnectorByUsername(String usernam);
}
