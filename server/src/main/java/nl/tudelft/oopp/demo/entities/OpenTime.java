package nl.tudelft.oopp.demo.entities;

import java.sql.Time;
import java.util.Objects;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
>>>>>>> server_test_controller_classes
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
    private Time t_open;

    @NotNull
    @Column (name = "t_close")
    private Time t_close;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private Building building;

    public OpenTime(String day, Time t_open, Time t_close, Building building) {
        this.day = day;
        this.t_open = t_open;
        this.t_close = t_close;
        this.building = building;

    }
    /**
     * Create a new OpenTime instance.
     *
     * @param id A unique ID for the OpenTime.
     * @param t_close The closing time of a building.
     * @param t_open The opening time of a building.
     * @param building The building for the OpenTime.
     * @param building_id The building's ID for the OpenTime.
     */

    public OpenTime(long id, String day, Time t_open, Time t_close, Building building) {
        this.id = id;
        this.day = day;
        this.t_open = t_open;
        this.t_close = t_close;
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

    public Time getT_open() {
        return t_open;
    }

    public void setT_open(Time t_open) {
        this.t_open = t_open;
    }

    public Time getT_close() {
        return t_close;
    }

    public void setT_close(Time t_close) {
        this.t_close = t_close;
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
                && Objects.equals(getT_open(), open_time.getT_open())
                && Objects.equals(getT_close(), open_time.getT_close())
                && Objects.equals(getBuilding(), open_time.getBuilding());
    }

}
