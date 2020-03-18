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

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private Date date;

    @Column(name = "t_open")
    private Time timeOpen;

    @Column(name = "t_close")
    private Time timeClose;

    /**
     * Created an instance of the Holiday object.
     * @param name The name of the holiday
     * @param date The date of the holiday
     * @param timeOpen The time at which the Building opens on this Holiday.
     * @param timeClose The time at which the Building closes on this Holiday.
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
     * @param id      A unique ID for the Holiday.
     * @param date    The date of the Holiday.
     * @param name    The name of the Holiday.
     * @param timeClose The closing time of a building during a Holiday.
     * @param timeOpen  The opening time of a building during a Holiday.
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
            && Objects.equals(getTimeOpen(), holiday.getTimeOpen())
            && Objects.equals(getTimeClose(), holiday.getTimeClose());
    }

}
