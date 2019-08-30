package team2.sofa.sofa.model;

public class Address {

    private int id;
    private String street;
    private int houseNumber;
    private String zipCode;
    private String city;

    public Address() {
        this(0,"", 0, "", "");
    }

    public Address(int id, String street, int houseNumber, String zipCode, String city) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.city = city;
    }

}
