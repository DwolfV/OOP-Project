package nl.tudelft.oopp.demo.entities;

import com.sun.istack.NotNull;

import java.sql.Date;
import java.sql.Time;
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


@Entity
@Table(name = "Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotBlank
    @Column(name = "date", nullable = false)
    private Date date;

    @NotBlank
    @Column(name = "startTime", nullable = false)
    private Time startTime;

    @NotBlank
    @Column(name = "endTime", nullable = false)
    private Time endTime;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Event() {

    }

    /**
     * Constructor for the Event entity.
     *
     * @param id - The auto-generated id
     * @param name - The name of the event
     * @param description - A description of the event
     * @param date - The date of the event
     * @param startTime - The start time of the event
     * @param endTime - The end time of the event
     * @param user - The user who created the event
     */
    public Event(long id,
                 String name,
                 String description,
                 Date date,
                 Time startTime,
                 Time endTime,
                 User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        return id == event.id;
    }

    @Override
    public String toString() {
        return "Event{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", date=" + date
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + ", user=" + user
                + '}';
    }
}
