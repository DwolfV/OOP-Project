package nl.tudelft.oopp.demo.entities;

public class Invitation {

    private long id;
    private RoomReservation roomReservation;
    private User guest;

    /**
     *  Empty constructor.
     */
    public Invitation() {

    }

    /**
     * Constructor with room reservation and guest.
     *
     * @param guest - invited user
     * @param roomReservation - a room reservation
     */
    public Invitation(User guest, RoomReservation roomReservation) {
        this.roomReservation = roomReservation;
        this.guest = guest;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoomReservation getRoomReservation() {
        return roomReservation;
    }

    public void setRoomReservation(RoomReservation roomReservation) {
        this.roomReservation = roomReservation;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
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
                + ", roomReservation=" + roomReservation
                + ", guest=" + guest
                + '}';
    }
}
