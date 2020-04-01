package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuSceneController implements Initializable {
    private long id;
    private String restaurantName;
    private String restaurantBuilding;

//    @FXML
//    private Label restaurantNameLabel;

    private MainSceneController mainSceneController;

    /**
     * TODO Displays the dishes available in a certain restaurant
     * @param location  url location
     * @param resources resource bundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        restaurantNameLabel.setText(getRestaurantName());
        System.out.println(getId()+" "+getRestaurantBuilding()+" "+getRestaurantName());
    }


    /**
     * Set controllers for the class.
     * @param mainSceneController Main Scene
     */
    public void setController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    /**
     *
     * @return The id of the restaurant of which the menu is displayed
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of a restaurant
     * @param id
     */
    public void setId(long id) {
        this.id = id;
        System.out.println("ala bun " + id);
    }

    /**
     *
     * @return The name of the restaurant of which the menu is displayed
     */
    public String getRestaurantName(){
        return restaurantName;
    }

    /**
     * Sets the name of a restaurant
     * @param restaurantName
     */
    public void setRestaurantName(String restaurantName){
        this.restaurantName = restaurantName;
        System.out.println("ala bun " + restaurantName);
        //restaurantNameLabel.setText(getRestaurantName());
    }

    /**
     *
     * @return The building where the restaurant of which the menu is displayed is located
     */
    public String getRestaurantBuilding(){
        return restaurantBuilding;
    }

    /**
     * Sets the name of the building in which a restaurant is located
     * @param restaurantBuilding
     */
    public void setRestaurantBuilding(String restaurantBuilding) {
        this.restaurantBuilding = restaurantBuilding;
        System.out.println("ala bun " + restaurantBuilding);
    }
}
