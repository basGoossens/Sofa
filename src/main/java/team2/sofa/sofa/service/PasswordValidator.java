package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.Employee;
import team2.sofa.sofa.model.User;
import team2.sofa.sofa.model.dao.ClientDao;
import team2.sofa.sofa.model.dao.EmployeeDao;

import java.util.List;

@Service
public class PasswordValidator {

    @Autowired
    ClientDao clientDao;

    @Autowired
    EmployeeDao employeeDao;

    public PasswordValidator() {
        super();
    }

    public boolean validateClientPassword(Client client) {
        boolean loginOk;
        Client clients = clientDao.findByUsername(client.getUserName());
        loginOk = client.getPassword().equals(clients.getPassword());
        return loginOk;
    }

    public boolean validateEmployeePassword(Employee employee) {
        boolean loginOk;
        Employee employee1 = employeeDao.findByUsername(employee.getUserName());
        loginOk = employee.getPassword().equals(employee1.getPassword());
        return loginOk;
    }
}

