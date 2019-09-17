package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.dao.AccountDao;

import java.sql.ResultSet;
import java.util.List;

@Service
public class SectorAnalyzer {

    @Autowired
    AccountDao accountDao;

    public SectorAnalyzer() {
    }

    public List<String> getAverageBalancePerSector(){
        return accountDao.getBalancePerSector();
    }

/*    @Service
    public class BalancePerSector {
        private String sector;
        private double averageBalance;

        public BalancePerSector() {
        }

        public String getSector() {
            return sector;
        }

        public void setSector(String sector) {
            this.sector = sector;
        }

        public double getAverageBalance() {
            return averageBalance;
        }

        public void setAverageBalance(double averageBalance) {
            this.averageBalance = averageBalance;
        }
    }*/

}


