package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.springframework.lang.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
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
    @JoinColumn(name = "building_id", referencedColumnName = "id", nullable = false)
    private Building building;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @Nullable
    private Set<Equipment> equipment = new HashSet<>();

    public Room() {

    }

    public Room(String name, Integer capacity, Building building) {
        this.name = name;
        this.capacity = capacity;
        this.building = building;
    }

    /**
     * Create a new Room instance.
     *
     * @param id The unique ID of the Room that is used to identify it.
     * @param name The name of the Room.
     * @param capacity The capacity of the room.
     * @param building The building, in which the room is located.
     */

    public Room(long id, String name, Integer capacity, Building building) {
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