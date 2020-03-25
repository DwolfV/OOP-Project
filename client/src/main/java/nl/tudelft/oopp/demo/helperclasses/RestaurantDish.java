package nl.tudelft.oopp.demo.helperclasses;

import java.util.Set;

public class RestaurantDish {

    private Long id;
    private Dish dish;
    private Restaurant restaurant;
    private Set<DishOrder> dishOrders;

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

    public Set<DishOrder> getDishOrders() {
        return dishOrders;
    }

    public void setDishOrders(Set<DishOrder> dishOrders) {
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
