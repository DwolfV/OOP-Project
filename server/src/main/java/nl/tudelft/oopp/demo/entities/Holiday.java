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
    private Time tOpen;

    @Column(name = "t_close")
    private Time tClose;


    public Holiday(String name, Date date, Time tOpen, Time tClose) {
        this.name = name;
        this.date = date;
        this.tOpen = tOpen;
        this.tClose = tClose;
    }

    /**
     * Create a new Holiday instance.
     *
     * @param id      A unique ID for the Holiday.
     * @param date    The date of the Holiday.
     * @param name    The name of the Holiday.
     * @param tClose The closing time of a building during a Holiday.
     * @param tOpen  The opening time of a building during a Holiday.
     */

    public Holiday(long id, String name, Date date, Time tOpen, Time tClose) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.tOpen = tOpen;
        this.tClose = tClose;
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

    public Time gettOpen() {
        return tOpen;
    }

    public void settOpen(Time tOpen) {
        this.tOpen = tOpen;
    }

    public Time gettClose() {
        return tClose;
    }

    public void settClose(Time tClose) {
        this.tClose = tClose;
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
            && Objects.equals(gettOpen(), holiday.gettOpen())
            && Objects.equals(gettClose(), holiday.gettClose());
    }

}
