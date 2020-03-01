package nl.tudelft.oopp.demo.helperclasses;

public class Building {

    private long id;
    private String name;
    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;

    public Building(String id,
                    String name,
                    String streetName,
                    String streetNumber,
                    String zipCode,
                    String city) {
        this.id = id;
        this.name = name;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
