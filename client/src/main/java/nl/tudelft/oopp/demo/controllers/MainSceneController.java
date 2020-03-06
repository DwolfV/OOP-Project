package nl.tudelft.oopp.demo.controllers;

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
import nl.tudelft.oopp.demo.communication.UserCommunication;
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
    private Pane Details_1, Details_2, Details_3, Details_4, Details_5, Details_6, Details_7, Details_8, Details_9;
    @FXML
    private Accordion ac = new Accordion();
    @FXML
    private BorderPane bPane = new BorderPane();
    private ObservableList<Building> buildingData = FXCollections.observableArrayList();
    private ObservableList<Room> roomData = FXCollections.observableArrayList();

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;


    @FXML
    public void handleCloseButtonAction(MouseEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
//        Platform.exit();
//        System.exit(0);
    }

//     handles for now both home and login buttons

    @FXML
    private static Stage secondaryStage;

    @FXML
    public void handleLoginButton(ActionEvent event) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(!UserCommunication.authenticate(username, password)) {
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/calendarScene.fxml"));
            Parent loginParent = (Parent) fxmlLoader.load();
            secondaryStage = new Stage();

            secondaryStage.setResizable(true);
            secondaryStage.setScene(new Scene(loginParent));
            secondaryStage.setTitle("Home");
            secondaryStage.show();
            MainDisplay.stg.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleHomeButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/calendarScene.fxml"));
            Parent calendarParent = (Parent) fxmlLoader.load();

            secondaryStage.setResizable(true);
            secondaryStage.setScene(new Scene(calendarParent));
            secondaryStage.setTitle("Home");
            secondaryStage.show();
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

                ObservableList<Room> rooms = FXCollections.observableList(RoomCommunication.getRoomsByBuildingId(1));
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

            secondaryStage.setScene(new Scene(restaurantsParent));
            secondaryStage.setTitle("Restaurants");
            secondaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleFriendsButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/friendsScene.fxml"));
            Parent friendsParent = (Parent) fxmlLoader.load();

            secondaryStage.setScene(new Scene(friendsParent));
            secondaryStage.setTitle("Friends");
            secondaryStage.show();
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

            secondaryStage.setScene(new Scene(settingsParent));
            secondaryStage.setTitle("Settings");
            secondaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleAdminButton (ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adminScene.fxml"));
            Parent adminParent = (Parent) fxmlLoader.load();
            Stage adminStage = new Stage();

            adminStage.isResizable();
            adminStage.setScene(new Scene(adminParent));
            adminStage.setTitle("Admin");
            adminStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /*@FXML
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

    }*/

    @FXML
    public static Stage registerStage;

    @FXML
    public void handleSignUpClick(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/registerScene.fxml"));
            Parent registerParent = (Parent) fxmlLoader.load();
            registerStage = new Stage();

            registerStage.setResizable(true);
            registerStage.setScene(new Scene(registerParent));
            registerStage.setTitle("Register");
            registerStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
