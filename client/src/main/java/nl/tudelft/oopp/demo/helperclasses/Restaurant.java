package nl.tudelft.oopp.demo.helperclasses;

import java.util.List;

public class Restaurant {

    String name;
    List<Dish> dishList;

    public Restaurant(String name, List<Dish> dishList) {
        this.name = name;
        this.dishList = dishList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }
}
