package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table (name = "Open_Time")
public class Open_Time {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "day")
    private String day;

    @Column ( name = "t_open" )
    private Time t_open;

    @Column ( name = "t_close" )
    private Time t_close;

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private Building building;

    private long building_id;

    public Open_Time(long id, String day, Time t_open, Time t_close, Building building, long building_id) {
        this.id = id;
        this.day = day;
        this.t_open = t_open;
        this.t_close = t_close;
        this.building = building;
        this.building_id = building_id;
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

    public long getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(long building_id) {
        this.building_id = building_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Open_Time open_time = (Open_Time) o;
        return getId() == open_time.getId() &&
                getBuilding_id() == open_time.getBuilding_id() &&
                Objects.equals(getDay(), open_time.getDay()) &&
                Objects.equals(getT_open(), open_time.getT_open()) &&
                Objects.equals(getT_close(), open_time.getT_close()) &&
                Objects.equals(getBuilding(), open_time.getBuilding());
    }

}
