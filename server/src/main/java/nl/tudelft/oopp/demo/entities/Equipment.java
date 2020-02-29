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
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @NotNull
    @Column(name = "name", length = 32)
    private String name;

    @NotNull
    @Column(name = "amount")
    private int amount;

    public Equipment() {

    }

    /**
     * Create a new RoomEquipment instance.
     * It is going to connect the Rooms to different Equipments.
     *
     * @param id The unique identifier for RoomEquipment.
     * @param room The room to which the RoomEquipment belongs.
     * @param name The Equipment like whi.
     * @param amount How much of the Equipment there is in the Room.
     */
    public Equipment(long id, Room room, String name, int amount) {
        this.id = id;
        this.room = room;
        this.name = name;
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

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
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
        Equipment that = (Equipment) o;
        return id == that.id
                && amount == that.amount
                && Objects.equals(room, that.room)
                && Objects.equals(name, that.name);
    }

}
