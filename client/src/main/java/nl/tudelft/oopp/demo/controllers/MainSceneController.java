package nl.tudelft.oopp.demo.controllers;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
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

    @FXML
    private Label closeButton;
    @FXML
    private Pane Details_1, Details_2, Details_3, Details_4, Details_5, Details_6, Details_7, Details_8, Details_9;

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

            calendarStage.setResizable(true);
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


    @FXML
    public void handleAdminButton (ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adminScene.fxml"));
            Parent adminParent = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.isResizable();
            stage.setScene(new Scene(adminParent));
            stage.setTitle("Admin");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handlePopUp(MouseEvent event) throws Exception {
        try {
            Tooltip details1 = new Tooltip();
            Tooltip details2 = new Tooltip();
            Tooltip details3 = new Tooltip();

            details1.setText("Facilities:\n - chairs \n - tables \n - couch \n - table");
            details1.setStyle("-fx-font-size: 15");

            details2.setText("Facilities:\n - chairs \n - blackboard \n - table");
            details2.setStyle("-fx-font-size: 15");

            details3.setText("Facilities:\n - chairs \n - table");
            details3.setStyle("-fx-font-size: 15");

            Tooltip.install(Details_1, details1);
            Tooltip.install(Details_2, details2);
            Tooltip.install(Details_3, details3);
            Tooltip.install(Details_4, details1);
            Tooltip.install(Details_5, details2);
            Tooltip.install(Details_6, details3);
            Tooltip.install(Details_7, details1);
            Tooltip.install(Details_8, details2);
            Tooltip.install(Details_9, details3);

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
