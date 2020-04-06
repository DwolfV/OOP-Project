package nl.tudelft.oopp.demo.controllers;

import java.awt.Dimension;
import java.awt.Toolkit;
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
    @FXML public Pane pane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        sidebar = emptySidebarLeftRoot;
        borderPane.setCenter(calendarRoot);

        HeaderSceneController headerSceneController = headerLoader.getController();
        headerSceneController.setController(this);
        CalendarSceneController calendarSceneController = calendarLoader.getController();
        calendarSceneController.setController(this);
        HamburgerMenuSceneController hamburgerMenuSceneController = hamburgerMenuLoader.getController();
        hamburgerMenuSceneController.setController(this, headerSceneController);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        borderPane.setPrefWidth(screenSize.getWidth());
        borderPane.setPrefHeight(screenSize.getHeight());

    }

    public Node getCenter() {
        return borderPane.getCenter();
    }

    public void changeCenter(Parent root) {
        borderPane.setCenter(root);
    }

}
