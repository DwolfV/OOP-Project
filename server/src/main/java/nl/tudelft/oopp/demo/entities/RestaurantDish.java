package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

@Entity
@Table(name = "Restaurant_Dish",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"restaurant_id", "dish_id"},
                        name = "unique_dish_per_restaurant_constraint"
                )
        })
public class RestaurantDish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false)
    private Restaurant restaurant;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "dish_id", referencedColumnName = "id", nullable = false)
    private Dish dish;

    //@OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    //@Nullable
    //private Set<DishOrder> dishOrders = new HashSet<>();


    public RestaurantDish(){

    }

    public RestaurantDish(Restaurant restaurant, Dish dish) {
        this.restaurant = restaurant;
        this.dish = dish;
    }

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

    //    @Nullable
    //    @JsonIgnore
    //    public Set<DishOrder> getDishOrders() {
    //        return dishOrders;
    //    }
    //
    //    public void setDishOrders(@Nullable Set<DishOrder> dishOrders) {
    //        this.dishOrders = dishOrders;
    //    }

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