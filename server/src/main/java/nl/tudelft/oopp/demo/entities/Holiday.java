package nl.tudelft.oopp.demo.entities;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

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

    @Column (name = "t_open")
    private Time t_open;

    @Column (name = "t_close")
    private Time t_close;


    public Holiday(String name, Date date, Time t_open, Time t_close) {
        this.name = name;
        this.date = date;
        this.t_open = t_open;
        this.t_close = t_close;
    }

    /**
     * Create a new Holiday instance.
     *
     * @param id A unique ID for the Holiday.
     * @param date The date of the Holiday.
     * @param name The name of the Holiday.
     * @param t_close The closing time of a building during a Holiday.
     * @param t_open The opening time of a building during a Holiday.
     */

    public Holiday(long id, String name, Date date, Time t_open, Time t_close) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.t_open = t_open;
        this.t_close = t_close;
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
