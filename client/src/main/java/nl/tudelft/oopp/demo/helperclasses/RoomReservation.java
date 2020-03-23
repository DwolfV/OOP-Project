package nl.tudelft.oopp.demo.helperclasses;

import java.sql.Date;
import java.sql.Time;

public class RoomReservation {

    private long id;
    private Date date;
    private Time startTime;
    private Time endTime;
    private User user;
    private Room room;

    /**
     * Create a new Building instance.
     *
     * @param id        The unique identifier for Building
     * @param date      The date of the room reservation
     * @param startTime The starting time of the reservation
     * @param endTime   The time when the reservation ends
     * @param user      The user who owns the reservation
     */
    public RoomReservation(Date date, Time startTime, Time endTime, User user, Room room) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
        this.room = room;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoomReservation that = (RoomReservation) o;
        return id == that.id;
    }

    @Override
    public String toString() {
        return "RoomReservation{"
                + ", date=" + date
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + ", user=" + user
                + '}';
    }
}
