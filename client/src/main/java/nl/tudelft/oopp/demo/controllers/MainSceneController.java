package nl.tudelft.oopp.demo.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.communication.UserCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Restaurant;
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
            buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());
            rooms = FXCollections.observableList(RoomCommunication.getRooms());

            TitledPane[] tps = new TitledPane[buildingData.size()];
            List<Button> buttons = new ArrayList<>();
            //List<Label> labels = new ArrayList<>();

            int c=0; // count - for lists, c - for tps

            // load the scene
            BorderPane rootScene = FXMLLoader.load(getClass().getResource("/reservationsScene.fxml"));

            //Create time
            ArrayList<String> timeFrom = new ArrayList<>();
            ArrayList<String> timeTo = new ArrayList<>();

            for(int p = 9; p < 20; p++){
                timeFrom.add(p+".00");
                timeFrom.add(p+".30");
                if(p>9){
                    timeTo.add(p+".00");
                    timeTo.add(p+".30");
                }
            }

            // fill the accordion
            for(int i = 0; i < buildingData.size(); i++){

                //Look for rooms for the building i;
                ObservableList<Room> showRooms = FXCollections.observableArrayList();
                for(int k = 0; k < rooms.size(); k++){
                    if(rooms.get(k).getBuilding().getName().equals(buildingData.get(i).getName())){
                        showRooms.add(rooms.get(k));
                    }
                }


                ObservableList<String> from = FXCollections.observableArrayList(timeFrom);
                ObservableList<String> to = FXCollections.observableArrayList(timeTo);


                //if there are rooms for the building i - show them;
                if(showRooms.size()!=0){
                    VBox vBox = new VBox();
                    tps[c] = new TitledPane();

                    for(int j = 0; j < showRooms.size(); j++){
                        HBox hBox = new HBox();

                        Label label1 = new Label(showRooms.get(j).getName());
                        label1.setStyle("-fx-font-weight: bold");

                        Label label2 = new Label("Capacity: " + showRooms.get(j).getCapacity() + " persons");
                        Button button1 = new Button("Reserve");
                        //button1.setOnAction();
                        buttons.add(button1);

                        ChoiceBox<String> cb = new ChoiceBox<>();
                        cb.setItems(from);
                        ChoiceBox<String> cbb = new ChoiceBox<>();
                        cbb.setItems(to);


                        hBox.getChildren().addAll(label1,label2,cb,cbb,button1);
                        hBox.setPadding(new Insets(5,10,5,10));
                        hBox.setSpacing(150);
                        hBox.setStyle("-fx-padding: 8;" + "-fx-border-style: solid inside;"
                                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                                + "-fx-border-radius: 5;" + "-fx-border-color: lightgrey;");
                        vBox.getChildren().add(hBox);
                    }
                    tps[c].setText(buildingData.get(i).getName());
                    tps[c].setContent(vBox);
                    ac.getPanes().add(tps[c]);
                    c++;
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
            ObservableList<Building> buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());
            ObservableList<Restaurant> restaurants = FXCollections.observableList(RestaurantCommunication.getRestaurants());
            buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());
            restaurants = FXCollections.observableList(RestaurantCommunication.getRestaurants());

            TitledPane[] tps = new TitledPane[buildingData.size()];
            List<Button> buttons = new ArrayList<>();
            List<Label> labels = new ArrayList<>();

            int count=0, c=0; // count - for lists, c - for tps

            // load the scene
            BorderPane rootScene = FXMLLoader.load(getClass().getResource("/restaurantsScene.fxml"));

            // fill the accordion
            for(int i = 0; i < buildingData.size(); i++){

                //Look for restaurants for the building i;
                ObservableList<Restaurant> showRestaurants = FXCollections.observableArrayList();
                for(int k = 0; k < restaurants.size(); k++){
                    if(restaurants.get(k).getBuilding().getName().equals(buildingData.get(i).getName())){
                        showRestaurants.add(restaurants.get(k));
                    }
                }

                //if there are restaurants for the building i - show them;
                if(showRestaurants.size()!=0){
                    tps[c] = new TitledPane();
                    GridPane grid = new GridPane();
                    ColumnConstraints colConst = new ColumnConstraints();
                    colConst.setPercentWidth(100/2);
                    grid.getColumnConstraints().add(colConst);
                    grid.setVgap(4);
                    grid.setPadding(new Insets(5, 5, 5, 5));

                    for (Restaurant restaurant : restaurants) {
                        System.out.println(buildingData.get(i).getName() + " " + restaurant.getName());
                    }

                    for(int j = 0; j < showRestaurants.size(); j++){
                        Label label1 = new Label(showRestaurants.get(j).getName());
                        labels.add(label1);
                        Button button1 = new Button("Menu");
                        buttons.add(button1);

                        grid.add(labels.get(count), 0, j);
                        grid.add(buttons.get(count), 1, j);
                        count = count +1;
                    }
                    tps[c].setText(buildingData.get(i).getName());
                    tps[c].setContent(grid);
                    ac.getPanes().add(tps[c]);
                    c++;
                }

            }

            // load the accordion into the scene
            VBox vBox = new VBox(ac);
            bPane.setCenter(vBox);
            bPane.setPadding(new Insets(30, 5, 5, 10));
            rootScene.setCenter(bPane);

            MainDisplay.secondaryStage.setScene(new Scene(rootScene, screenSize.getWidth(), screenSize.getHeight()));
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
