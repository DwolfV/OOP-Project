package nl.tudelft.oopp.demo.helperclasses;

import java.time.LocalTime;

public class Restaurant {

    private long id;
    private String name;
    private Building building;
    private LocalTime timeClose;
    private LocalTime timeOpen;

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


    public Restaurant(String name, Building building, LocalTime timeClose, LocalTime timeOpen) {
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

    public LocalTime getTimeClose() {
        return timeClose;
    }

    public void setTimeClose(LocalTime timeClose) {
        this.timeClose = timeClose;
    }

    public LocalTime getTimeOpen() {
        return timeOpen;
    }

    public void setTimeOpen(LocalTime timeOpen) {
        this.timeOpen = timeOpen;
    }
}
