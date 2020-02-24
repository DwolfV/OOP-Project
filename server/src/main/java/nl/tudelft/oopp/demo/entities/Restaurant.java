package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @Column(name = "restaurantName")
    private String restaurantName;

    @Column(name = "buildingName")
    private String buildingName;

    @Column(name = "openTime")
    private Time openTime;

    @Column(name = "closeTime")
    private Time closeTime;

    public Restaurant() {

    }

    public Restaurant(String restaurantName, String buildingName, Time openTime, Time closeTime) {
        this.restaurantName = restaurantName;
        this.buildingName = buildingName;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Time getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(getRestaurantName(), that.getRestaurantName()) &&
                Objects.equals(getBuildingName(), that.getBuildingName());
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantName='" + restaurantName + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                '}';
    }
}
