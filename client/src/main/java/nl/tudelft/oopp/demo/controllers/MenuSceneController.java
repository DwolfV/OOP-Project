package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.helperclasses.Restaurant;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuSceneController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ObservableList<Restaurant> restaurants = FXCollections.observableList(RestaurantCommunication.getRestaurants());
    }

    public static void loadMenu(long restaurantId) {

    }
}
