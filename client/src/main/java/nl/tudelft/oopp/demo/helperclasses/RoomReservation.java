package nl.tudelft.oopp.demo.helperclasses;

import java.time.LocalDate;
import java.time.LocalTime;

public class RoomReservation {

    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private User user;
    private Room room;

    public RoomReservation() {
    }

    /**
     * Create a new Building instance.
     *
     * @param date      The date of the room reservation
     * @param startTime The starting time of the reservation
     * @param endTime   The time when the reservation ends
     * @param user      The user who owns the reservation
     * @param room      The room that the reservation is linked to
     */
    public RoomReservation(LocalDate date, LocalTime startTime, LocalTime endTime, User user, Room room) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
