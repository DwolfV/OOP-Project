package nl.tudelft.oopp.demo.helperclasses;

import java.sql.Time;
import java.util.List;

public class Restaurant {

    private long id;
    private String name;
    private Building building;
    private Time tClose;
    private Time tOpen;

    public Restaurant() {

    }

    /**
     * Constructor for the Restaurant class.
     *
     * @param id - id of the restaurant
     * @param name - name of the restaurant
     * @param building - the building in which the restaurant is
     * @param tClose - the time at which the restaurant closes
     * @param tOpen - the time at which the restaurant opens
     */

    public Restaurant() {
    }


    public Restaurant(String name, Building building, Time tClose, Time tOpen) {
        this.name = name;
        this.building = building;
        this.tClose = tClose;
        this.tOpen = tOpen;
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

    public Time gettClose() {
        return tClose;
    }

    public void settClose(Time tClose) {
        this.tClose = tClose;
    }

    public Time gettOpen() {
        return tOpen;
    }

    public void settOpen(Time tOpen) {
        this.tOpen = tOpen;
    }
}
