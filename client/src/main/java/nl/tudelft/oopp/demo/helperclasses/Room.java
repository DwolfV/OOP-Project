package nl.tudelft.oopp.demo.helperclasses;

import java.util.Objects;

public class Room {

    private Long id;

    private String name;

    private Integer capacity;

    private Building building;

    public Room() {

    }

    /**
     * Create a new Room instance.
     *
     * @param name The name of the Room.
     * @param capacity The capacity of the room.
     * @param building The building, in which the room is located.
     */
    public Room(String name, Integer capacity, Building building) {
        this.name = name;
        this.capacity = capacity;
        this.building = building;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
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

    @Override
    public String toString() {
        return "Room{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", capacity=" + capacity
                + ", building=" + building
                + '}';
    }
}
