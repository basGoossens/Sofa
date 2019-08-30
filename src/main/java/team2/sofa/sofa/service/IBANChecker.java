package team2.sofa.sofa.service;

import java.math.BigInteger;

public class IBANChecker {

    private final String BANKCODE = "SOFA";
    private final String COUNTRYCODE = "NL";
    private final int ASCIITOBANKCODE = 55;
    private String safetyNumber;
    private String bankAccountNumber;

    public IBANChecker (String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
        this.safetyNumber = SafetyNumber(this.bankAccountNumber);

    }

    private String SafetyNumber (String bankAccountNumber) {
        String bankNumber1 = Integer.toString(((int)BANKCODE.charAt(0)) - ASCIITOBANKCODE);
        String bankNumber2 = Integer.toString(((int)BANKCODE.charAt(1)) - ASCIITOBANKCODE);
        String bankNumber3 = Integer.toString(((int)BANKCODE.charAt(2)) - ASCIITOBANKCODE);
        String bankNumber4 = Integer.toString(((int)BANKCODE.charAt(3)) - ASCIITOBANKCODE);
        String countryNumber1 = Integer.toString(((int)COUNTRYCODE.charAt(0)) - ASCIITOBANKCODE);
        String countryNumber2 = Integer.toString(((int)COUNTRYCODE.charAt(1)) - ASCIITOBANKCODE);
        String longNumberString = bankNumber1+bankNumber2+bankNumber3+bankNumber4+bankAccountNumber+countryNumber1+countryNumber2;
        BigInteger longNumber = new BigInteger(longNumberString);
        BigInteger divider = new BigInteger("97");
        BigInteger value = longNumber.mod(divider);
        int valueInt = value.intValue();
        int safetyNumber = 98 - valueInt;
        String paddedSafetyNumber = String.format("%02d", safetyNumber);
        return paddedSafetyNumber;
    }


    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        bankAccountNumber = bankAccountNumber;
    }

    @Override
    public String toString() {
        return "IBANCODE = " + COUNTRYCODE + safetyNumber + BANKCODE + bankAccountNumber;
    }
}
