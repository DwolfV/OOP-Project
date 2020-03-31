package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuSceneController {
    private MainSceneController mainSceneController;

    /**
     * TODO Displays the dishes available in a certain restaurant
     * @param location  url location
     * @param resources resource bundle
     */
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Set controllers for the class.
     * @param mainSceneController Main Scene
     */
    public void setController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

}
