package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.communication.RoomReservationCommunication;
import nl.tudelft.oopp.demo.communication.UserCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.BuildingToStringConvertor;
import nl.tudelft.oopp.demo.helperclasses.Restaurant;
import nl.tudelft.oopp.demo.helperclasses.Room;
import nl.tudelft.oopp.demo.views.MainDisplay;

public class MainSceneController implements Initializable {

    private static ArrayList<String> timeFrom = new ArrayList<>();
    private static ArrayList<String> timeTo = new ArrayList<>();
    private static BorderPane rootScene;
    final Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    final Button updateButtonBuilding = new Button("Update");
    final Button deleteButtonBuilding = new Button("Delete");
    final Button updateButtonRoom = new Button("Update");
    final Button deleteButtonRoom = new Button("Delete");
    final Button updateButtonRestaurant = new Button("Update");
    final Button deleteButtonRestaurant = new Button("Delete");
    @FXML
    private final Accordion ac = new Accordion();
    @FXML
    private final BorderPane borderPane = new BorderPane();
    private final TableView<Building> tableBuilding = new TableView<>();
    private final TableView<Room> tableRoom = new TableView<>();
    private final TableView<Restaurant> tableRestaurant = new TableView<>();
    @FXML
    private Label closeButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private DatePicker dp;
    @FXML
    private Button searchId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Closes the secondary stage when clicking the close button.
     */
    public void closeSecondaryStage() {
        MainDisplay.secondaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * Handles the click on the close button.
     * @param event The MouseEvent passed when clicking a button
     */
    @FXML
    public void handleCloseButtonAction(MouseEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles what happens when the client clicks on the Login button.
     * @param event The Action event that is passed when clicking a button.
     */
    @FXML
    public void handleLoginButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!UserCommunication.authenticate(username, password)) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the click on the Sign up button.
     * @param event The Action event passed when clicking a button.
     */
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles what happens when the client clicks on the Home button.
     * @param event The Action event that is passed when clicking a button.
     */
    @FXML
    public void handleHomeButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/calendarScene.fxml"));
            Parent calendarParent = fxmlLoader.load();

            MainDisplay.secondaryStage.setScene(new Scene(calendarParent, screenSize.getWidth(), screenSize.getHeight()));
            MainDisplay.secondaryStage.setTitle("Home");
            MainDisplay.secondaryStage.show();
            MainDisplay.secondaryStage.setMaximized(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeSecondaryStage();
    }

    /**
     * Handles what happens when the client picks a date.
     * @param event The Action event that is passed when clicking a button.
     */
    public void pickDate(ActionEvent event) {
        ObservableList<Room> rooms = FXCollections.observableList(RoomCommunication.getRooms());
        searchId.setOnAction(e -> {
            /*LocalDate date = dp.getValue();
            for(int i = 0; i < rooms.size(); i++) {
                String string1 = String.valueOf(RoomReservationCommunication.getAllRoomReservationTimesPerRoomAndDate(rooms.get(i).getId(), Date.valueOf(date.toString())));
                String replaced = string1.replace("{", "").replace("}", "");
                replaced.trim();
                if (!replaced.equals("")) {
                    String[] string2 = replaced.split(", ");
                    for (int k = 0; k < string2.length; k++) {
                        String[] string3 = string2[k].split("=");
                        timeFrom.add(string3[0]);
                        System.out.println(string3.length);
                        timeTo.add(string3[1]);
                    }
                }
            }*/

            ObservableList<Building> buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());

            TitledPane[] tps = new TitledPane[buildingData.size()];
            List<Button> buttons = new ArrayList<>();
            //List<Label> labels = new ArrayList<>();

            int c = 0; // count - for lists, c - for tps

            // fill the accordion
            for (int i = 0; i < buildingData.size(); i++) {

                //Look for rooms for the building i;
                ObservableList<Room> showRooms = FXCollections.observableArrayList();
                for (int k = 0; k < rooms.size(); k++) {
                    if (rooms.get(k).getBuilding().getName().equals(buildingData.get(i).getName())) {
                        LocalDate date = dp.getValue();
                        String string1 = String.valueOf(RoomReservationCommunication.getAllRoomReservationTimesPerRoomAndDate(rooms.get(k).getId(), Date.valueOf(date.toString())));
                        String replaced = string1.replace("{", "").replace("}", "");
                        replaced.trim();
                        if (!replaced.equals("")) {
                            showRooms.add(rooms.get(k));
                        }
                    }
                }


                //if there are rooms for the building i - show them;
                if (showRooms.size() != 0) {
                    VBox vbox = new VBox();
                    tps[c] = new TitledPane();

                    for (int j = 0; j < showRooms.size(); j++) {
                        HBox hbox = new HBox();

                        Label label1 = new Label(showRooms.get(j).getName());
                        label1.setStyle("-fx-font-weight: bold");
                        //label1.setPadding(new Insets(0,10,0,0));

                        Label label2 = new Label("Capacity: " + showRooms.get(j).getCapacity() + " persons");
                        Button button1 = new Button("Reserve");
                        //button1.setOnAction();
                        buttons.add(button1);


                        LocalDate date = dp.getValue();
                        String string1 = String.valueOf(RoomReservationCommunication.getAllRoomReservationTimesPerRoomAndDate(rooms.get(j).getId(), Date.valueOf(date.toString())));
                        String replaced = string1.replace("{", "").replace("}", "");
                        replaced.trim();
                        if (!replaced.equals("")) {
                            String[] string2 = replaced.split(", ");
                            for (int k = 0; k < string2.length; k++) {
                                String[] string3 = string2[k].split("=");
                                timeFrom.add(string3[0]);
                                System.out.println(string3.length);
                                timeTo.add(string3[1]);
                            }
                        }


                        ObservableList<String> from = FXCollections.observableArrayList(timeFrom);
                        ObservableList<String> to = FXCollections.observableArrayList(timeTo);

                        ChoiceBox<String> cb = new ChoiceBox<>();
                        cb.setItems(from);
                        ChoiceBox<String> cbb = new ChoiceBox<>();
                        cbb.setItems(to);


                        hbox.getChildren().addAll(label1, label2, cb, cbb, button1);
                        hbox.setSpacing(150);
                        hbox.setStyle("-fx-padding: 8;" + "-fx-border-style: solid inside;"
                            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                            + "-fx-border-radius: 5;" + "-fx-border-color: lightgrey;");
                        vbox.getChildren().add(hbox);
                    }
                    tps[c].setText(buildingData.get(i).getName());
                    tps[c].setContent(vbox);
                    ac.getPanes().add(tps[c]);
                    c++;
                }

            }

            // load the accordion into the scene
            VBox vbox = new VBox(ac);
            borderPane.setCenter(vbox);
            borderPane.setPadding(new Insets(30, 5, 5, 10));
            rootScene.setCenter(borderPane);

        });
    }

