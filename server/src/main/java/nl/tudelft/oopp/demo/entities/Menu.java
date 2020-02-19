package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @Column(name = "restaurantName")
    private String restaurantName;

    @Id
    @Column(name = "dishName")
    private String dishName;

    @Column(name = "price")
    private int price;

    @Column(name = "vegetarian")
    private boolean vegetarian;

    public Menu() {

    }

    public Menu(String restaurantName, String dishName, int price, boolean vegetarian) {
        this.restaurantName = restaurantName;
        this.dishName = dishName;
        this.price = price;
        this.vegetarian = vegetarian;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(getRestaurantName(), menu.getRestaurantName()) &&
                Objects.equals(getDishName(), menu.getDishName());
    }

    @Override
    public String toString() {
        return "Menu{" +
                "restaurantName='" + restaurantName + '\'' +
                ", dishName='" + dishName + '\'' +
                ", price=" + price +
                ", vegetarian=" + vegetarian +
                '}';
    }
}
