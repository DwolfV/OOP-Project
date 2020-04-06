package nl.tudelft.oopp.demo.entities;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Equipment",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"item_id", "room_id"},
                        name = "unique_item_per_room_constraint"
                )
        })
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
    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    private Item item;

    @NotNull
    @Column(name = "amount", nullable = false)
    private int amount;

    public Equipment() {

    }

    /**
     * Create a new Equipment instance.
     *
     * @param item The item for the Equipment.
     * @param amount The amount of the Equipment that is in stock.
     * @param room The room for the Equipment.
     */

    public Equipment(Room room, Item item, int amount) {
        this.room = room;
        this.item = item;
        this.amount = amount;
    }

    /**
     * Create a new Equipment instance.
     *
     * @param id A unique identifier for the Equipment.
     * @param item The item for the Equipment.
     * @param amount The amount of the Equipment that is in stock.
     * @param room The room for the Equipment.
     */

    public Equipment(long id, Room room, Item item, int amount) {
        this.id = id;
        this.room = room;
        this.item = item;
        this.amount = amount;
    }

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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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
        Equipment equipment = (Equipment) o;
        return id == equipment.id;
    }
}
