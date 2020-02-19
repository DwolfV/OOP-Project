package nl.tudelft.oopp.demo.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "building")
public class Building {

    @Id
    @Column(name = "buildingId")
    private String buildingId;

    @Column(name = "buildingName")
    private String buildingName;

    @Column(name = "street")
    private String street;

    @Column(name = "streetNumber")
    private String streetNumber;

    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "city")
    private String city;

    public Building() {

    }

    public Building(String buildingId, String buildingName, String street, String streetNumber, String zipCode, String city) {
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.street = street;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getName() {
        return buildingName;
    }

    public void setName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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
        return "Building{" +
                "buildingId='" + buildingId + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return Objects.equals(getBuildingId(), building.getBuildingId()) &&
                Objects.equals(getName(), building.getName()) &&
                Objects.equals(getStreet(), building.getStreet()) &&
                Objects.equals(getStreetNumber(), building.getStreetNumber()) &&
                Objects.equals(getZipCode(), building.getZipCode()) &&
                Objects.equals(getCity(), building.getCity());
    }
}
