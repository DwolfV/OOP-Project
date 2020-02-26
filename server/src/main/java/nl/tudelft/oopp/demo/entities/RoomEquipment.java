package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Room_Equipment")
public class RoomEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private Equipment equipment;

    @NotNull
    @Column(name = "amount")
    private int amount;


    /**
     * Create a new RoomEquipment instance.
     * It is going to connect the Rooms to different Equipments.
     *
     * @param id The unique identifier for RoomEquipment.
     * @param room The room to which the RoomEquipment belongs.
     * @param equipment The Equipment that is contained in the Room.
     * @param amount How much of the Equipment there is in the Room.
     */
    public RoomEquipment(long id, Room room, Equipment equipment, int amount) {
        this.id = id;
        this.room = room;
        this.equipment = equipment;
        this.amount = amount;
    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoomEquipment that = (RoomEquipment) o;
        return id == that.id
                && amount == that.amount
                && Objects.equals(room, that.room)
                && Objects.equals(equipment, that.equipment);
    }

}
