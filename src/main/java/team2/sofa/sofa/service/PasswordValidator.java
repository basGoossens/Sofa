package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.User;
import team2.sofa.sofa.model.dao.ClientDao;

import java.util.List;

    @Service
    public class PasswordValidator {

    @Autowired
    ClientDao clientDao;

    public PasswordValidator() {
        super();
    }

    public boolean validateClientPassword(User user) {
        boolean loginOk;
        User dbUser = clientDao.findByName(user.getUserName());
        loginOk = user.getPassword().equals(dbUser.getPassword());
        return loginOk;
    }
}
