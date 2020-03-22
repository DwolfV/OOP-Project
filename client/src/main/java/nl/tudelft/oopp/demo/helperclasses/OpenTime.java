package nl.tudelft.oopp.demo.helperclasses;

import java.sql.Time;

public class OpenTime {

    private Long id;
    private String day;
    private Time openTime;
    private Time closeTime;
    private Building building;

    public OpenTime() {

    }


    /**
     * Creates a new instance of the OpenTime entity.
     * @param day - The day that corresponds to a specific openTime
     * @param openTime - The time when a specific building opens
     * @param closeTime - The time when a specific building closes
     * @param building - The building that corresponds to this OpenTime
     */


    public OpenTime(String day, Time openTime, Time closeTime, Building building) {
        this.day = day;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.building = building;
    }

    /**
     * Creates a new instance of the OpenTime entity.
     * @param id - The unique identifier corresponding to a specific openTime
     * @param day - The day that corresponds to a specific openTime
     * @param openTime - The time when a specific building opens
     * @param closeTime - The time when a specific building closes
     * @param building - The building that corresponds to this OpenTime
     */

    public OpenTime(Long id, String day, Time openTime, Time closeTime, Building building) {
        this.id = id;
        this.day = day;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.building = building;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Time getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @Override
    public String toString() {
        return "OpenTime{"
                + "id=" + id
                + ", day='" + day + '\''
                + ", openTime=" + openTime
                + ", closeTime=" + closeTime
                + ", building=" + building
                + '}';
    }
}
