package nl.tudelft.oopp.demo.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roomfeatures")
public class RoomFeature {

    @Id
    @Column(name = "buildingId")
    private String buildingId;

    @Column(name = "roomName")
    private String roomName;

    @Column(name = "objectName")
    private String objectName;

    @Column(name = "quantity")
    private String quantity;

    public RoomFeature() {

    }

    /**
     * Create a new RoomFeature instance.
     *
     * @param buildingId The id of the Building in which the Room is located
     * @param roomName The name of the room
     * @param objectName The object that is located in the room.
     * @param quantity The amount of such objects there are in the room.
     */
    public RoomFeature(String buildingId, String roomName, String objectName, String quantity) {
        this.buildingId = buildingId;
        this.roomName  = roomName;
        this.objectName = objectName;
        this.quantity = quantity;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoomFeature feature = (RoomFeature) o;

        return buildingId == feature.buildingId
                && roomName == feature.roomName
                && objectName == feature.objectName;
    }
}

