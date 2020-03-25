package nl.tudelft.oopp.demo.helperclasses;


import java.time.LocalDate;
import java.time.LocalTime;

public class Occasion {

    private LocalDate date;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Building building;

    public Occasion() {

    }

    public Occasion(LocalDate date, LocalTime openTime, LocalTime closeTime) {
        this.date = date;
        this.closeTime = closeTime;
        this.openTime = openTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
