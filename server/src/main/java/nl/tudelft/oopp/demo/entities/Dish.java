package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Dish")
public class Dish {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private float price;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    private Set<RestaurantDish> restaurantDishes = new HashSet<>();

    public Dish() {

    }

    /**
     * Create a new Dish instance.
     *
     * @param name The name of the Dish.
     * @param description The description of the Dish; for example: the ingredients.
     * @param price The price of the Dish.
     * @param type The type of the Dish; for example: vegan, vegetarian etc.
     */


    public Dish(String name, String description, String type, float price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Set<RestaurantDish> getRestaurantDishes() {
        return restaurantDishes;
    }

    public void setRestaurantDishes(Set<RestaurantDish> restaurantDishes) {
        this.restaurantDishes = restaurantDishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dish dish = (Dish) o;
        return getId() == dish.getId();
    }

    @Override
    public String toString() {
        return "Dish{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", description='"
                + description
                + '\''
                + ", type='"
                + type
                + '\''
                + ", price="
                + price
                + '}';
    }
}
