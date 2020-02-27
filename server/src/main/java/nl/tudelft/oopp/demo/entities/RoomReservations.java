package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import java.sql.Time;
import java.util.Date;
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
@Table(name = "Room_Reservation")
public class RoomReservations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotBlank
    @Column(name = "date")
    private Date date;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @NotBlank
    @Column(name = "start_time")
    private Time startTime;

    @NotBlank
    @Column(name = "end_time")
    private Time endTime;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * Create a new RoomReservation instance.
     *
     * @param id A unique identifier of the RoomReservation.
     * @param date Te date for which the Room is reserved.
     * @param room The room that is reserved.
     * @param startTime The start time of the RoomReservation.
     * @param endTime The end time of the RoomReservation.
     * @param user The user that has reserved the room.
     */

    public RoomReservations(long id,
                            Date date,
                            Room room,
                            Time startTime,
                            Time endTime,
                            User user) {
        this.id = id;
        this.date = date;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
    }

    @JsonIgnore
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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
        RoomReservations that = (RoomReservations) o;
        return id == that.id
                && Objects.equals(date, that.date)
                && Objects.equals(room, that.room)
                && Objects.equals(startTime, that.startTime)
                && Objects.equals(endTime, that.endTime)
                && Objects.equals(user, that.user);
    }
}
