package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Time;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private Building building;

    @NotNull
    @Column(name = "t_close")
    private Time timeClose;

    @NotNull
    @Column(name = "t_open")
    private Time timeOpen;

    public Restaurant() {

    }

    public Restaurant(String name, Building building, Time timeClose, Time timeOpen) {
        this.name = name;
        this.building = building;
        this.timeClose = timeClose;
        this.timeOpen = timeOpen;
    }

    /**
     * Create a new Restaurant instance.
     *
     * @param id A unique identifier for the Restaurant.
     * @param name The name of the Holiday.
     * @param building The name of the building that the Restaurant is located in.
     * @param timeClose The closing time of the Restaurant.
     * @param timeOpen The opening time of the Restaurant.
     */

    public Restaurant(long id, String name, Building building, Time timeClose, Time timeOpen) {
        this.id = id;
        this.name = name;
        this.building = building;
        this.timeClose = timeClose;
        this.timeOpen = timeOpen;
    }

    @JsonIgnore
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Restaurant that = (Restaurant) o;
        return id == that.id
                && Objects.equals(name, that.name)
                && Objects.equals(building, that.building)
                && Objects.equals(timeClose, that.timeClose)
                && Objects.equals(timeOpen, that.timeOpen);
    }

}
