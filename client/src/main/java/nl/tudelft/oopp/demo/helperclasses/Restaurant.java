package nl.tudelft.oopp.demo.helperclasses;

import java.sql.Time;
import java.util.List;

public class Restaurant {

    private long id;
    private String name;
    private Building building;
    private Time tClose;
    private Time tOpen;

    public Restaurant(long id, String name, Building building, Time tClose, Time tOpen) {
        this.id = id;
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
