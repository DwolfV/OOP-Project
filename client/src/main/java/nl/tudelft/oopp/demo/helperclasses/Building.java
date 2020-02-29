package nl.tudelft.oopp.demo.helperclasses;

import java.sql.Time;
import java.util.List;

public class Building {

    String BuildingID;
    String BuildingName;
    String StreetName;
    String StreetNumber;
    String ZipCode;
    String City;

    /**
     * Create a new Building instance.
     *
     * @param BuildingID ID of the Building.
     * @param BuildingName Name of the Building.
     * @param StreetName Street Name of the Building.
     * @param StreetNumber Street Number of the Building.
     * @param ZipCode Zip Code of the building.
     * @param City City of the building.
     */

    public Building(String BuildingID, String BuildingName, String StreetName, String StreetNumber, String ZipCode, String City) {
        this.BuildingID = BuildingID;
        this.BuildingName = BuildingName;
        this.StreetName = StreetName;
        this.StreetNumber = StreetNumber;
        this.ZipCode = ZipCode;
        this.City = City;
    }

    public String getBuildingID() {
        return BuildingID;
    }

    public void setBuildingID(String buildingID) {
        this.BuildingID = BuildingID;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        this.BuildingName = BuildingName;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        this.StreetName = StreetName;
    }

    public String getStreetNumber() {
        return StreetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.StreetNumber = StreetNumber;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        this.ZipCode = ZipCode;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        this.City = City;
    }

}
