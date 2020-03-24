package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalTime;
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
    private LocalTime timeClose;

    @NotNull
    @Column(name = "t_open")
    private LocalTime timeOpen;

    public Restaurant() {

    }
    /**
     * Create a new Restaurant instance.
     *
     * @param name     The name of the Holiday.
     * @param building The name of the building that the Restaurant is located in.
     * @param timeClose   The closing time of the Restaurant.
     * @param timeOpen    The opening time of the Restaurant.
     */

    public Restaurant(String name, Building building, LocalTime timeClose, LocalTime timeOpen) {
        this.name = name;
        this.building = building;
        this.timeClose = timeClose;
        this.timeOpen = timeOpen;
    }

    /**
     * Create a new Restaurant instance.
     *
     * @param id       A unique identifier for the Restaurant.
     * @param name     The name of the Holiday.
     * @param building The name of the building that the Restaurant is located in.
     * @param timeClose   The closing time of the Restaurant.
     * @param timeOpen    The opening time of the Restaurant.
     */

    public Restaurant(long id, String name, Building building, LocalTime timeClose, LocalTime timeOpen) {
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
