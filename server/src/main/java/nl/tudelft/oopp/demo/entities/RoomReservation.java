package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "Room_Reservation")
public class RoomReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    private Room room;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "roomReservation", cascade = CascadeType.ALL)
    @Nullable
    private Set<Order> orders = new HashSet<>();

    public RoomReservation() {
    }

    /**
     * Create a new RoomReservation instance.
     *
     * @param date      Te date for which the Room is reserved.
     * @param room      The room that is reserved.
     * @param startTime The start time of the RoomReservation.
     * @param endTime   The end time of the RoomReservation.
     * @param user      The user that has reserved the room.
     */

    public RoomReservation(LocalDate date,
                           Room room,
                           LocalTime startTime,
                           LocalTime endTime,
                           User user) {
        this.date = date;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
    }

    /**
     * Create a new RoomReservation instance.
     *
     * @param id        A unique identifier of the RoomReservation.
     * @param date      Te date for which the Room is reserved.
     * @param room      The room that is reserved.
     * @param startTime The start time of the RoomReservation.
     * @param endTime   The end time of the RoomReservation.
     * @param user      The user that has reserved the room.
     */

    public RoomReservation(long id,
                           LocalDate date,
                           Room room,
                           LocalTime startTime,
                           LocalTime endTime,
                           User user) {
        this.id = id;
        this.date = date;
        this.room = room;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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

    @JsonIgnore
    @Nullable
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(@Nullable Set<Order> orders) {
        this.orders = orders;
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
        return id == that.id
                && Objects.equals(date, that.date)
                && Objects.equals(room, that.room)
                && Objects.equals(startTime, that.startTime)
                && Objects.equals(endTime, that.endTime)
                && Objects.equals(user, that.user);
    }
}
