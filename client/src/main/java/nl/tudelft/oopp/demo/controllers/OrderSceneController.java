package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderSceneController implements Initializable {

    private MainSceneController mainSceneController;
    private  HamburgerMenuSceneController hamburgerMenuSceneController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setController(MainSceneController mainSceneController, HamburgerMenuSceneController hamburgerMenuSceneController){
        this.mainSceneController = mainSceneController;
        this.hamburgerMenuSceneController = hamburgerMenuSceneController;
    }
}
