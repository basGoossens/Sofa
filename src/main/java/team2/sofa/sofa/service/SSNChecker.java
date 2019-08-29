package team2.sofa.sofa.service;

import java.util.Random;

public class SSNChecker {
    private static final int LOW = 10000000;
    private static final int HIGH = 100000000;
    private String bsn;

    public SSNChecker(){
        do {bsn = bsnGenerator();
        } while (!BSNcheck(bsn));
    }

    public static boolean BSNcheck(String ssn){
        int sum = 0;
        int multiply = 9;
        for (int i = 0; i < ssn.length()-1; i++) {
            int number = Character.getNumericValue(ssn.charAt(i));
            sum += number * multiply;
            multiply--;
        }
        sum += Character.getNumericValue(ssn.charAt(8)) * -1;
        return sum % 11 == 0;
    }

    public static String bsnGenerator() {
        Random r = new Random();
        int result = r.nextInt(HIGH-LOW) + LOW;
        return String.format("%09d", result);
    }

    public String getBsn(){
        return bsn;
    }
}
