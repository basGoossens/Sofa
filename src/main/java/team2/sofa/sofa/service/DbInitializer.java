package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.model.dao.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DbInitializer {
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

    private Stack<String[]> rawData;
    private Stack<String> ssnStack;
    private Stack<String> ibanStack;
    private Stack<String> companyList;
    private int[] numberAccounts;

    public DbInitializer() {
        super();
        rawData = makeDataList();
        ssnStack = SSNFunctionality.bsnStack(rawData.size());
        this.ibanStack = new IBANGenerator().ibanStack(rawData.size());
        this.companyList = makeCompanyList();
        numberAccounts = new int[]{0, 1, 2, 3};
    }
    public void makeBusiness(int count){
        for (int i = 0; i < count ; i++) {
            Business b = new Business();
            b.setBusinessName(companyList.pop());
            b.setSector(BusinessSector.AGRARISCH);
            Client c = clientDao.findClientById(5);
            b.setOwner(c);
            businessDao.save(b);
        }

    }
    public void makeBusinessAccount(int count){
        for (int i = 0; i < count ; i++) {
            BusinessAccount ba = new BusinessAccount();
            Random r = new Random();
            ba.setIBAN(ibanStack.pop());
            ba.setBalance((int) (r.nextDouble() * 10000) / 100.0);
            Business b = businessDao.findById(i+1).get();
            ba.setBusiness(b);
            Client c = clientDao.findClientById(i+4);
            connectAccount(c, ba);
        }
    }

    /**
     * Helper methode genereert bedijfsnamen uit data.csv bestand.
     * @return
     * lijst bedrijfsnamen om in het model Business te gebruiken
     */
    private Stack<String> makeCompanyList() {
        Stack<String> comp = new Stack<>();
        for (int i = 0; i < rawData.size(); i++) {
            comp.push(rawData.pop()[11]);
        }
        return comp;
    }

    /**
     * helper methode om data in CSV bestand in te laden en in lijst te plaatsen
     * @return
     * Lijst van iedere line in CSV bestand als ruwe String inclusief comma's.
     */
    private Stack<String[]> makeDataList() {
        Scanner fileReader;
        Stack<String[]> u = new Stack<>();
        try {
            Resource resource = new ClassPathResource("DataKlein.csv");
            File customerList = resource.getFile();
            fileReader = new Scanner(customerList);
            fileReader.nextLine();
            while (fileReader.hasNextLine()) {
                String[] split = fileReader.nextLine().split(",");
                u.push(split);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return u;
    }

    /**
     * maakt clients en slaat deze op in DB
     * @param count aantal te genereren clients
     */
    public void makeClient(int count) {
        for (int i = 0; i < count; i++) {
            Client client = new Client();
            String[] x = rawData.pop();
            setName(client, x);
            client.setAddress(makeAddress(x));
            client.setEmail(x[5]);
            client.setTelephoneNr(x[6]);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            client.setBirthday(x[7]);
            client.setGender(x[8]);
            client.setUsername(x[9]);
            client.setPassword(x[10]);
            client.setSSN(ssnStack.pop());
            clientDao.save(client);
        }
    }

    /**
     * maakt Employees en slaat deze op in DB
     * @param role de rol van de te maken Employee zoals vermeld in Enum EmployeeRole
     */
    public void makeEmployee( EmployeeRole role){
        Employee employee = new Employee();
        String[] emp = rawData.pop();
        setName(employee, emp);
        employee.setAddress(makeAddress(emp));
        employee.setRole(role);
        employee.setEmail(emp[5]);
        employee.setTelephoneNr(emp[6]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        employee.setBirthday(emp[7]);
        employee.setGender(emp[8]);
        employee.setUsername(emp[9]);
        employee.setPassword(emp[10]);
        employee.setSSN(ssnStack.pop());
        employeeDao.save(employee);
    }

    /**
     * helper methode voor het het splitsen en setten van firstname, prefix en lastname van de User (client, danwel employee)
     * @param user Kan Client, danwel Employee zijn.
     * @param split de String Array met data uit CSV bestand.
     */
    private void setName(User user, String[] split) {
        user.setFirstName(split[0].replace("\"", ""));
        if (split[1].startsWith("\"")) {
            String opgeschoond = split[1].replace("\"", "");
            String[] splits = opgeschoond.split(" ");
            String prefix = "";
            for (int i = 0; i < splits.length - 1; i++) {
                prefix += splits[i];
                if (i < splits.length - 2) {
                    prefix += " ";
                }
            }
            user.setPrefix(prefix);
            user.setLastName(splits[splits.length - 1]);
        } else {
            user.setLastName(split[1]);
        }
    }

    /**
     * helper methode om een Address te maken die gekoppeld is aan de User.
     * En deze vast weg te schrijven in DB zodat er een juiste koppeling ontstaat tussen de tabel address en user
     * @param split de String Array met data uit CSV bestand.
     * @return een Address om te gebruiken in de concrete klasses van User
     */
    private Address makeAddress(String[] split) {
        Address a = new Address();
        String street = "";
        String st = split[2].replace("\"", "");
        String splits[] = st.split(" ");
        for (int i = 0; i < splits.length - 1; i++) {
            street += splits[i];
            if (i < splits.length - 2) {
                street += " ";
            }
        }
        a.setStreet(street);
        a.setHouseNumber(Integer.parseInt(splits[splits.length - 1]));
        a.setZipCode(split[3]);
        a.setCity(split[4].replace("\"", ""));
        addressDao.save(a);
        return a;
    }

    /**
     * Methode die voor Clients een variabel aantal Accounts inclusief IBAN en Saldo creëert en deze direct koppelt aan de Client.
     * Dit zodat er onderscheid is tusen Clients zonder particuliere Rekening, sommige met maar 1 en sommige met 2.
     * Tevens wordt er random een saldo gecreëerd op het gemaakte account tussen de 99.99 en 0.01
     */
    public void fillAccounts() {
        Iterable<Client> client = clientDao.findAll();
        for (Client c : client
        ) {
            Random r = new Random();
            int result = r.nextInt(3);
            for (int i = 0; i < result; i++) {
                Account a = new Account();
                a.setIBAN(ibanStack.pop());
                a.setBalance((int) (r.nextDouble() * 10000) / 100.0);
                connectAccount(c, a);
            }
        }
    }

    /**
     * helper methode voor fillAccounts om op juiste wijze de Accounts en Owners te koppelen.
     * en in de DB weg te schrijven.
     * @param client
     * @param account
     */
    private void connectAccount(Client client, Account account) {
        client.addAccount(account);
        account.addClient(client);
        clientDao.save(client);
        accountDao.save(account);
    }
}

