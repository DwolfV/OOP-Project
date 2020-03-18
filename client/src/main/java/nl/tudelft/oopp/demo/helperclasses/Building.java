package nl.tudelft.oopp.demo.helperclasses;

import java.util.HashSet;
import java.util.Set;

public class Building {

    private Long id;
    private String name;
    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;

    private Set<Room> rooms = new HashSet<>();

    public Building() {

    }

    /**
     * Create a new Building instance.
     *
     * @param name         The name of the Building.
     * @param streetName   The name of the street for the Building
     * @param streetNumber The number of the street for the Building
     * @param zipCode      The zip code for the Building
     * @param city         The city of the Building
     */

    public Building(String name,
                    String streetName,
                    String streetNumber,
                    String zipCode,
                    String city) {
        this.name = name;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "Building{"
                + "buildingId='" + id + '\''
                + ", buildingName='" + name + '\''
                + ", street='" + streetName + '\''
                + ", streetNumber='" + streetNumber + '\''
                + ", zipCode='" + zipCode + '\''
                + ", city='" + city + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Building building = (Building) o;
        return getName().equals(building.getName())
                && getStreetName().equals(building.getStreetName())
                && getStreetNumber().equals(building.getStreetNumber())
                && getZipCode().equals(building.getZipCode())
                && getCity().equals(building.getCity());
    }
}
