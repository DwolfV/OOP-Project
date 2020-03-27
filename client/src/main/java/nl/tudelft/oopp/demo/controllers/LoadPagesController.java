package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class LoadPagesController {

    public FXMLLoader mainLoader;
    public FXMLLoader headerLoader;
    public FXMLLoader hamburgerMenuLoader;
    public FXMLLoader adminLoader;
    public FXMLLoader calendarLoader;
    public FXMLLoader reservationLoader;
    public FXMLLoader restaurantLoader;
    public FXMLLoader sidebarLoader;

    public Parent mainRoot;
    public Parent headerRoot;
    public Parent hamburgerMenuRoot;
    public Parent adminRoot;
    public Parent calendarRoot;
    public Parent reservationRoot;
    public Parent restaurantRoot;
    public Parent sidebarRoot;

    public MainSceneController mainSceneController;
    public HeaderSceneController headerSceneController;
    public HamburgerMenuSceneController hamburgerMenuSceneController;
    public AdminSceneController adminSceneController;
    public CalendarSceneController calendarSceneController;
    public ReservationSceneController reservationSceneController;
    public RestaurantSceneController restaurantSceneController;
    public SidebarSceneController sidebarSceneController;

    public void loadAll() {
        mainLoader = new FXMLLoader(getClass().getResource("/Scenes/mainScene.fxml"));
        headerLoader = new FXMLLoader(getClass().getResource("/Scenes/headerScene.fxml"));
        hamburgerMenuLoader = new FXMLLoader(getClass().getResource("/Scenes/hamburgerMenuScene.fxml"));
        adminLoader = new FXMLLoader(getClass().getResource("/Scenes/adminScene.fxml"));
        calendarLoader = new FXMLLoader(getClass().getResource("/Scenes/calendarScene.fxml"));
        reservationLoader = new FXMLLoader(getClass().getResource("/Scenes/reservationScene.fxml"));
        restaurantLoader = new FXMLLoader(getClass().getResource("/Scenes/restaurantScene.fxml"));
        sidebarLoader = new FXMLLoader(getClass().getResource("/Scenes/sidebarScene.fxml"));
        try {
            mainRoot = mainLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
