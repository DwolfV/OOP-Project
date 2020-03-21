package nl.tudelft.oopp.demo.helperclasses;

import java.sql.Time;

public class Restaurant {

    private long id;
    private String name;
    private Building building;
    private Time timeClose;
    private Time timeOpen;

    public Restaurant() {

    }

    /**
     * Constructor for the Restaurant class.
     *
     * @param id       - id of the restaurant
     * @param name     - name of the restaurant
     * @param building - the building in which the restaurant is
     * @param timeClose   - the time at which the restaurant closes
     * @param timeOpen    - the time at which the restaurant opens
     */


    public Restaurant(String name, Building building, Time timeClose, Time timeOpen) {
        this.name = name;
        this.building = building;
        this.timeClose = timeClose;
        this.timeOpen = timeOpen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Time getTimeClose() {
        return timeClose;
    }

    public void setTimeClose(Time timeClose) {
        this.timeClose = timeClose;
    }

    public Time getTimeOpen() {
        return timeOpen;
    }

    public void setTimeOpen(Time timeOpen) {
        this.timeOpen = timeOpen;
    }
}
