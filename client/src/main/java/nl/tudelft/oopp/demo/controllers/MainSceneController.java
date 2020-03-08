package nl.tudelft.oopp.demo.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.*;


import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.communication.UserCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Room;
import nl.tudelft.oopp.demo.views.MainDisplay;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    final Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

    @FXML
    private Label closeButton;

    @FXML
    private final Accordion ac = new Accordion();

    @FXML
    private final BorderPane bPane = new BorderPane();

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void closeSecondaryStage(){
        MainDisplay.secondaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    @FXML
    public void handleCloseButtonAction(MouseEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleLoginButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(!UserCommunication.authenticate(username, password)) {
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/calendarScene.fxml"));
            Parent loginParent = fxmlLoader.load();
            MainDisplay.secondaryStage = new Stage();

            MainDisplay.secondaryStage.setScene(new Scene(loginParent, screenSize.getWidth(), screenSize.getHeight()));
            MainDisplay.secondaryStage.setTitle("Home");
            MainDisplay.secondaryStage.show();
            MainDisplay.primaryStage.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSignUpClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/registerScene.fxml"));
            Parent registerParent = fxmlLoader.load();
            MainDisplay.registerStage = new Stage();

            MainDisplay.registerStage.setResizable(true);
            MainDisplay.registerStage.setScene(new Scene(registerParent));
            MainDisplay.registerStage.setTitle("Register");
            MainDisplay.registerStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleHomeButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/calendarScene.fxml"));
            Parent calendarParent = fxmlLoader.load();

            MainDisplay.secondaryStage.setScene(new Scene(calendarParent, screenSize.getWidth(), screenSize.getHeight()));
            MainDisplay.secondaryStage.setTitle("Home");
            MainDisplay.secondaryStage.show();
            MainDisplay.secondaryStage.setMaximized(true);
        } catch(Exception e) {
            e.printStackTrace();
        }
        closeSecondaryStage();
    }

    @FXML
    public void handleReservationButton(ActionEvent event) {
        try {
            ObservableList<Building> buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());
            ObservableList<Room> rooms = FXCollections.observableList(RoomCommunication.getRooms());
            TitledPane[] tps = new TitledPane[buildingData.size()];
            List<Button> buttons = new ArrayList<>();
            List<Label> labels = new ArrayList<>();

            int count=0;

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


                long id = buildingData.get(i).getId();

                if(id!=0){
                    rooms = FXCollections.observableList(RoomCommunication.getRoomsByBuildingId(89));

                    for (Room room : rooms) {
                        System.out.println(buildingData.get(i).getName() + " " + room.getName());
                    }

                    for(int j = 0; j < rooms.size(); j++){
                        Label label1 = new Label(rooms.get(j).getName());
                        labels.add(label1);
                        Button button1 = new Button("Reserve");
                        buttons.add(button1);

                        grid.add(labels.get(count), 0, j);
                        grid.add(buttons.get(count), 1, j);
                        count = count +1;

                        /*grid.add(new Label(rooms.get(j).getName()), 0, j);
                        grid.add(new Button("Reserve"), 1, j);*/
                    }

                    tps[i].setText(buildingData.get(i).getName());
                    tps[i].setContent(grid);
                    ac.getPanes().add(tps[i]);
                }

            }

            // load the accordion into the scene
            VBox vBox = new VBox(ac);
            bPane.setCenter(vBox);
            bPane.setPadding(new Insets(30, 5, 5, 10));
            rootScene.setCenter(bPane);

            // show the scene
            MainDisplay.secondaryStage.setScene(new Scene(rootScene, screenSize.getWidth(), screenSize.getHeight()));
            MainDisplay.secondaryStage.setTitle("Reservations");
            MainDisplay.secondaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
        closeSecondaryStage();
    }

    @FXML
    public void handleRestaurantsButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/restaurantsScene.fxml"));
            Parent restaurantsParent = fxmlLoader.load();

            MainDisplay.secondaryStage.setScene(new Scene(restaurantsParent, screenSize.getWidth(), screenSize.getHeight()));
            MainDisplay.secondaryStage.setTitle("Restaurants");
            MainDisplay.secondaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
        closeSecondaryStage();
    }

    @FXML
    public void handleFriendsButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/friendsScene.fxml"));
            Parent friendsParent = fxmlLoader.load();

            MainDisplay.secondaryStage.setScene(new Scene(friendsParent, screenSize.getWidth(), screenSize.getHeight()));
            MainDisplay.secondaryStage.setTitle("Friends");
            MainDisplay.secondaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
        closeSecondaryStage();
    }

    @FXML
    public void handleSettingsButton(ActionEvent event) {
        try {
            URL location = getClass().getResource("/settingsScene.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            Parent settingsParent = fxmlLoader.load();

            MainDisplay.secondaryStage.setScene(new Scene(settingsParent, screenSize.getWidth(), screenSize.getHeight()));
            MainDisplay.secondaryStage.setTitle("Settings");
            MainDisplay.secondaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
        closeSecondaryStage();
    }

    @FXML
    public void handleAdminButton (ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adminScene.fxml"));
            Parent adminParent = fxmlLoader.load();
            MainDisplay.adminStage = new Stage();

            MainDisplay.adminStage.setScene(new Scene(adminParent));
            MainDisplay.adminStage.setTitle("Admin");
            MainDisplay.adminStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
        closeSecondaryStage();
    }
}
