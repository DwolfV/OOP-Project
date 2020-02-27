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
    public void handleCloseButtonAction(MouseEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
//        Platform.exit();
//        System.exit(0);
    }

    // handles for now both home and login buttons
    @FXML
    public void handleHomeButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/calendarScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("Home");
            stage.show();
            MainDisplay.stg.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleReservationButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reservationsScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("Reservations");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRestaurantsButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/restaurantsScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("Restaurants");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleFriendsButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/friendsScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("Friends");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSettingsButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/settingsScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("Settings");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleAdminButton (ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adminScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
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

    @FXML
    public void handleBuildingButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/buildingFormScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("New Building");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRoomButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/roomFormScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("New Room");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Quote for you");
//        alert.setHeaderText(null);
//        alert.setContentText(ServerCommunication.getQuote());
//        alert.showAndWait();

}
