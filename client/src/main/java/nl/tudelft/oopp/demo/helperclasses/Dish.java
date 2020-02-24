package nl.tudelft.oopp.demo.helperclasses;

public class Dish {

    String name;
    String description;
    String type;
    float price;

    /**
     * Create a new Dish instance.
     *
     * @param name A name of the Dish.
     * @param description A short description of the dish and its contents.
     * @param type Type of the dish, like fish, meat, drink, etc. or something more specific.
     * @param price The price of that Dish.
     */

    public Dish(String name, String description, String type, float price) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
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
}
