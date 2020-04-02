package nl.tudelft.oopp.demo.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.views.MainDisplay;

public class MainSceneController implements Initializable {

    public Parent headerRoot;
    public Parent hamburgerMenuRoot;
    public Parent calendarRoot;
    public static Parent emptySidebarLeftRoot;
    public static Parent emptySidebarRightRoot;

    public FXMLLoader headerLoader;
    public FXMLLoader emptySidebarLoaderLeft;
    public FXMLLoader emptySidebarLoaderRight;
    public FXMLLoader hamburgerMenuLoader;
    public FXMLLoader calendarLoader;

    public Parent sidebar;

    @FXML public BorderPane borderPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize StartupScene");
        headerLoader = new FXMLLoader(getClass().getResource("/Scenes/headerScene.fxml"));
        emptySidebarLoaderLeft = new FXMLLoader(getClass().getResource("/Scenes/emptySidebarScene.fxml"));
        emptySidebarLoaderRight = new FXMLLoader(getClass().getResource("/Scenes/emptySidebarSceneRight.fxml"));
        hamburgerMenuLoader = new FXMLLoader(getClass().getResource("/Scenes/hamburgerMenuScene.fxml"));
        calendarLoader = new FXMLLoader(getClass().getResource("/Scenes/calendarScene.fxml"));
        try {
            headerRoot = headerLoader.load();
            emptySidebarLeftRoot = emptySidebarLoaderLeft.load();
            emptySidebarRightRoot = emptySidebarLoaderRight.load();
            hamburgerMenuRoot = hamburgerMenuLoader.load();
            calendarRoot = calendarLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPane.setTop(headerRoot);
        borderPane.setLeft(emptySidebarLeftRoot);
        borderPane.setRight(emptySidebarRightRoot);
        borderPane.getRight().setPickOnBounds(false); // TESTING
        sidebar = emptySidebarLeftRoot;
        borderPane.setCenter(calendarRoot);

        HeaderSceneController headerSceneController = headerLoader.getController();
        headerSceneController.setController(this);
        CalendarSceneController calendarSceneController = calendarLoader.getController();
        calendarSceneController.setController(this);
        HamburgerMenuSceneController hamburgerMenuSceneController = hamburgerMenuLoader.getController();
        hamburgerMenuSceneController.setController(this, headerSceneController);
    }

    public Node getCenter() {
        return borderPane.getCenter();
    }

    public void changeCenter(Parent root) {
        borderPane.setCenter(root);
    }

}
