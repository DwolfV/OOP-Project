package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "buildingId")
    private String buildingId;

    @Column(name = "roomName")
    private String roomName;

    @Column(name = "capacity")
    private int capacity;

    public Room(String buildingId, String roomName, int capacity) {
        this.buildingId = buildingId;
        this.roomName = roomName;
        this.capacity = capacity;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return getCapacity() == room.getCapacity() &&
                Objects.equals(getBuildingId(), room.getBuildingId()) &&
                Objects.equals(getRoomName(), room.getRoomName());
    }

}