    /**
     * Handles what happens when the client clicks on the Reservations button.
     * @param event The Action event that is passed when clicking a button.
     */
    @FXML
    public void handleReservationButton(ActionEvent event) {
        try {
            // load the scene
            rootScene = FXMLLoader.load(getClass().getResource("/reservationsScene.fxml"));

            // show the scene
            MainDisplay.secondaryStage.setScene(new Scene(rootScene, screenSize.getWidth(), screenSize.getHeight()));
            MainDisplay.secondaryStage.setTitle("Reservations");
            MainDisplay.secondaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        closeSecondaryStage();
    }

    /**
     * Handles what happens when the client clicks on the Restaurants button.
     * @param event The Action event that is passed when clicking a button.
     */
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

            int count = 0; // for lists
            int c = 0; // for tps

            // load the scene
            BorderPane rootScene = FXMLLoader.load(getClass().getResource("/restaurantsScene.fxml"));

            // fill the accordion
            for (int i = 0; i < buildingData.size(); i++) {

                //Look for restaurants for the building i;
                ObservableList<Restaurant> showRestaurants = FXCollections.observableArrayList();
                for (int k = 0; k < restaurants.size(); k++) {
                    if (restaurants.get(k).getBuilding().getName().equals(buildingData.get(i).getName())) {
                        showRestaurants.add(restaurants.get(k));
                    }
                }

                //if there are restaurants for the building i - show them;
                if (showRestaurants.size() != 0) {
                    tps[c] = new TitledPane();
                    GridPane grid = new GridPane();
                    ColumnConstraints colConst = new ColumnConstraints();
                    colConst.setPercentWidth(100 / 2);
                    grid.getColumnConstraints().add(colConst);
                    grid.setVgap(4);
                    grid.setPadding(new Insets(5, 5, 5, 5));

                    for (Restaurant restaurant : restaurants) {
                        System.out.println(buildingData.get(i).getName() + " " + restaurant.getName());
                    }

                    for (int j = 0; j < showRestaurants.size(); j++) {
                        Label label1 = new Label(showRestaurants.get(j).getName());
                        labels.add(label1);
                        Button button1 = new Button("Menu");
                        buttons.add(button1);

                        grid.add(labels.get(count), 0, j);
                        grid.add(buttons.get(count), 1, j);
                        count = count + 1;
                    }
                    tps[c].setText(buildingData.get(i).getName());
                    tps[c].setContent(grid);
                    ac.getPanes().add(tps[c]);
                    c++;
                }

            }

            // load the accordion into the scene
            VBox vbox = new VBox(ac);
            borderPane.setCenter(vbox);
            borderPane.setPadding(new Insets(30, 5, 5, 10));
            rootScene.setCenter(borderPane);

            MainDisplay.secondaryStage.setScene(new Scene(rootScene, screenSize.getWidth(), screenSize.getHeight()));
            MainDisplay.secondaryStage.setTitle("Restaurants");
            MainDisplay.secondaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
        closeSecondaryStage();
    }

