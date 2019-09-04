package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.*;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.AddressDao;
import team2.sofa.sofa.model.dao.ClientDao;
import team2.sofa.sofa.model.dao.EmployeeDao;

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

    private List<String[]> rawData;
    private Stack<String> ssnStack;
    private Stack<String> ibanStack;
    private Stack<String> companyList;
    private int[] numberAccounts;

    public DbInitializer() {
        super();
        rawData = makeDataList();
        ssnStack = SSNFunctionality.bsnStack(rawData.size());
        this.ibanStack = new IBANGenerator().ibanStack(rawData.size());
        numberAccounts = new int[]{0, 1, 2, 3};
    }

    /**
     * Helper methode genereert bedijfsnamen uit data.csv bestand.
     * @return
     * lijst bedrijfsnamen om in het model Business te gebruiken
     */
    private List<String> makeCompanyList() {
        Stack<String> comp = new Stack<>();
        for (int i = 0; i < rawData.size(); i++) {
            comp.push(rawData.get(i)[11]);
        }
        return comp;
    }

    /**
     * helper methode om data in CSV bestand in te laden en in lijst te plaatsen
     * @return
     * Lijst van iedere line in CSV bestand als ruwe String inclusief comma's.
     */
    private List<String[]> makeDataList() {
        Scanner fileReader;
        List<String[]> u = new ArrayList<>();
        try {
            Resource resource = new ClassPathResource("DataKlein.csv");
            File customerList = resource.getFile();
            fileReader = new Scanner(customerList);
            fileReader.nextLine();
            while (fileReader.hasNextLine()) {
                String[] split = fileReader.nextLine().split(",");
                u.add(split);
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
            setName(client, rawData.get(i));
            client.setAddress(makeAddress(rawData.get(i)));
            client.setEmail(rawData.get(i)[5]);
            client.setTelephoneNr(rawData.get(i)[6]);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            client.setBirthday(rawData.get(i)[7]);
            client.setGender(rawData.get(i)[8]);
            client.setUsername(rawData.get(i)[9]);
            client.setPassword(rawData.get(i)[10]);
            client.setSsn(ssnStack.pop());
            clientDao.save(client);
        }
    }

    /**
     * maakt Employees en slaat deze op in DB
     * @param index welk index nummer te gebruiken in rawData List
     * @param role de rol van de te maken Employee zoals vermeld in Enum EmployeeRole
     */
    public void makeEmployee(int index, EmployeeRole role){
        Employee employee = new Employee();
        setName(employee, rawData.get(index));
        employee.setAddress(makeAddress(rawData.get(index)));
        employee.setRole(role);
        employee.setEmail(rawData.get(index)[5]);
        employee.setTelephoneNr(rawData.get(index)[6]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        employee.setBirthday(rawData.get(index)[7]);
        employee.setGender(rawData.get(index)[8]);
        employee.setUsername(rawData.get(index)[9]);
        employee.setPassword(rawData.get(index)[10]);
        employee.setSsn(ssnStack.pop());
        employeeDao.save(employee);
    }

    /**
     * helper methode voor het het splitsen en setten van firstname, prefix en lastname van de User (client, danwel employee)
     * @param user Kan Client, danwel Employee zijn.
     * @param split de String Array met data uit CSV bestand.
     */
    private void setName(User user, String[] split) {
        user.setFirstName(split[0]);
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

