package nl.tudelft.oopp.demo.controllers;

//import com.jfoenix.controls.JFXHamburger;
//import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.*;


import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.views.MainDisplay;

import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

//    @FXML
//    private JFXHamburger menu;
//
//    @FXML
//    public void handleMenu(MouseEvent event) {
//        HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(menu);
//        transition.setRate(-1);
//        menu.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
//            transition.setRate(transition.getRate()*-1);
//            transition.play();
//        });
//    }

    @FXML
    private Label closeButton;

    @FXML
    public void handleCloseButtonAction(MouseEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
//        Platform.exit();
//        System.exit(0);
    }

//     handles for now both home and login buttons
    @FXML
    public void handleHomeButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/calendarScene.fxml"));
            Parent calendarParent = (Parent) fxmlLoader.load();
            Stage calendarStage = new Stage();

            calendarStage.setScene(new Scene(calendarParent));
            calendarStage.setTitle("Home");
            calendarStage.show();
            MainDisplay.stg.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleReservationButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reservationsScene.fxml"));
            Parent reservationParent = (Parent) fxmlLoader.load();
            Stage reservationsStage = new Stage();

            reservationsStage.setScene(new Scene(reservationParent));
            reservationsStage.setTitle("Reservations");
            reservationsStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRestaurantsButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/restaurantsScene.fxml"));
            Parent restaurantsParent = (Parent) fxmlLoader.load();
            Stage restaurantsStage = new Stage();

            restaurantsStage.setScene(new Scene(restaurantsParent));
            restaurantsStage.setTitle("Restaurants");
            restaurantsStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleFriendsButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/friendsScene.fxml"));
            Parent friendsParent = (Parent) fxmlLoader.load();
            Stage friendsStage = new Stage();

            friendsStage.setScene(new Scene(friendsParent));
            friendsStage.setTitle("Friends");
            friendsStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSettingsButton(ActionEvent event) throws Exception {
        try {
            URL location = getClass().getResource("/settingsScene.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            Parent settingsParent = (Parent) fxmlLoader.load();
            Stage settingsStage = new Stage();

            settingsStage.setScene(new Scene(settingsParent));
            settingsStage.setTitle("Settings");
            settingsStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Loading User Data");
    }

}
