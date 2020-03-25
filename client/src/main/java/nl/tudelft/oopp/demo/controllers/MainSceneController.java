package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainSceneController implements Initializable {

    public Parent headerRoot;
    public Parent sidebarRoot;
    public Parent hamburgerMenuRoot;
    public Parent calendarRoot;
    public Parent sidebar;

    public FXMLLoader headerLoader;
    public FXMLLoader sidebarLoader;
    public FXMLLoader hamburgerMenuLoader;
    public FXMLLoader calendarLoader;

    @FXML public BorderPane borderPane;
    @FXML public VBox vbox;
    @FXML public Pane tempPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize StartupScene");
        headerLoader = new FXMLLoader(getClass().getResource("/Scenes/headerScene.fxml"));
        sidebarLoader = new FXMLLoader(getClass().getResource("/Scenes/sidebarScene.fxml"));
        hamburgerMenuLoader = new FXMLLoader(getClass().getResource("/Scenes/hamburgerMenuScene.fxml"));
        calendarLoader = new FXMLLoader(getClass().getResource("/Scenes/calendarScene.fxml"));
        try {
            headerRoot = headerLoader.load();
            sidebarRoot = sidebarLoader.load();
            hamburgerMenuRoot = hamburgerMenuLoader.load();
            calendarRoot = calendarLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPane.setTop(headerRoot);
        borderPane.setLeft(sidebarRoot);
        sidebar = sidebarRoot;
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