    /**
     * Handles what happens when the client clicks on the Friends button.
     * @param event The Action event that is passed when clicking a button.
     */
    @FXML
    public void handleFriendsButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/friendsScene.fxml"));
            Parent friendsParent = fxmlLoader.load();

            MainDisplay.secondaryStage.setScene(new Scene(friendsParent, screenSize.getWidth(), screenSize.getHeight()));
            MainDisplay.secondaryStage.setTitle("Friends");
            MainDisplay.secondaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeSecondaryStage();
    }

    /**
     * Handles what happens when the client clicks on the Settings button.
     * @param event The Action event that is passed when clicking a button.
     */
    @FXML
    public void handleSettingsButton(ActionEvent event) {
        try {
            URL location = getClass().getResource("/settingsScene.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            Parent settingsParent = fxmlLoader.load();

            MainDisplay.secondaryStage.setScene(new Scene(settingsParent, screenSize.getWidth(), screenSize.getHeight()));
            MainDisplay.secondaryStage.setTitle("Settings");
            MainDisplay.secondaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeSecondaryStage();
    }

    public void updateBuildingButtonClicked() {
        Building building = tableBuilding.getSelectionModel().getSelectedItem();
        BuildingCommunication.updateBuilding(building.getId(), building.getName(), building.getStreetName(), building.getStreetNumber(), building.getZipCode(), building.getCity());
    }

    /**
     * Handles what happens when the client clicks on the 'delete' button in the buildings menu.
     */
    public void deleteBuildingButtonClicked() {
        ObservableList<Building> allBuildings;
        allBuildings = tableBuilding.getItems();
        Building building = tableBuilding.getSelectionModel().getSelectedItem();

        allBuildings.remove(building);
        BuildingCommunication.removeBuilding(building.getId());
    }

    public void updateRoomButtonClicked() {
        Room room = tableRoom.getSelectionModel().getSelectedItem();
        RoomCommunication.updateRoom(room.getId(), room.getName(), room.getCapacity(), room.getBuilding().getId());
    }

    /**
     * Handles what happens when the client clicks on the 'delete' button in the rooms menu.
     */
    public void deleteRoomButtonClicked() {
        ObservableList<Room> allRooms;
        allRooms = tableRoom.getItems();
        Room room = tableRoom.getSelectionModel().getSelectedItem();

        allRooms.remove(room);
        RoomCommunication.removeRoom(room.getId());
    }

    public void updateButtonRestaurantClicked() {
        Restaurant restaurant = tableRestaurant.getSelectionModel().getSelectedItem();
        RestaurantCommunication.updateRestaurant(restaurant.getId(), restaurant.getName(), restaurant.getBuilding(), restaurant.getTimeClose(), restaurant.getTimeOpen());
    }

    /**
     * Handles what happens when the client clicks on the 'delete' button in the restaurants menu.
     */
    public void deleteButtonRestaurantClicked() {
        ObservableList<Restaurant> allRestaurants;
        allRestaurants = tableRestaurant.getItems();
        Restaurant restaurant = tableRestaurant.getSelectionModel().getSelectedItem();

        allRestaurants.remove(restaurant);
        RestaurantCommunication.removeRestaurant(restaurant.getId());
    }

    /**
     * Handles what happens when the user clicks on the admin button.
     * @param event The ActionEvent passed when clicking a button.
     * @throws IOException Can throw an exception if the user passes unexpected input.
     */
    public void handleAdminButton(ActionEvent event) throws IOException {
        // load main admin scene
        BorderPane rootScene = FXMLLoader.load(getClass().getResource("/adminScene.fxml"));

        TitledPane buildingTP = new TitledPane("Buildings", new Button("View"));
        TitledPane roomsTP = new TitledPane("Rooms", new Button("View"));
        TitledPane restaurantsTP = new TitledPane("Restaurants", new Button("View"));
        ac.getPanes().addAll(buildingTP, roomsTP, restaurantsTP);

        // Table for buildings
        tableBuilding.setEditable(true);

        TableColumn<Building, Long> idCol =
            new TableColumn<>("id");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(
            new PropertyValueFactory<>("id"));

        TableColumn<Building, String> buildingCol =
            new TableColumn<>("Building Name");
        buildingCol.setMinWidth(100);
        buildingCol.setCellValueFactory(
            new PropertyValueFactory<>("name"));
        buildingCol.setCellFactory(TextFieldTableCell.forTableColumn());
        buildingCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Building, String> t) -> {
                t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setName(t.getNewValue());
            });

        TableColumn<Building, String> streetNameCol =
            new TableColumn<>("Street Name");
        streetNameCol.setMinWidth(100);
        streetNameCol.setCellValueFactory(
            new PropertyValueFactory<>("streetName"));
        streetNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        streetNameCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Building, String> t) -> {
                t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setStreetName(t.getNewValue());
            });

        TableColumn<Building, String> streetNumCol =
            new TableColumn<>("Street Number");
        streetNumCol.setMinWidth(100);
        streetNumCol.setCellValueFactory(
            new PropertyValueFactory<>("streetNumber"));
        streetNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        streetNumCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Building, String> t) -> {
                t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setStreetNumber(t.getNewValue());
            });

        TableColumn<Building, String> zipCodeCol =
            new TableColumn<>("Zip Code");
        zipCodeCol.setMinWidth(100);
        zipCodeCol.setCellValueFactory(
            new PropertyValueFactory<>("zipCode"));
        zipCodeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        zipCodeCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Building, String> t) -> {
                t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setZipCode(t.getNewValue());
            });

        TableColumn<Building, String> cityCol =
            new TableColumn<>("City");
        cityCol.setMinWidth(100);
        cityCol.setCellValueFactory(
            new PropertyValueFactory<>("City"));
        cityCol.setCellFactory(TextFieldTableCell.forTableColumn());
        cityCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Building, String> t) -> {
                t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setCity(t.getNewValue());
            });

        ObservableList<Building> buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());
        tableBuilding.setItems(buildingData);
        tableBuilding.getColumns().addAll(idCol, buildingCol, streetNameCol, streetNumCol, zipCodeCol, cityCol);

        //delete button
        deleteButtonBuilding.setOnAction(e -> {
            try {
                deleteBuildingButtonClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //update button
        updateButtonBuilding.setOnAction(e -> {
            try {
                updateBuildingButtonClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox hboxAddDeleteUpdate = new HBox(10);
        hboxAddDeleteUpdate.setPadding(new Insets(20, 20, 20, 0));
        hboxAddDeleteUpdate.getChildren().setAll(deleteButtonBuilding, updateButtonBuilding);

        // adding a building
        BorderPane borderPaneAddBuidling = new BorderPane();
        VBox vboxAddBuilding = new VBox();

        Text buildingName = new Text("Building Name");
        Text streetName = new Text("Street Name");
        Text streetNumber = new Text("Street Number");
        Text zipCode = new Text("Zip Code");
        Text city = new Text("City");

        TextField buildingNameInput = new TextField();
        TextField streetNameInput = new TextField();
        TextField streetNumberInput = new TextField();
        TextField zipCodeInput = new TextField();
        TextField cityInput = new TextField();

        Button addButtonBuilding = new Button("Add Building");

        vboxAddBuilding.getChildren().addAll(buildingName, buildingNameInput, streetName, streetNameInput, streetNumber, streetNumberInput, zipCode, zipCodeInput, city, cityInput, addButtonBuilding);
        vboxAddBuilding.setPadding(new Insets(10, 10, 10, 10));
        vboxAddBuilding.setSpacing(10);
        borderPaneAddBuidling.setTop(vboxAddBuilding);

        addButtonBuilding.setOnAction(e -> {
            String buildingNameInput1 = buildingNameInput.getText();
            String streetNameInput1 = streetNameInput.getText();
            String streetNumberInput1 = streetNumberInput.getText();
            String zipCodeInput1 = zipCodeInput.getText();
            String cityInput1 = cityInput.getText();

            BuildingCommunication.addBuilding(buildingNameInput1, streetNameInput1, streetNumberInput1, zipCodeInput1, cityInput1);

            buildingNameInput.setText(null);
            streetNameInput.setText(null);
            streetNumberInput.setText(null);
            zipCodeInput.setText(null);
            cityInput.setText(null);

            tableBuilding.refresh();
        });

        // This VBox contains the table for the rooms and adding a room
        VBox vboxBuildingTP = new VBox();
        HBox hboxBuildingTP = new HBox();
        hboxBuildingTP.setSpacing(100);
        hboxBuildingTP.getChildren().addAll(tableBuilding, borderPaneAddBuidling);
        vboxBuildingTP.setPadding(new Insets(20, 20, 20, 20));
        vboxBuildingTP.getChildren().addAll(hboxBuildingTP, hboxAddDeleteUpdate);
        buildingTP.setContent(vboxBuildingTP);

        // Table for Rooms
        tableRoom.setEditable(true);

        TableColumn<Room, Long> idColRooms =
            new TableColumn<>("id");
        idColRooms.setMinWidth(100);
        idColRooms.setCellValueFactory(
            new PropertyValueFactory<>("id"));

        TableColumn<Room, String> roomCol =
            new TableColumn<>("Room Name");
        roomCol.setMinWidth(100);
        roomCol.setCellValueFactory(
            new PropertyValueFactory<>("name"));
        roomCol.setCellFactory(TextFieldTableCell.forTableColumn());
        roomCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Room, String> t) -> {
                t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setName(t.getNewValue());
            });

        TableColumn<Room, Integer> capacityCol =
            new TableColumn<>("Capacity");
        capacityCol.setMinWidth(100);
        capacityCol.setCellValueFactory(
            new PropertyValueFactory<>("capacity"));
        capacityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        capacityCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Room, Integer> t) -> {
                t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setCapacity(t.getNewValue());
            });

        TableColumn<Room, String> buildingNameCol =
            new TableColumn<>("Building Name");
        buildingNameCol.setMinWidth(100);
        buildingNameCol.setCellValueFactory(
            new PropertyValueFactory<>("building"));
        buildingNameCol.setCellFactory(TextFieldTableCell.<Room, String>forTableColumn(new BuildingToStringConvertor()));

        ObservableList<Room> roomData = FXCollections.observableList(RoomCommunication.getRooms());
        tableRoom.setItems(roomData);
        tableRoom.getColumns().addAll(idColRooms, roomCol, capacityCol, buildingNameCol);

        //delete button
        deleteButtonRoom.setOnAction(e -> {
            try {
                deleteRoomButtonClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //update button
        updateButtonRoom.setOnAction(e -> {
            try {
                updateRoomButtonClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // HBox for the buttons under the table
        HBox hboxAddDeleteUpdateRooms = new HBox(10);
        hboxAddDeleteUpdateRooms.setPadding(new Insets(20, 20, 20, 0));
        hboxAddDeleteUpdateRooms.getChildren().setAll(deleteButtonRoom, updateButtonRoom);

        // adding room scene
        ObservableList<Building> buildingNames = FXCollections.observableList(BuildingCommunication.getBuildings());
        ArrayList<String> buildingList = new ArrayList<String>();

        for (int i = 0; i < buildingNames.size(); i++) {
            buildingList.add(buildingNames.get(i).getName() + ", " + buildingNames.get(i).getId());
        }
        ObservableList<String> bl = FXCollections.observableArrayList(buildingList);

        BorderPane borderPaneAddRoom = new BorderPane();
        VBox vboxAddRoom = new VBox();

        Text roomName = new Text("Room Name");
        Text capacity = new Text("Capacity");
        Text building = new Text("Building Name");

        TextField roomNameField = new TextField();
        TextField capacityField = new TextField();
        TextField buildingField = new TextField();
        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        Button addButton = new Button("Add Room");

        choiceBox.setItems(bl);

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String[] string = newValue.split(", ");
            buildingField.setText(string[1]);
        });

        vboxAddRoom.getChildren().addAll(roomName, roomNameField, capacity, capacityField, building, buildingField, choiceBox, addButton);
        vboxAddRoom.setPadding(new Insets(10, 10, 10, 10));
        vboxAddRoom.setSpacing(10);
        borderPaneAddRoom.setTop(vboxAddRoom);

        addButton.setOnAction(e -> {
            String roomName1 = roomNameField.getText();
            Integer capacity1 = Integer.parseInt(capacityField.getText());

            RoomCommunication.addRoom(roomName1, capacity1, Long.parseLong(buildingField.getText()));

            buildingField.setText(null);
            roomNameField.setText(null);
            capacityField.setText(null);
        });

        // This VBox contains the table for the rooms and adding a room
        VBox vboxRoomsTP = new VBox();
        HBox hboxRoomTP = new HBox();
        hboxRoomTP.setSpacing(100);
        hboxRoomTP.getChildren().addAll(tableRoom, borderPaneAddRoom);
        vboxRoomsTP.setPadding(new Insets(20, 20, 20, 20));
        vboxRoomsTP.getChildren().addAll(hboxRoomTP, hboxAddDeleteUpdateRooms);
        roomsTP.setContent(vboxRoomsTP);

        //        // Table for restaurants
        //        tableRestaurant.setEditable(true);
        //
        //        TableColumn<Restaurant, Long> idRestaurantCol =
        //                new TableColumn<>("id");
        //        idRestaurantCol.setMinWidth(100);
        //        idRestaurantCol.setCellValueFactory(
        //                new PropertyValueFactory<>("id"));
        //
        //        TableColumn<Restaurant, String> restaurantNameCol =
        //                new TableColumn<>("Building Name");
        //        restaurantNameCol.setMinWidth(100);
        //        restaurantNameCol.setCellValueFactory(
        //                new PropertyValueFactory<>("name"));
        //        restaurantNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //        restaurantNameCol.setOnEditCommit(
        //                (TableColumn.CellEditEvent<Restaurant, String> t) -> {
        //                    t.getTableView().getItems().get(
        //                            t.getTablePosition().getRow()).setName(t.getNewValue());
        //                });
        //
        //        TableColumn<Restaurant, Building> buildingNameRestaurantCol =
        //                new TableColumn<>("Building Name");
        //        buildingNameRestaurantCol.setMinWidth(100);
        //        buildingNameRestaurantCol.setCellValueFactory(
        //                new PropertyValueFactory<>("building"));
        //        buildingNameRestaurantCol.setCellFactory(TextFieldTableCell.<Restaurant, String>forTableColumn(new BuildingToStringConvertor()));
        //        buildingNameRestaurantCol.setOnEditCommit(
        //                (TableColumn.CellEditEvent<Restaurant, Building> t) -> {
        //                    t.getTableView().getItems().get(
        //                            t.getTablePosition().getRow()).setBuilding(t.getNewValue());
        //                });
        //
        //        TableColumn<Restaurant, String> timeCloseCol =
        //                new TableColumn<>("Closing Time");
        //        timeCloseCol.setMinWidth(100);
        //        timeCloseCol.setCellValueFactory(
        //                new PropertyValueFactory<>("tClose"));
        //        timeCloseCol.setCellFactory(TextFieldTableCell.<Restaurant, String>forTableColumn((new TimeToStringConvertor())));
        ////        timeCloseCol.setOnEditCommit(
        ////                (TableColumn.CellEditEvent<Restaurant, String> t) -> {
        ////                    t.getTableView().getItems().get(
        ////                            t.getTablePosition().getRow()).settClose(t.getNewValue());
        ////                });
        //
        //        TableColumn<Restaurant, String> timeOpenCol =
        //                new TableColumn<>("Opening Time");
        //        timeOpenCol.setMinWidth(100);
        //        timeOpenCol.setCellValueFactory(
        //                new PropertyValueFactory<>("tOpen"));
        //        timeOpenCol.setCellFactory(TextFieldTableCell.<Restaurant, String>forTableColumn((new TimeToStringConvertor())));
        ////        timeOpenCol.setOnEditCommit(
        ////                (TableColumn.CellEditEvent<Restaurant, String> t) -> {
        ////                    t.getTableView().getItems().get(
        ////                            t.getTablePosition().getRow()).settOpen(t.getNewValue());
        ////                });
        //
        //        ObservableList<Restaurant> restaurantData = FXCollections.observableList(RestaurantCommunication.getRestaurants());
        //        tableRestaurant.setItems(restaurantData);
        //        tableRestaurant.getColumns().addAll(idRestaurantCol, restaurantNameCol, buildingNameRestaurantCol, timeCloseCol, timeOpenCol);
        //
        //        //delete button
        //        deleteButtonRestaurant.setOnAction(e -> {
        //            try {
        //                deleteButtonRestaurantClicked();
        //            } catch (Exception ex) {
        //                ex.printStackTrace();
        //            }
        //        });
        //
        //        //update button
        //        updateButtonRestaurant.setOnAction(e -> {
        //            try {
        //                updateButtonRestaurantClicked();
        //            } catch (Exception ex) {
        //                ex.printStackTrace();
        //            }
        //        });
        //
        //        // HBox for the buttons under the table
        //        HBox hBoxAddDeleteUpdateRestaurants = new HBox(10);
        //        hBoxAddDeleteUpdateRestaurants.setPadding(new Insets(20, 20, 20, 0));
        //        hBoxAddDeleteUpdateRestaurants.getChildren().setAll(deleteButtonRestaurant, updateButtonRestaurant);
        //
        //        // This VBox contains the table for the rooms and adding a room
        //        VBox vBoxRestaurantTP = new VBox();
        //        HBox hBoxRestaurantTP = new HBox();
        //        hBoxRestaurantTP.setSpacing(100);
        //        hBoxRestaurantTP.getChildren().addAll(tableRestaurant);
        //        vBoxRestaurantTP.setPadding(new Insets(20, 20, 20, 20));
        //        vBoxRestaurantTP.getChildren().addAll(hBoxRestaurantTP, hBoxAddDeleteUpdateRestaurants);
        //        restaurantsTP.setContent(vBoxRestaurantTP);

        // load everything
        VBox vbox = new VBox(ac);
        borderPane.setCenter(vbox);
        borderPane.setPadding(new Insets(10, 50, 10, 50));
        rootScene.setCenter(borderPane);

        // show the scene
        MainDisplay.secondaryStage.setScene(new Scene(rootScene, screenSize.getWidth(), screenSize.getHeight()));
        MainDisplay.secondaryStage.setTitle("Admin");
        MainDisplay.secondaryStage.show();
        closeSecondaryStage();
    }

}
