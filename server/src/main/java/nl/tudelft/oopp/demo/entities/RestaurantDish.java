package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Restaurant_Dish")
public class RestaurantDish {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private long restaurantId;

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private long buildingId;

    public RestaurantDish(long id, long restaurantId, long buildingId) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.buildingId = buildingId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantDish that = (RestaurantDish) o;
        return getId() == that.getId();
    }

    @Override
    public String toString() {
        return "RestaurantDish{" +
                "id=" + id +
                ", restaurantId=" + restaurantId +
                ", buildingId=" + buildingId +
                '}';
    }
}