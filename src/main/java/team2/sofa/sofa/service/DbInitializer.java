package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.model.dao.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DbInitializer {
    //verschillende statics afhankelijk van hoeveelheid data die je wil inladen.
    //zie ook comment in Controller DatabaseController
    private final String BIG = "Data7000.csv";
    private final String MEDIUM = "Data5000.csv";
    private final String SMALL = "Data99.csv";
    private final int MAX_AMOUNT = 1000;
    private final int MIN_AMOUNT = 100;

    @Autowired
    ClientDao clientDao;
    @Autowired
    AddressDao addressDao;
    @Autowired
    AccountDao accountDao;
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    BusinessDao businessDao;
    @Autowired
    TransactionDao transactionDao;
    @Autowired
    FundTransfer fundTransfer;

    private List<String[]> rawData;
    private Stack<String> ssnStack;
    private Stack<String> ibanStack;
    private Stack<String> companyList;

    public DbInitializer() {
        super();
        rawData = makeDataList(SMALL);
        ssnStack = SSNFunctionality.bsnStack(rawData.size());
        this.ibanStack = new IBANGenerator().ibanStack(rawData.size()*2);
        this.companyList = makeCompanyList();
    }

    /**
     * Genereert business objects om op te slaan in de DB
     *
     * @param countb aantal te creëren businesses
     */
    public void makeBusiness(int countb, int countC) {
        for (int i = 0; i < countb; i++) {
            Random r = new Random();
            Business b = new Business();
            b.setBusinessName(companyList.pop());
            BusinessSector[] businessSectors = (BusinessSector.values());
            b.setSector(businessSectors[i%11]);
            int randomClient = r.nextInt(countC);
            if (randomClient == 0){
                randomClient = 1;
            }
            Client c = clientDao.findClientById(randomClient);
            b.setOwner(c);
            businessDao.save(b);
            makeBusinessAccount(b,c);
        }
    }

    /**
     * maakt businessaccounts aan en koppelt deze aan clients
     *
     */
    private void makeBusinessAccount(Business business, Client client) {
            BusinessAccount ba = new BusinessAccount();
            Random r = new Random();
            ba.setIban(ibanStack.pop());
            BigDecimal balance = new BigDecimal(r.nextInt(MAX_AMOUNT)+MIN_AMOUNT);
            ba.setBalance(balance);
            ba.setBusiness(business);
            connectAccount(client, ba);
    }

    /**
     * Helper methode genereert bedijfsnamen uit data.csv bestand.
     *
     * @return lijst bedrijfsnamen om in het model Business te gebruiken
     */
    private Stack<String> makeCompanyList() {
        Stack<String> comp = new Stack<>();
        for (int i = 0; i < rawData.size(); i++) {
            if (!(rawData.get(i).length == 11))
            comp.push(rawData.get(i)[11].replace("\"", ""));
        }
        return comp;
    }

    /**
     * helper methode om data in CSV bestand in te laden en in lijst te plaatsen
     *
     * @return Lijst van iedere line in CSV bestand als ruwe String inclusief comma's.
     */
    private List<String[]> makeDataList(String size) {
        Scanner fileReader;
        List<String[]> data = new ArrayList<>();
        try {
            Resource resource = new ClassPathResource(size);
            File customerList = resource.getFile();
            fileReader = new Scanner(customerList);
            fileReader.nextLine();
            while (fileReader.hasNextLine()) {
                String[] split = fileReader.nextLine().split(",");
                data.add(split);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * maakt clients en slaat deze op in DB
     *
     * @param count aantal te genereren clients
     */
    public void makeClient(int count) {
        for (int i = 0; i < count; i++) {
            Client client = new Client();
            String[] data = rawData.get(i);
            setName(client, data);
            Address address = makeAddress(data);
            addressDao.save(address);
            client.setAddress(address);
            client.setEmail(data[5]);
            client.setTelephoneNr(data[6]);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            client.setBirthday(data[7]);
            client.setGender(data[8]);
            client.setUsername(data[9]);
            client.setPassword(data[10]);
            client.setSsn(ssnStack.pop());
            clientDao.save(client);
        }
    }

    /**
     * maakt Employees en slaat deze op in DB
     *
     */
    public void makeEmployees(int count) {
        Random random = new Random();
        for (int i = count; i > 0; i--) {
            int index = random.nextInt(rawData.size());
            Employee employee = new Employee();
            String[] emp = rawData.get(index);
            setName(employee, emp);
            Address address = makeAddress(emp);
            if (addressDao.existsAddressByZipCodeAndHouseNumber(address.getZipCode(),address.getHouseNumber())){
                address = addressDao.findAddressByZipCodeAndHouseNumber(address.getZipCode(),address.getHouseNumber());
            }
            addressDao.save(address);
            employee.setAddress(address);
            employee.setRole(null);
            employee.setEmail(emp[5]);
            employee.setTelephoneNr(emp[6]);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            employee.setBirthday(emp[7]);
            employee.setGender(emp[8]);
            employee.setUsername(emp[9]);
            employee.setPassword(emp[10]);
            employee.setSsn(ssnStack.pop());
            employeeDao.save(employee);
        }
    }
    public void assignEmployeeRoles(){
        EmployeeRole[] roles = EmployeeRole.values();
        List<Employee> list = (List<Employee>) employeeDao.findAll();
        list.get(0).setRole(roles[0]);
        list.get(1).setRole(roles[1]);
        for (int i = 2; i < list.size(); i++) {
            list.get(i).setRole(roles[2]);
        }
    }

    /**
     * helper methode voor het het splitsen en setten van firstname, prefix en lastname van de User (client, danwel employee)
     *
     * @param user  Kan Client, danwel Employee zijn.
     * @param split de String Array met data uit CSV bestand.
     */
    private void setName(User user, String[] split) {
        user.setFirstName(split[0].replace("\"", ""));
        if (split[1].startsWith("\"")) {
            String opgeschoond = split[1].replace("\"", "");
            String[] splits = opgeschoond.split(" ");
            StringBuilder prefix = new StringBuilder();
            for (int i = 0; i < splits.length - 1; i++) {
                prefix.append(splits[i]);
                if (i < splits.length - 2) {
                    prefix.append(" ");
                }
            }
            user.setPrefix(prefix.toString());
            user.setLastName(splits[splits.length - 1]);
        } else {
            user.setLastName(split[1]);
        }
    }

    /**
     * helper methode om een Address te maken die gekoppeld is aan de User.
     * En deze vast weg te schrijven in DB zodat er een juiste koppeling ontstaat tussen de tabel address en user
     *
     * @param split de String Array met data uit CSV bestand.
     * @return een Address om te gebruiken in de concrete klasses van User
     */
    private Address makeAddress(String[] split) {
        Address address = new Address();
        StringBuilder street = new StringBuilder();
        String st = split[2].replace("\"", "");
        String[] splits = st.split(" ");
        for (int i = 0; i < splits.length - 1; i++) {
            street.append(splits[i]);
            if (i < splits.length - 2) {
                street.append(" ");
            }
        }
        address.setStreet(street.toString());
        address.setHouseNumber(Integer.parseInt(splits[splits.length - 1]));
        address.setZipCode(split[3].replace("\"", ""));
        address.setCity(split[4].replace("\"", ""));
        return address;
    }

    /**
     * Methode die voor Clients een variabel aantal Accounts inclusief IBAN en Saldo creëert en deze direct koppelt aan de Client.
     * Dit zodat er onderscheid is tusen Clients zonder particuliere Rekening, sommige met maar 1 en sommige met 2.
     * Tevens wordt er random een saldo gecreëerd op het gemaakte account tussen de 99.99 en 0.01
     */
    public void fillPrivateAccounts() {
        Iterable<Client> client = clientDao.findAll();
        for (Client c : client
        ) {
            Random r = new Random();
            int result = r.nextInt(3);
            for (int i = 0; i < result; i++) {
                PrivateAccount a = new PrivateAccount();
                a.setIban(ibanStack.pop());
                BigDecimal balance = new BigDecimal(r.nextInt(MAX_AMOUNT)+MIN_AMOUNT);
                a.setBalance(balance);
                connectAccount(c, a);
            }
        }
    }

    /**
     * helper methode voor fillAccounts om op juiste wijze de Accounts en Owners te koppelen.
     * en in de DB weg te schrijven.
     *
     * @param client  nieuwe klant
     * @param account nieuwe account
     */
    private void connectAccount(Client client, Account account) {
        client.addAccount(account);
        account.addClient(client);
        clientDao.save(client);
        accountDao.save(account);
    }
    public void fillTransactions(){
        long maxId = accountDao.count();
        Random random = new Random();
        for (int i = 0; i < maxId ; i++) {
            int credit = random.nextInt(Math.toIntExact(maxId))+1;
            int debit = random.nextInt((int) maxId)+1;
            while (debit == credit){
                debit = random.nextInt((int) maxId)+1;
            }
            Account cr = accountDao.findAccountById(credit);
            Account db = accountDao.findAccountById(debit);
            Transaction t = new Transaction(new BigDecimal(random.nextInt(10)+1),"IKEA", LocalDate.now(),cr,db);
            fundTransfer.storeTransaction(db,cr,t);
        }
    }
    public void makeDoubleAccounts(){
        List<Client> list = clientDao.findClientsByAccountsNull();
        List<Account> accounts = (List<Account>) accountDao.findAll();

    }
}

