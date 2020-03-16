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
@Table(name = "Open_Time")
public class OpenTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @NotBlank
    @Column(name = "day")
    private String day;

    @NotNull
    @Column(name = "t_open")
    private Time timeOpen;

    @NotNull
    @Column(name = "t_close")
    private Time timeClose;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private Building building;

    public OpenTime(String day, Time timeOpen, Time timeClose, Building building) {
        this.day = day;
        this.timeOpen = timeOpen;
        this.timeClose = timeClose;
        this.building = building;

    }

    /**
     * Create a new OpenTime instance.
     *
     * @param id          A unique ID for the OpenTime.
     * @param timeClose     The closing time of a building.
     * @param timeOpen      The opening time of a building.
     * @param building    The building for the OpenTime.
     */

    public OpenTime(long id, String day, Time timeOpen, Time timeClose, Building building) {
        this.id = id;
        this.day = day;
        this.timeOpen = timeOpen;
        this.timeClose = timeClose;
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

    public Time getTimeOpen() {
        return timeOpen;
    }

    public void setTimeOpen(Time timeOpen) {
        this.timeOpen = timeOpen;
    }

    public Time getTimeClose() {
        return timeClose;
    }

    public void setTimeClose(Time timeClose) {
        this.timeClose = timeClose;
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
        OpenTime openTime = (OpenTime) o;
        return getId() == openTime.getId()
            && Objects.equals(getDay(), openTime.getDay())
            && Objects.equals(getTimeOpen(), openTime.getTimeOpen())
            && Objects.equals(getTimeClose(), openTime.getTimeClose())
            && Objects.equals(getBuilding(), openTime.getBuilding());
    }

}
