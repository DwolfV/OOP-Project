package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.TimeStringConverter;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.OpenTimeCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.helperclasses.*;

import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static nl.tudelft.oopp.demo.controllers.MainSceneController.*;

public class AdminSceneController implements Initializable {

    static final Button updateButtonBuilding = new Button("Update");
    static final Button deleteButtonBuilding = new Button("Delete");
    static final Button updateButtonRoom = new Button("Update");
    static final Button deleteButtonRoom = new Button("Delete");
    static final Button updateButtonRestaurant = new Button("Update");
    static final Button deleteButtonRestaurant = new Button("Delete");
    private static final TableView<Building> tableBuilding = new TableView<>();
    private static final TableView<Room> tableRoom = new TableView<>();
    private static final TableView<Restaurant> tableRestaurant = new TableView<>();

    /**
     * The method below is implemented for the update button under the building section in the admin scene.
     * When the user double clicks on a specific section of a row one will be able to change the details, and
     * after changing the details one will have to press on the update button to update the database.
     */
    public static void updateBuildingButtonClicked() {
        Building building = tableBuilding.getSelectionModel().getSelectedItem();
        BuildingCommunication.updateBuilding(building.getId(), building.getName(), building.getStreetName(), building.getStreetNumber(), building.getZipCode(), building.getCity());
    }

    /**
     * The method below is implemented for the delete button under the building section in the admin scene.
     * When the user selects a row in the building table it will be deleted from the database.
     */
    public static void deleteBuildingButtonClicked() {
        ObservableList<Building> allBuildings;
        allBuildings = tableBuilding.getItems();
        Building building = tableBuilding.getSelectionModel().getSelectedItem();

        allBuildings.remove(building);
        BuildingCommunication.removeBuilding(building.getId());
    }

    /**
     * The method below is implemented for the update button under the rooms section in the admin scene.
     * When the user double clicks on a specific section of a row one will be able to change the details, and
     * after changing the details one will have to press on the update button to update the database.
     */
    public static void updateRoomButtonClicked() {
        Room room = tableRoom.getSelectionModel().getSelectedItem();
        RoomCommunication.updateRoom(room.getId(), room.getName(), room.getCapacity(), room.getBuilding().getId());
    }

    /**
     * The method below is implemented for the delete button under the rooms section in the admin scene.
     * When the user selects a row in the rooms table it will be deleted from the database.
     */
    public static void deleteRoomButtonClicked() {
        ObservableList<Room> allRooms;
        allRooms = tableRoom.getItems();
        Room room = tableRoom.getSelectionModel().getSelectedItem();

        allRooms.remove(room);
        RoomCommunication.removeRoom(room.getId());
    }

    /**
     * The method below is implemented for the update button under the restaurant section in the admin scene.
     * When the user double clicks on a specific section of a row one will be able to change the details, and
     * after changing the details one will have to press on the update button to update the database.
     */
    public static void updateButtonRestaurantClicked() {
        Restaurant restaurant = tableRestaurant.getSelectionModel().getSelectedItem();
        RestaurantCommunication.updateRestaurant(restaurant.getId(), restaurant.getName(), restaurant.getBuilding(), restaurant.gettClose(), restaurant.gettOpen());
    }

    /**
     * The method below is implemented for the delete button under the restaurant section in the admin scene.
     * When the user selects a row in the rooms table it will be deleted from the database.
     */
    public static void deleteButtonRestaurantClicked() {
        ObservableList<Restaurant> allRestaurants;
        allRestaurants = tableRestaurant.getItems();
        Restaurant restaurant = tableRestaurant.getSelectionModel().getSelectedItem();

        allRestaurants.remove(restaurant);
        RestaurantCommunication.removeRestaurant(restaurant.getId());
    }

    /**
     * The method below is implemented for the building table which is loaded on the main accordion element in
     * the admin scene.
     */
    public static void BuildingView() {
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
                (TableColumn.CellEditEvent<Building, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setName(t.getNewValue()));

        TableColumn<Building, String> streetNameCol =
                new TableColumn<>("Street Name");
        streetNameCol.setMinWidth(100);
        streetNameCol.setCellValueFactory(
                new PropertyValueFactory<>("streetName"));
        streetNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        streetNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setStreetName(t.getNewValue()));

