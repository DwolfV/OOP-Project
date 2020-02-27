package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Time;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    private Time tClose;

    @NotNull
    @Column(name = "t_open")
    private Time tOpen;

    public Restaurant() {}

    public Restaurant(long id, String name, Building building, Time tClose, Time tOpen) {
        this.id = id;
        this.name = name;
        this.building = building;
        this.tClose = tClose;
        this.tOpen = tOpen;
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
                && Objects.equals(tClose, that.tClose)
                && Objects.equals(tOpen, that.tOpen);
    }

}
