package team2.sofa.sofa.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class DbInitializer {

    public DbInitializer(){
        super();
    }

    private List<String> customerList(){
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
    private Set<String> ssnSet(){
        return SSNFunctionality.bsnSet(4500);
    }

}