        TableColumn<Building, String> streetNumCol =
                new TableColumn<>("Street Number");
        streetNumCol.setMinWidth(100);
        streetNumCol.setCellValueFactory(
                new PropertyValueFactory<>("streetNumber"));
        streetNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        streetNumCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setStreetNumber(t.getNewValue()));

        TableColumn<Building, String> zipCodeCol =
                new TableColumn<>("Zip Code");
        zipCodeCol.setMinWidth(100);
        zipCodeCol.setCellValueFactory(
                new PropertyValueFactory<>("zipCode"));
        zipCodeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        zipCodeCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setZipCode(t.getNewValue()));

        TableColumn<Building, String> cityCol =
                new TableColumn<>("City");
        cityCol.setMinWidth(100);
        cityCol.setCellValueFactory(
                new PropertyValueFactory<>("City"));
        cityCol.setCellFactory(TextFieldTableCell.forTableColumn());
        cityCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setCity(t.getNewValue()));

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

        HBox hBoxAddDeleteUpdate = new HBox(10);
        hBoxAddDeleteUpdate.setPadding(new Insets(20, 20, 20, 0));
        hBoxAddDeleteUpdate.getChildren().setAll(deleteButtonBuilding, updateButtonBuilding);

        // adding a building
        BorderPane borderPaneAddBuilding = new BorderPane();
        VBox vBoxAddBuilding = new VBox();

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

        vBoxAddBuilding.getChildren().addAll(buildingName, buildingNameInput, streetName, streetNameInput, streetNumber, streetNumberInput, zipCode, zipCodeInput, city, cityInput, addButtonBuilding);
        vBoxAddBuilding.setPadding(new Insets(0, 10, 10, 10));
        vBoxAddBuilding.setSpacing(10);
        borderPaneAddBuilding.setTop(vBoxAddBuilding);

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
        });

        // adding a openTime for each building
        BorderPane borderPaneAddOpenTime = new BorderPane();
        VBox vBoxAddOpenTime = new VBox();

        ObservableList<Building> buildingNames = FXCollections.observableList(BuildingCommunication.getBuildings());
        ArrayList<String> buildingList = new ArrayList<>();

        for (int i = 0; i < buildingNames.size(); i++) {
            buildingList.add(buildingNames.get(i).getName() + ", " + buildingNames.get(i).getId());
        }
        ObservableList<String> bl = FXCollections.observableArrayList(buildingList);

        Text day = new Text("Day");
        Text openTime = new Text("Open Time");
        Text closeTime = new Text("Close Time");
        Text building = new Text("Building Name");

        TextField dayInput = new TextField();
        TextField openTimeInput = new TextField();
        TextField closeTimeInput = new TextField();
        TextField buildingInput = new TextField();

        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        Button addOpenTime = new Button("Add Open Time");

        choiceBox.setItems(bl);

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String[] string = newValue.split(", ");
            buildingInput.setText(string[1]);
        });

        vBoxAddOpenTime.getChildren().addAll(day, dayInput, openTime, openTimeInput, closeTime, closeTimeInput, building, buildingInput, choiceBox, addOpenTime);
        vBoxAddOpenTime.setPadding(new Insets(0, 10, 10, 10));
        vBoxAddOpenTime.setSpacing(10);
        borderPaneAddOpenTime.setTop(vBoxAddOpenTime);

        addOpenTime.setOnAction(e -> {
            System.out.println((openTimeInput.getText()));

            String day1 = dayInput.getText();
            Time openTime1 = Time.valueOf(openTimeInput.getText());
            Time closeTime1 = Time.valueOf(closeTimeInput.getText());

            OpenTimeCommunication.addOpenTime(day1, openTime1, closeTime1, Long.parseLong(buildingInput.getText()));

            dayInput.setText(null);
            openTimeInput.setText(null);
            closeTimeInput.setText(null);
            buildingInput.setText(null);

            choiceBox.setValue(null);
        });

        // This VBox contains the table for the buildings and adding a building
        VBox vBoxBuildingTP = new VBox();
        vBoxBuildingTP.setPadding(new Insets(20, 20, 20, 20));
        HBox hBoxBuildingTP = new HBox();
        hBoxBuildingTP.setSpacing(100);
        VBox vBoxBuildingAndButtons = new VBox();

        vBoxBuildingAndButtons.getChildren().addAll(tableBuilding, hBoxAddDeleteUpdate);
        hBoxBuildingTP.getChildren().addAll(vBoxBuildingAndButtons, borderPaneAddBuilding, borderPaneAddOpenTime);
        vBoxBuildingTP.getChildren().addAll(hBoxBuildingTP);
        buildingTP.setContent(vBoxBuildingTP);
    }

    /**
     * The method below is implemented for the rooms table which is loaded on the main accordion element in
     * the admin scene.
     */
    public static void RoomView() {
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
                (TableColumn.CellEditEvent<Room, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setName(t.getNewValue()));

        TableColumn<Room, Integer> capacityCol =
                new TableColumn<>("Capacity");
        capacityCol.setMinWidth(100);
        capacityCol.setCellValueFactory(
                new PropertyValueFactory<>("capacity"));
        capacityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        capacityCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Room, Integer> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setCapacity(t.getNewValue()));

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
        HBox hBoxAddDeleteUpdateRooms = new HBox(10);
        hBoxAddDeleteUpdateRooms.setPadding(new Insets(20, 20, 20, 0));
        hBoxAddDeleteUpdateRooms.getChildren().setAll(deleteButtonRoom, updateButtonRoom);

        // adding room scene
        ObservableList<Building> buildingNames = FXCollections.observableList(BuildingCommunication.getBuildings());
        ArrayList<String> buildingList = new ArrayList<>();

        for (int i = 0; i < buildingNames.size(); i++) {
            buildingList.add(buildingNames.get(i).getName() + ", " + buildingNames.get(i).getId());
        }
        ObservableList<String> bl = FXCollections.observableArrayList(buildingList);

        BorderPane borderPaneAddRoom = new BorderPane();
        VBox vBoxAddRoom = new VBox();

        Text roomName = new Text("Room Name");
        Text capacity = new Text("Capacity");
        Text building = new Text("Building Name");

        TextField RoomName = new TextField();
        TextField Capacity = new TextField();
        TextField Building = new TextField();

        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        Button addButton = new Button("Add Room");

        choiceBox.setItems(bl);

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String[] string = newValue.split(", ");
            Building.setText(string[1]);
        });

        vBoxAddRoom.getChildren().addAll(roomName, RoomName, capacity, Capacity, building, Building, choiceBox, addButton);
        vBoxAddRoom.setPadding(new Insets(0, 10, 10, 10));
        vBoxAddRoom.setSpacing(10);
        borderPaneAddRoom.setTop(vBoxAddRoom);

        addButton.setOnAction(e -> {
            String roomName1 = RoomName.getText();
            int capacity1 = Integer.parseInt(Capacity.getText());

            RoomCommunication.addRoom(roomName1, capacity1, Long.parseLong(Building.getText()));

            Building.setText(null);
            RoomName.setText(null);
            Capacity.setText(null);

            choiceBox.setValue(null);
        });

        // This VBox contains the table for the rooms and adding a room
        VBox vBoxRoomsTP = new VBox();
        HBox hBoxRoomTP = new HBox();
        hBoxRoomTP.setSpacing(100);
        hBoxRoomTP.getChildren().addAll(tableRoom, borderPaneAddRoom);
        vBoxRoomsTP.setPadding(new Insets(20, 20, 20, 20));
        vBoxRoomsTP.getChildren().addAll(hBoxRoomTP, hBoxAddDeleteUpdateRooms);
        roomsTP.setContent(vBoxRoomsTP);
    }

    /**
     * The method below is implemented for the restaurant table which is loaded on the main accordion element in
     * the admin scene.
     */
    public static void RestaurantView() {
        // Table for restaurants
        tableRestaurant.setEditable(true);

        TableColumn<Restaurant, Long> idRestaurantCol =
                new TableColumn<>("id");
        idRestaurantCol.setMinWidth(100);
        idRestaurantCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn<Restaurant, String> restaurantNameCol =
                new TableColumn<>("Building Name");
        restaurantNameCol.setMinWidth(100);
        restaurantNameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        restaurantNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        restaurantNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Restaurant, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setName(t.getNewValue()));

        TableColumn<Restaurant, Building> buildingNameRestaurantCol =
                new TableColumn<>("Building Name");
        buildingNameRestaurantCol.setMinWidth(100);
        buildingNameRestaurantCol.setCellValueFactory(
                new PropertyValueFactory<>("building"));
        buildingNameRestaurantCol.setCellFactory(TextFieldTableCell.<Restaurant, String>forTableColumn(new BuildingToStringConvertor()));
        buildingNameRestaurantCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Restaurant, Building> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setBuilding(t.getNewValue()));

        TableColumn<Restaurant, String> timeCloseCol =
                new TableColumn<>("Closing Time");
        timeCloseCol.setMinWidth(100);
        timeCloseCol.setCellValueFactory(
                new PropertyValueFactory<>("tClose"));
        timeCloseCol.setCellFactory(TextFieldTableCell.<Restaurant, String>forTableColumn((new TimeToStringConverter())));
