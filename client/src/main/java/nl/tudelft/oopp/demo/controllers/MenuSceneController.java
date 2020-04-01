package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.Initializable;
import nl.tudelft.oopp.demo.communication.RestaurantDishCommunication;
import nl.tudelft.oopp.demo.helperclasses.Dish;

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

    public static void loadMenu(long restaurantId) {
        rdList = RestaurantDishCommunication.getDishesByRestaurant(restaurantId);
        for(int i = 0; i < rdList.size(); i++) {
            System.out.println(rdList.get(i));
        }
    }
}
