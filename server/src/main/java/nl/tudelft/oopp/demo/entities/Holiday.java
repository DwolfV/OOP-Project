package nl.tudelft.oopp.demo.entities;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Holiday")
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column (name = "name")
    private String name;

    @Column (name = "date")
    private Date date;

    @Column (name = "timeOpen")
    private Time timeOpen;

    @Column (name = "timeClose")
    private Time timeClose;

    /**
     * Create a new Holiday instance.
     *
     * @param date The date of the Holiday.
     * @param name The name of the Holiday.
     * @param timeOpen The closing time of a building during a Holiday.
     * @param timeClose The opening time of a building during a Holiday.
     */


    public Holiday(String name, Date date, Time timeOpen, Time timeClose) {
        this.name = name;
        this.date = date;
        this.timeOpen = timeOpen;
        this.timeClose = timeClose;
    }

    /**
     * Create a new Holiday instance.
     *
     * @param id A unique ID for the Holiday.
     * @param date The date of the Holiday.
     * @param name The name of the Holiday.
     * @param timeOpen The closing time of a building during a Holiday.
     * @param timeClose The opening time of a building during a Holiday.
     */

    public Holiday(long id, String name, Date date, Time timeOpen, Time timeClose) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeOpen = timeOpen;
        this.timeClose = timeClose;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getT_open() {
        return timeOpen;
    }

    public void setT_open(Time timeOpen) {
        this.timeOpen = timeOpen;
    }

    public Time getT_close() {
        return timeClose;
    }

    public void setT_close(Time timeClose) {
        this.timeClose = timeClose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Holiday holiday = (Holiday) o;
        return getId() == holiday.getId()
                && Objects.equals(getName(), holiday.getName())
                && Objects.equals(getDate(), holiday.getDate())
                && Objects.equals(getT_open(), holiday.getT_open())
                && Objects.equals(getT_close(), holiday.getT_close());
    }

}
