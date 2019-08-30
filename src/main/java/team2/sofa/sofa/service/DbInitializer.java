package team2.sofa.sofa.service;

import org.springframework.stereotype.Service;
import team2.sofa.sofa.model.Address;
import team2.sofa.sofa.model.Client;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DbInitializer {
    private List<String> rawCustomerList;
    private Stack<String> ssnStack;

    public DbInitializer(){
        super();
        rawCustomerList = makeList();
        ssnStack = SSNFunctionality.bsnSet(rawCustomerList.size());
    }

    private List<String> makeList(){
        Scanner fileReader;
        List<String> u = new ArrayList<>();
        try {
            File customerList = new File("Resources/Particulier.csv");
            fileReader = new Scanner(customerList);
            fileReader.nextLine();
            while (fileReader.hasNextLine()){
                u.add(fileReader.nextLine());
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return u;
    }

    private void makeClient(){
        Client client = new Client();
        for (int i = 0; i < rawCustomerList.size(); i++) {
            String[] raw = rawCustomerList.get(i).split(",");
            setName(client, raw);
            client.setAddress(makeAddress(raw));
            client.setEmail(raw[5]);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            client.setBirthday(LocalDate.parse(raw[7], formatter));
            client.setGender(raw[8]);
            client.setUsername(raw[9]);
            client.setPassword(raw[10]);
            client.setSSN(ssnStack.pop());
        }

    }

    private void setName(Client user, String[] split){
        user.setFirstName(split[0]);
        if (split[1].startsWith("\"")){
            String opgeschoond = split[1].replace("\"", "");
            String[] splits = opgeschoond.split(" ");
            String prefix = "";
            for (int i = 0; i < splits.length-1; i++) {
                prefix += splits[i];
                if (i < splits.length-2){
                    prefix += " ";
                }
            }
            user.setPrefix(prefix);
            user.setLastName(splits[splits.length-1]);
        } else {
            user.setLastName(split[1]);
        }
    }
    private Address makeAddress(String[] split){
        Address a = new Address();
        String street = "";
        String st = split[2].replace("\"", "");
        String splits[] = st.split(" ");
        for (int i = 0; i < splits.length-1; i++) {
            street += splits[i];
            if (i < splits.length-2) {
                street += " ";
            }
        }
        a.setStreet(street);
        a.setHouseNumber(Integer.parseInt(splits[splits.length-1]));
        a.setZipCode(split[3]);
        a.setCity(split[4].replace("\"", ""));
        return a;
    }

}
