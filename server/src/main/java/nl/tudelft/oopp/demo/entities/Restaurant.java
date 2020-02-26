package nl.tudelft.oopp.demo.entities;

import java.sql.Time;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "Restaurant")
public class Restaurant {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private Building building;

    private long buildingId;

    @Column(name = "t_close")
    private Time tClose;

    @Column(name = "t_open")
    private Time tOpen;

    public Restaurant() {

    }

    public Restaurant(long id, String name, long buildingId, Time tClose, Time tOpen) {
        this.id = id;
        this.name = name;
        this.buildingId = buildingId;
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

    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return getId() == that.getId();
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", building=" + building +
                ", buildingId=" + buildingId +
                ", tClose=" + tClose +
                ", tOpen=" + tOpen +
                '}';
    }
}
