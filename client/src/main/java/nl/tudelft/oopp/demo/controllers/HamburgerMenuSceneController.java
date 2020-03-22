package nl.tudelft.oopp.demo.controllers;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HamburgerMenuSceneController implements Initializable {

    private MainSceneController mainSceneController;

    @FXML public JFXHamburger hamburger;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }
}
