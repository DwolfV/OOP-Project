package nl.tudelft.oopp.demo.entities;

import java.sql.Time;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table (name = "Open_Time")
public class OpenTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @NotBlank
    @Column(name = "day")
    private String day;

    @NotNull
    @Column (name = "t_open")
    private Time openTime;

    @NotNull
    @Column (name = "t_close")
    private Time closeTime;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private Building building;

    public OpenTime() {

    }

    public OpenTime(String day, Time openTime, Time closeTime, Building building) {
        this.day = day;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.building = building;

    }
    /**
     * Create a new OpenTime instance.
     *
     * @param id A unique ID for the OpenTime.
     * @param closeTime The closing time of a building.
     * @param openTime The opening time of a building.
     * @param building The building for the OpenTime.
     * @param building The building's ID for the OpenTime.
     */

    public OpenTime(long id, String day, Time openTime, Time closeTime, Building building) {
        this.id = id;
        this.day = day;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.building = building;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OpenTime open_time = (OpenTime) o;
        return getId() == open_time.getId()
                && Objects.equals(getDay(), open_time.getDay())
                && Objects.equals(getOpenTime(), open_time.getOpenTime())
                && Objects.equals(getCloseTime(), open_time.getCloseTime())
                && Objects.equals(getBuilding(), open_time.getBuilding());
    }

}
