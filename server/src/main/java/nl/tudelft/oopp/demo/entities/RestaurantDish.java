package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Restaurant_Dish")
public class RestaurantDish {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "dish_id", referencedColumnName = "id")
    private Dish dish;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    @Nullable
    private Set<DishOrder> dishOrders = new HashSet<>();


    public RestaurantDish(){

    }

    public RestaurantDish(long id, Restaurant restaurant, Dish dish) {
        this.id = id;
        this.restaurant = restaurant;
        this.dish = dish;
    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    @Nullable
    public Set<DishOrder> getDishOrders() {
        return dishOrders;
    }

    public void setDishOrders(@Nullable Set<DishOrder> dishOrders) {
        this.dishOrders = dishOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestaurantDish that = (RestaurantDish) o;
        return id == that.id;
    }

}