//        timeCloseCol.setOnEditCommit(
//                (TableColumn.CellEditEvent<Restaurant, String> t) -> {
//                    t.getTableView().getItems().get(
//                            t.getTablePosition().getRow()).settClose(t.getNewValue());
//                });

        TableColumn<Restaurant, String> timeOpenCol =
                new TableColumn<>("Opening Time");
        timeOpenCol.setMinWidth(100);
        timeOpenCol.setCellValueFactory(
                new PropertyValueFactory<>("tOpen"));
        timeOpenCol.setCellFactory(TextFieldTableCell.<Restaurant, String>forTableColumn((new TimeToStringConverter())));
//        timeOpenCol.setOnEditCommit(
//                (TableColumn.CellEditEvent<Restaurant, String> t) -> {
//                    t.getTableView().getItems().get(
//                            t.getTablePosition().getRow()).settOpen(t.getNewValue());
//                });

        ObservableList<Restaurant> restaurantData = FXCollections.observableList(RestaurantCommunication.getRestaurants());
        tableRestaurant.setItems(restaurantData);
        tableRestaurant.getColumns().addAll(idRestaurantCol, restaurantNameCol, buildingNameRestaurantCol, timeCloseCol, timeOpenCol);

        //delete button
        deleteButtonRestaurant.setOnAction(e -> {
            try {
                deleteButtonRestaurantClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //update button
        updateButtonRestaurant.setOnAction(e -> {
            try {
                updateButtonRestaurantClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // HBox for the buttons under the table
        HBox hBoxAddDeleteUpdateRestaurants = new HBox(10);
        hBoxAddDeleteUpdateRestaurants.setPadding(new Insets(20, 20, 20, 0));
        hBoxAddDeleteUpdateRestaurants.getChildren().setAll(deleteButtonRestaurant, updateButtonRestaurant);

        // This VBox contains the table for the rooms and adding a room
        VBox vBoxRestaurantTP = new VBox();
        HBox hBoxRestaurantTP = new HBox();
        hBoxRestaurantTP.setSpacing(100);
        hBoxRestaurantTP.getChildren().addAll(tableRestaurant);
        vBoxRestaurantTP.setPadding(new Insets(20, 20, 20, 20));
        vBoxRestaurantTP.getChildren().addAll(hBoxRestaurantTP, hBoxAddDeleteUpdateRestaurants);
        restaurantsTP.setContent(vBoxRestaurantTP);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
