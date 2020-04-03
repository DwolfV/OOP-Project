package nl.tudelft.oopp.demo.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Invitation")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User guest;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "room_reservation_id", referencedColumnName = "id")
    private RoomReservation roomReservation;

    public Invitation() {

    }

    public Invitation(User user, RoomReservation roomReservation) {
        this.guest = user;
        this.roomReservation = roomReservation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public RoomReservation getRoomReservation() {
        return roomReservation;
    }

    public void setRoomReservation(RoomReservation roomReservation) {
        this.roomReservation = roomReservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Invitation that = (Invitation) o;
        return id == that.id;
    }

    @Override
    public String toString() {
        return "Invitation{"
                + "id=" + id
                + ", guest=" + guest
                + ", roomReservation=" + roomReservation
                + '}';
    }
}


