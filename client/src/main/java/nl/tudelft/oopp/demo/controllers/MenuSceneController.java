package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.Initializable;
import nl.tudelft.oopp.demo.communication.DishCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantDishCommunication;
import nl.tudelft.oopp.demo.helperclasses.Dish;
import nl.tudelft.oopp.demo.helperclasses.Restaurant;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MenuSceneController implements Initializable {

    private static List<Dish> rdList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ObservableList<Restaurant> restaurants = FXCollections.observableList(RestaurantCommunication.getRestaurants());
        System.out.println("Load MenuSceneController");
    }

    public static void loadDishes() {
//        for(int i = 0; i<10; i++) {
//            String name = "name" + i;
//            String desc = "A nicely prepared " + name + " dish, with a little spice.";
//            DishCommunication.addDish(name, desc, "food", 10 + i);
//        }
        List<Dish> dishes = DishCommunication.getDishes();
        System.out.println("Dish list:/n" + dishes.toString());
        for(int i = 0; i<10; i++) {
            Restaurant restaurant = RestaurantCommunication.getRestaurantById(249);
            Dish dish = dishes.get(i);
            //RestaurantDishCommunication.addLinkRestaurantDish(dish, restaurant);
            System.out.println("Added: " + dish + " in " + restaurant);
        }
    }

    public static void loadMenu(long restaurantId) {
        loadDishes();
        rdList = RestaurantDishCommunication.getDishesByRestaurant(restaurantId);
        for(int i = 0; i < rdList.size(); i++) {
            System.out.println(rdList.get(i));
        }
    }
}
