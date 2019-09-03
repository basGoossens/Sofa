package team2.sofa.sofa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Address;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.AddressDao;
import team2.sofa.sofa.model.dao.ClientDao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
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
    private List<String> rawDataList;
    private Stack<String> ssnStack;
    private Stack<String> ibanStack;
    private Stack<String> companyList;
    private int[] numberAccounts;

    public DbInitializer() {
        super();
        rawDataList = makeDataList();
        ssnStack = SSNFunctionality.bsnStack(rawDataList.size());
        this.ibanStack = new IBANGenerator().ibanStack(rawDataList.size());
        numberAccounts = new int[]{0, 1, 2, 3};
    }

    private List<String> makeCompanyList() {
        Stack<String> comp = new Stack<>();
        for (int i = 0; i < rawDataList.size(); i++) {
            String[] raw = rawDataList.get(i).split(",");
            comp.push(raw[11]);
        }
        return comp;
    }

    private List<String> makeDataList() {
        Scanner fileReader;
        List<String> u = new ArrayList<>();
        try {
            Resource resource = new ClassPathResource("Data.csv");
            File customerList = resource.getFile();
            fileReader = new Scanner(customerList);
            fileReader.nextLine();
            while (fileReader.hasNextLine()) {
                u.add(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return u;
    }

    public void makeClient() {
        for (int i = 0; i < 50; i++) {
            Client client = new Client();
            String[] raw = rawDataList.get(i).split(",");
            setName(client, raw);
            client.setAddress(makeAddress(raw));
            client.setEmail(raw[5]);
            client.setTelephoneNr(raw[6]);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            client.setBirthday(LocalDate.parse(raw[7], formatter));
            client.setGender(raw[8]);
            client.setUsername(raw[9]);
            client.setPassword(raw[10]);
            client.setSSN(ssnStack.pop());
            clientDao.save(client);
        }
    }


    private void setName(Client client, String[] split) {
        client.setFirstName(split[0]);
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
            client.setPrefix(prefix);
            client.setLastName(splits[splits.length - 1]);
        } else {
            client.setLastName(split[1]);
        }
    }

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

    private void connectAccount(Client client, Account account) {
        client.addAccount(account);
        account.addClient(client);
        clientDao.save(client);
        accountDao.save(account);
    }
}

