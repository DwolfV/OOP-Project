package nl.tudelft.oopp.demo.controllers;

import com.jfoenix.controls.JFXHamburger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HamburgerMenuSceneController implements Initializable {

    private MainSceneController mainSceneController;
    private HeaderSceneController headerSceneController;
    private RestaurantSceneController restaurantSceneController;
    private AdminSceneController adminSceneController;

    private Parent reservationRoot;
    private Parent restaurantRoot;
    private Parent sidebarFilterRoot;
    private Parent sidebarRoot;
    private Parent adminPanelRoot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader reservationLoader = new FXMLLoader(getClass().getResource("/Scenes/reservationScene.fxml"));
        FXMLLoader sidebarFilterLoader = new FXMLLoader(getClass().getResource("/Scenes/sidebarFilterScene.fxml"));
        FXMLLoader sidebarLoader = new FXMLLoader(getClass().getResource("/Scenes/sidebarScene.fxml"));
        FXMLLoader restaurantLoader = new FXMLLoader(getClass().getResource("/Scenes/restaurantScene.fxml"));
        FXMLLoader adminPanelLoader = new FXMLLoader(getClass().getResource("/Scenes/adminScene.fxml"));
        try {
            reservationRoot = reservationLoader.load();
            sidebarFilterRoot = sidebarFilterLoader.load();
            sidebarRoot = sidebarLoader.load();
            restaurantRoot = restaurantLoader.load();
            adminPanelRoot = adminPanelLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        restaurantSceneController = restaurantLoader.getController();
        adminSceneController = adminPanelLoader.getController();
    }

    public void setController(MainSceneController mainSceneController, HeaderSceneController headerSceneController) {
        this.mainSceneController = mainSceneController;
        this.headerSceneController = headerSceneController;

    }

    public void openReservations(MouseEvent event) {
        mainSceneController.changeCenter(reservationRoot);
        mainSceneController.sidebar = (sidebarFilterRoot);
        headerSceneController.changeLeft();
    }

    public void openCalendar(MouseEvent event) {
        mainSceneController.changeCenter(mainSceneController.calendarRoot);
        mainSceneController.sidebar = (sidebarRoot);
        headerSceneController.changeLeft();
    }

    public void openRestaurants(MouseEvent event) {
        mainSceneController.changeCenter(restaurantRoot);
        mainSceneController.sidebar = (sidebarFilterRoot);
        headerSceneController.changeLeft();
    }

    public void openAdminPanel(MouseEvent event) {
        adminSceneController.setControllers(mainSceneController);
        mainSceneController.changeCenter(adminPanelRoot);
        mainSceneController.sidebar = (sidebarRoot);
        headerSceneController.changeLeft();
    }
}
