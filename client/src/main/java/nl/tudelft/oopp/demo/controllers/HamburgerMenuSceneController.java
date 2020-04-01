package nl.tudelft.oopp.demo.controllers;

import java.awt.*;
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
    private ReservationSceneController reservationSceneController;
    private AdminSceneController adminSceneController;
    private MenuSceneController menuSceneController;

    private Parent reservationRoot;
    private Parent restaurantRoot;
    private Parent sidebarFilterRoot;
    private Parent sidebarRoot;
    private Parent emptySidebarRoot;
    private Parent adminPanelRoot;
    private Parent menuRoot;
//
//
//    private Parent menuP;

    public FXMLLoader sidebarFilterLoader;

    @FXML public Button adminButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader reservationLoader = new FXMLLoader(getClass().getResource("/Scenes/reservationScene.fxml"));
        sidebarFilterLoader = new FXMLLoader(getClass().getResource("/Scenes/sidebarFilterScene.fxml"));
        FXMLLoader sidebarLoader = new FXMLLoader(getClass().getResource("/Scenes/sidebarScene.fxml"));
        FXMLLoader emptySidebarLoader = new FXMLLoader(getClass().getResource("/Scenes/emptySidebarScene.fxml"));
        FXMLLoader restaurantLoader = new FXMLLoader(getClass().getResource("/Scenes/restaurantScene.fxml"));
        FXMLLoader adminPanelLoader = new FXMLLoader(getClass().getResource("/Scenes/adminScene.fxml"));
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/Scenes/menuScene.fxml"));

        try {
            reservationRoot = reservationLoader.load();
            sidebarFilterRoot = sidebarFilterLoader.load();
            sidebarRoot = sidebarLoader.load();
            emptySidebarRoot = emptySidebarLoader.load();
            restaurantRoot = restaurantLoader.load();
            adminPanelRoot = adminPanelLoader.load();
            menuRoot = menuLoader.load();

//            double width = mainSceneController.getCenter().getScene().getWidth();
//            double heigh = mainSceneController.getCenter().getScene().getHeight();
//            menuP = new Parent(menuRoot, width, heigh);

        } catch (IOException e) {
            e.printStackTrace();
        }

        reservationSceneController = reservationLoader.getController();
        restaurantSceneController = restaurantLoader.getController();
        adminSceneController = adminPanelLoader.getController();
        restaurantSceneController.setController(mainSceneController, this);
        menuSceneController = menuLoader.getController();
        menuSceneController.setController(mainSceneController);
        reservationSceneController.setControllers(this);
        reservationSceneController.init();
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
        CalendarSceneController calendarSceneController = mainSceneController.calendarLoader.getController();
        calendarSceneController.init();
    }

    /**
     * Open restaurant page.
     * @param event mouse click
     */
    public void openRestaurants(MouseEvent event) {
        mainSceneController.changeCenter(restaurantRoot);
        mainSceneController.sidebar = (sidebarRoot);
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

    /**
     * Open a menu for a restaurant page.
     */
    public void openMenu(){
        mainSceneController.changeCenter(menuRoot);
        mainSceneController.sidebar = (sidebarRoot);
        headerSceneController.changeLeft();
    }
}
