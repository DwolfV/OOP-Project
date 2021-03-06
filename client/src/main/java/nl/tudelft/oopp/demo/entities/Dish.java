package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class Dish {

    private long id;
    String name;
    String description;
    String type;
    float price;

    public Dish() {

    }

    /**
     * Create a new Dish instance.
     *
     * @param name        A name of the Dish.
     * @param description A short description of the dish and its contents.
     * @param type        Type of the dish, like fish, meat, drink, etc. or something more specific.
     * @param price       The price of that Dish.
     */

    public Dish(String name, String description, String type, float price) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dish dish = (Dish) o;
        return getId() == dish.getId()
                &&
                Float.compare(dish.getPrice(), getPrice()) == 0
                &&
                Objects.equals(getName(), dish.getName())
                &&
                Objects.equals(getDescription(), dish.getDescription())
                &&
                Objects.equals(getType(), dish.getType());
    }

}
