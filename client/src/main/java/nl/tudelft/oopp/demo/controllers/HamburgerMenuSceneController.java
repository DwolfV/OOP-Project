package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.demo.communication.Authenticator;

public class HamburgerMenuSceneController implements Initializable {

    private MainSceneController mainSceneController;
    private HeaderSceneController headerSceneController;
    private RestaurantSceneController restaurantSceneController;
    private AdminSceneController adminSceneController;

    private Parent reservationRoot;
    private Parent restaurantRoot;
    private Parent sidebarFilterRoot;
    private Parent sidebarRoot;
    private Parent emptySidebarRoot;
    private Parent adminPanelRoot;

    @FXML public Button adminButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader reservationLoader = new FXMLLoader(getClass().getResource("/Scenes/reservationScene.fxml"));
        FXMLLoader sidebarFilterLoader = new FXMLLoader(getClass().getResource("/Scenes/sidebarFilterScene.fxml"));
        FXMLLoader sidebarLoader = new FXMLLoader(getClass().getResource("/Scenes/sidebarScene.fxml"));
        FXMLLoader emptySidebarLoader = new FXMLLoader(getClass().getResource("/Scenes/emptySidebarScene.fxml"));
        FXMLLoader restaurantLoader = new FXMLLoader(getClass().getResource("/Scenes/restaurantScene.fxml"));
        FXMLLoader adminPanelLoader = new FXMLLoader(getClass().getResource("/Scenes/adminScene.fxml"));
        try {
            reservationRoot = reservationLoader.load();
            sidebarFilterRoot = sidebarFilterLoader.load();
            sidebarRoot = sidebarLoader.load();
            emptySidebarRoot = emptySidebarLoader.load();
            restaurantRoot = restaurantLoader.load();
            adminPanelRoot = adminPanelLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        restaurantSceneController = restaurantLoader.getController();
        adminSceneController = adminPanelLoader.getController();
        adminSceneController.setControllers(mainSceneController);
        if (!Authenticator.isAdmin()) {
            adminButton.setVisible(false);
        }
    }

    /**
     * Set controllers for the class.
     * @param mainSceneController Main Scene
     * @param headerSceneController Header Scene
     */
    public void setController(MainSceneController mainSceneController, HeaderSceneController headerSceneController) {
        this.mainSceneController = mainSceneController;
        this.headerSceneController = headerSceneController;

    }

    /**
     * Open reservations page.
     * @param event mouse click
     */
    public void openReservations(MouseEvent event) {
        mainSceneController.changeCenter(reservationRoot);
        mainSceneController.sidebar = (sidebarFilterRoot);
        headerSceneController.changeLeft();
    }

    /**
     * Open calendar page.
     * @param event mouse click
     */
    public void openCalendar(MouseEvent event) {
        mainSceneController.changeCenter(mainSceneController.calendarRoot);
        mainSceneController.sidebar = (sidebarRoot);
        headerSceneController.changeLeft();
    }

    /**
     * Open restaurant page.
     * @param event mouse click
     */
    public void openRestaurants(MouseEvent event) {
        mainSceneController.changeCenter(restaurantRoot);
        mainSceneController.sidebar = (sidebarFilterRoot);
        headerSceneController.changeLeft();
    }

    /**
     * Open admin page.
     * @param event mouse click
     */
    public void openAdminPanel(MouseEvent event) {
        mainSceneController.changeCenter(adminPanelRoot);
        mainSceneController.sidebar = (emptySidebarRoot);
        headerSceneController.changeLeft();
    }
}
