package nl.tudelft.oopp.demo.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.*;


import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Room;
import nl.tudelft.oopp.demo.views.MainDisplay;

import java.io.IOException;
import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    @FXML
    private Label closeButton;

    @FXML
    private Accordion ac = new Accordion();

    @FXML
    private BorderPane bPane = new BorderPane();

    private ObservableList<Building> buildingData = FXCollections.observableArrayList();
    private ObservableList<Room> roomData = FXCollections.observableArrayList();

    @FXML
    public void handleCloseButtonAction(MouseEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleLoginButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/calendarScene.fxml"));
            Parent loginParent = (Parent) fxmlLoader.load();
            MainDisplay.secondaryStage = new Stage();

            MainDisplay.secondaryStage.setResizable(true);
            MainDisplay.secondaryStage.setScene(new Scene(loginParent));
            MainDisplay.secondaryStage.setTitle("Home");
            MainDisplay.secondaryStage.show();
            MainDisplay.primaryStage.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleHomeButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/calendarScene.fxml"));
            Parent calendarParent = (Parent) fxmlLoader.load();

            MainDisplay.secondaryStage.setResizable(true);
            MainDisplay.secondaryStage.setScene(new Scene(calendarParent));
            MainDisplay.secondaryStage.setTitle("Home");
            MainDisplay.secondaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleReservationButton(ActionEvent event) throws Exception {
        try {
            buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());
            roomData = FXCollections.observableList(RoomCommunication.getRooms());
            TitledPane[] tps = new TitledPane[buildingData.size()];

            // load the scene
            BorderPane rootScene = FXMLLoader.load(getClass().getResource("/reservationsScene.fxml"));

            // fill the accordion
            for(int i = 0; i < tps.length; i++){
                tps[i] = new TitledPane();
                GridPane grid = new GridPane();
                ColumnConstraints colConst = new ColumnConstraints();
                colConst.setPercentWidth(100/2);
                grid.getColumnConstraints().add(colConst);
                grid.setVgap(4);
                grid.setPadding(new Insets(5, 5, 5, 5));

                ObservableList<Room> rooms = FXCollections.observableList(RoomCommunication.getRoomsByBuildingId(12));
                for(int j = 0; j < rooms.size(); j++){
                    grid.add(new Label(rooms.get(j).getName()), 0, j);
                    grid.add(new Button("Reserve"), 1, j);
                }

                tps[i].setText(buildingData.get(i).getName());
                tps[i].setContent(grid);
                ac.getPanes().add(tps[i]);
            }

            // load the accordion into the scene
            VBox vBox = new VBox(ac);
            bPane.setCenter(vBox);
            bPane.setPadding(new Insets(30, 5, 5, 10));
            rootScene.setCenter(bPane);

            // show the scene
            Stage reservationsStage = new Stage();
            Scene scene = new Scene(rootScene);
            reservationsStage.setScene(scene);
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

            MainDisplay.secondaryStage.setScene(new Scene(restaurantsParent));
            MainDisplay.secondaryStage.setTitle("Restaurants");
            MainDisplay.secondaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleFriendsButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/friendsScene.fxml"));
            Parent friendsParent = (Parent) fxmlLoader.load();

            MainDisplay.secondaryStage.setScene(new Scene(friendsParent));
            MainDisplay.secondaryStage.setTitle("Friends");
            MainDisplay.secondaryStage.show();
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

            MainDisplay.secondaryStage.setScene(new Scene(settingsParent));
            MainDisplay.secondaryStage.setTitle("Settings");
            MainDisplay.secondaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAdminButton (ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adminScene.fxml"));
            Parent adminParent = (Parent) fxmlLoader.load();
            MainDisplay.adminStage = new Stage();

            MainDisplay.adminStage.isResizable();
            MainDisplay.adminStage.setScene(new Scene(adminParent));
            MainDisplay.adminStage.setTitle("Admin");
            MainDisplay.adminStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSignUpClick(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/registerScene.fxml"));
            Parent registerParent = (Parent) fxmlLoader.load();
            MainDisplay.registerStage = new Stage();

            MainDisplay.registerStage.setResizable(true);
            MainDisplay.registerStage.setScene(new Scene(registerParent));
            MainDisplay.registerStage.setTitle("Register");
            MainDisplay.registerStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
