package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "accessories")
public class Accessory {

    @Id
    @Column(name = "buildingId")
    private String buildingId;

    @Column(name = "accessoryType")
    private String accessoryType;

    @Column(name = "stock")
    private int stock;

    public Accessory() {
    }

    public Accessory(String buildingId, String accessoryType, int stock) {
        this.buildingId = buildingId;
        this.accessoryType = accessoryType;
        this.stock = stock;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getAccessoryType() {
        return accessoryType;
    }

    public void setAccessoryType(String accessoryType) {
        this.accessoryType = accessoryType;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accessory accessory = (Accessory) o;
        return Objects.equals(getBuildingId(), accessory.getBuildingId()) &&
                Objects.equals(getAccessoryType(), accessory.getAccessoryType());
    }

    @Override
    public String toString() {
        return "Accessory{" +
                "buildingId='" + buildingId + '\'' +
                ", accessoryType='" + accessoryType + '\'' +
                ", stock=" + stock +
                '}';
    }
}
