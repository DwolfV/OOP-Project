package nl.tudelft.oopp.demo.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {

    private long id;
    private String name;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private User user;

    public Event() {

    }

    /**
     * Creates an instance of a new Event object.
     * @param name the name of the event
     * @param description description of the event
     * @param date date of the event
     * @param startTime start time of the event
     * @param endTime end time of the event
     * @param user user who created the event
     */
    public Event(String name,
                 String description,
                 LocalDate date,
                 LocalTime startTime,
                 LocalTime endTime,
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
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
