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
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Room")
public class Room {
    // TODO nullable = false

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "name", length = 32, nullable = false)
    private String name;


    @NotNull
    @Column(name = "capacity", nullable = false)
    private int capacity;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private Building building;

    public Room() {

    }

    /**
     * Create a new Room instance.
     *
     * @param id The unique ID of the Room that is used to identify it.
     * @param name The name of the Room.
     * @param capacity The capacity of the room.
     * @param building The building, in which the room is located.
     */

    public Room(long id, String name, int capacity, Building building) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.building = building;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return id == room.id
                && capacity == room.capacity
                && Objects.equals(name, room.name)
                && Objects.equals(building, room.building);
    }

}