package nl.tudelft.oopp.demo.helperclasses;

import java.sql.Time;

public class Restaurant {

    private long id;
    private String name;
    private Building building;
    private Time tclose;
    private Time topen;

    public Restaurant() {

    }

    /**
     * Constructor for the Restaurant class.
     *
     * @param name     - name of the restaurant
     * @param building - the building in which the restaurant is
     * @param tclose   - the time at which the restaurant closes
     * @param topen    - the time at which the restaurant opens
     */


    public Restaurant(String name, Building building, Time tclose, Time topen) {
        this.name = name;
        this.building = building;
        this.tclose = tclose;
        this.topen = topen;
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
        return tclose;
    }

    public void settClose(Time tclose) {
        this.tclose = tclose;
    }

    public Time gettOpen() {
        return topen;
    }

    public void settOpen(Time topen) {
        this.topen = topen;
    }
}
