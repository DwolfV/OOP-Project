package nl.tudelft.oopp.demo.controllers;

import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HeaderSceneController implements Initializable {

    private MainSceneController mainSceneController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    @FXML
    public void switchSidebar(MouseEvent event) {
        mainSceneController.switchSidebar();
    }
}