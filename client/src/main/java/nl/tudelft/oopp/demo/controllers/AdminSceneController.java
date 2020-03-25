package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.controllers.MainSceneController.buildingTP;
import static nl.tudelft.oopp.demo.controllers.MainSceneController.restaurantsTP;
import static nl.tudelft.oopp.demo.controllers.MainSceneController.roomsTP;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.OccasionCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.helperclasses.*;

public class AdminSceneController implements Initializable {

    static final Button updateButtonBuilding = new Button("Update");
    static final Button deleteButtonBuilding = new Button("Delete");
    static final Button updateTimeBuilding = new Button("Update");
    static final Button deleteTimeBuilding = new Button("Delete");
    static final Button updateButtonRoom = new Button("Update");
    static final Button deleteButtonRoom = new Button("Delete");
    static final Button updateButtonRestaurant = new Button("Update");
    static final Button deleteButtonRestaurant = new Button("Delete");
    public static final TableView<Building> tableBuilding = new TableView<>();
    public static final TableView<Occasion> tableHoliday = new TableView<>();
    public static final TableView<Room> tableRoom = new TableView<>();
    public static final TableView<Restaurant> tableRestaurant = new TableView<>();

    /**
     * The method below is implemented for the update button under the building section in the admin scene.
     * When the user double clicks on a specific section of a row one will be able to change the details, and
     * after changing the details one will have to press on the update button to update the database.
     */
    public static void updateBuildingButtonClicked() {
        Building building = tableBuilding.getSelectionModel().getSelectedItem();
        BuildingCommunication.updateBuilding(building.getId(), building.getName(), building.getOpenTime(), building.getCloseTime(), building.getStreetName(), building.getStreetNumber(),
            building.getZipCode(), building.getCity());
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
     * The method below is implemented for the update button under the time section in the admin scene.
     * When the user double clicks on a specific section of a row one will be able to change the details, and
     * after changing the details one will have to press on the update button to update the database.
     */
    public static void updateTimeButtonClicked() {
        Occasion occasion = tableHoliday.getSelectionModel().getSelectedItem();
        OccasionCommunication.updateOccasion(occasion.getId(), occasion.getDate(), occasion.getOpenTime(), occasion.getCloseTime(), occasion.getBuilding().getId());
    }

    /**
     * The method below is implemented for the delete button under the time section in the admin scene.
     * When the user selects a row in the rooms table it will be deleted from the database.
     */
    public static void deleteTimeButtonClicked() {
        ObservableList<Occasion> allTimes;
        allTimes = tableHoliday.getItems();
        Occasion occasion = tableHoliday.getSelectionModel().getSelectedItem();

        allTimes.remove(occasion);
        OccasionCommunication.removeOccasion(occasion.getId());
    }

    /**
     * The method below is implemented for the update button under the restaurant section in the admin scene.
     * When the user double clicks on a specific section of a row one will be able to change the details, and
     * after changing the details one will have to press on the update button to update the database.
     */
    public static void updateButtonRestaurantClicked() {
        Restaurant restaurant = tableRestaurant.getSelectionModel().getSelectedItem();
        RestaurantCommunication.updateRestaurant(restaurant.getId(), restaurant.getName(), restaurant.getBuilding().getId(), restaurant.getTimeClose(), restaurant.getTimeOpen());
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
    public static void buildingView() {
        //clearing any previous tableView to avoid multiplication
        tableBuilding.getColumns().clear();

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
        buildingCol.setOnEditCommit((TableColumn.CellEditEvent<Building, String> t) ->
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setName(t.getNewValue()));

        TableColumn<Building, LocalTime> openTimeCol =
            new TableColumn<>("Open Time");
        openTimeCol.setMinWidth(100);
        openTimeCol.setCellValueFactory(
            new PropertyValueFactory<>("openTime"));
        openTimeCol.setCellFactory(TextFieldTableCell.forTableColumn(new TimeToStringConverter()));
        openTimeCol.setOnEditCommit((TableColumn.CellEditEvent<Building, LocalTime> t) ->
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setOpenTime(t.getNewValue()));

        TableColumn<Building, LocalTime> closeTimeCol =
            new TableColumn<>("Close Time");
        closeTimeCol.setMinWidth(100);
        closeTimeCol.setCellValueFactory(
            new PropertyValueFactory<>("closeTime"));
        closeTimeCol.setCellFactory((TextFieldTableCell.forTableColumn(new TimeToStringConverter())));
        closeTimeCol.setOnEditCommit((TableColumn.CellEditEvent<Building, LocalTime> t) ->
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setCloseTime(t.getNewValue()));

        TableColumn<Building, String> streetNameCol =
            new TableColumn<>("Street Name");
        streetNameCol.setMinWidth(100);
        streetNameCol.setCellValueFactory(
            new PropertyValueFactory<>("streetName"));
        streetNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        streetNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setStreetName(t.getNewValue()));
        streetNameCol.setOnEditCommit((TableColumn.CellEditEvent<Building, String> t) ->
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setStreetName(t.getNewValue()));

        TableColumn<Building, String> streetNumCol =
            new TableColumn<>("Street Number");
        streetNumCol.setMinWidth(100);
        streetNumCol.setCellValueFactory(
            new PropertyValueFactory<>("streetNumber"));
        streetNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        streetNumCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Building, String> t) -> t.getTableView().getItems().get(
                t.getTablePosition().getRow()).setStreetNumber(t.getNewValue()));
        streetNumCol.setOnEditCommit((TableColumn.CellEditEvent<Building, String> t) ->
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setStreetNumber(t.getNewValue()));

        TableColumn<Building, String> zipCodeCol =
            new TableColumn<>("Zip Code");
        zipCodeCol.setMinWidth(100);
        zipCodeCol.setCellValueFactory(
            new PropertyValueFactory<>("zipCode"));
        zipCodeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        zipCodeCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Building, String> t) -> t.getTableView().getItems().get(
                t.getTablePosition().getRow()).setZipCode(t.getNewValue()));
        zipCodeCol.setOnEditCommit((TableColumn.CellEditEvent<Building, String> t) ->
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setZipCode(t.getNewValue()));

        TableColumn<Building, String> cityCol =
            new TableColumn<>("City");
        cityCol.setMinWidth(100);
        cityCol.setCellValueFactory(
            new PropertyValueFactory<>("City"));
        cityCol.setCellFactory(TextFieldTableCell.forTableColumn());
        cityCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Building, String> t) -> t.getTableView().getItems().get(
                t.getTablePosition().getRow()).setCity(t.getNewValue()));
        cityCol.setOnEditCommit((TableColumn.CellEditEvent<Building, String> t) ->
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setCity(t.getNewValue()));

        ObservableList<Building> buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());
        tableBuilding.setItems(buildingData);
        tableBuilding.getColumns().addAll(idCol, buildingCol, openTimeCol, closeTimeCol, streetNameCol, streetNumCol, zipCodeCol, cityCol);

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

        HBox hBoxAddDeleteUpdateBuilding = new HBox(10);
        hBoxAddDeleteUpdateBuilding.setPadding(new Insets(20, 20, 20, 0));
        hBoxAddDeleteUpdateBuilding.getChildren().setAll(deleteButtonBuilding, updateButtonBuilding);

        // adding a building
        BorderPane borderPaneAddBuilding = new BorderPane();
        VBox vBoxAddBuilding = new VBox();

        Text buildingName = new Text("Building Name");
        Text openTime = new Text("Open Time");
        Text closeTime = new Text("Close Time");
        Text streetName = new Text("Street Name");
        Text streetNumber = new Text("Street Number");
        Text zipCode = new Text("Zip Code");
        Text city = new Text("City");

        TextField buildingNameInput = new TextField();
        TextField openTimeInput = new TextField();
        TextField closeTimeInput = new TextField();
        TextField streetNameInput = new TextField();
        TextField streetNumberInput = new TextField();
        TextField zipCodeInput = new TextField();
        TextField cityInput = new TextField();

        Button addButtonBuilding = new Button("Add Building");

        vBoxAddBuilding.getChildren().addAll(buildingName, buildingNameInput, openTime, openTimeInput,
                closeTime, closeTimeInput, streetName, streetNameInput, streetNumber, streetNumberInput,
                zipCode, zipCodeInput, city, cityInput, addButtonBuilding);
        vBoxAddBuilding.setPadding(new Insets(0, 10, 10, 10));
        vBoxAddBuilding.setSpacing(10);
        borderPaneAddBuilding.setTop(vBoxAddBuilding);

        addButtonBuilding.setOnAction(e -> {
            String buildingNameInputText = buildingNameInput.getText();
            String openTimeInputText = openTimeInput.getText();
            String closeTimeInputText = closeTimeInput.getText();
            String streetNameInputText = streetNameInput.getText();
            String streetNumberInputText = streetNumberInput.getText();
            String zipCodeInputText = zipCodeInput.getText();
            String cityInputText = cityInput.getText();

            BuildingCommunication.addBuilding(buildingNameInputText, LocalTime.parse(openTimeInputText), LocalTime.parse(closeTimeInputText),
                streetNameInputText, streetNumberInputText, zipCodeInputText, cityInputText);

            buildingNameInput.setText(null);
            openTimeInput.setText(null);
            closeTimeInput.setText(null);
            streetNameInput.setText(null);
            streetNumberInput.setText(null);
            zipCodeInput.setText(null);
            cityInput.setText(null);
        });

        //clearing any previous tableView to avoid multiplication
        tableHoliday.getColumns().clear();

        // Table for Holiday Time for buildings
        tableHoliday.setEditable(true);

        TableColumn<Occasion, Long> idBuildingTimeCol =
                new TableColumn<>("id");
        idBuildingTimeCol.setMinWidth(100);
        idBuildingTimeCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn<Occasion, String> buildingNameCol =
                new TableColumn<>("Building Name");
        buildingNameCol.setMinWidth(100);
        buildingNameCol.setCellValueFactory(
                new PropertyValueFactory<>("building"));
        buildingNameCol.setCellFactory(TextFieldTableCell.<Occasion, String>forTableColumn(new BuildingToStringConverter()));

        TableColumn<Occasion, LocalDate> dayCol =
                new TableColumn<>("Day");
        dayCol.setMinWidth(100);
        dayCol.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        dayCol.setCellFactory(TextFieldTableCell.forTableColumn(new DateToStringConverter()));
        dayCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Occasion, LocalDate> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setDate(t.getNewValue()));

        TableColumn<Occasion, LocalTime> openHolidayTimeCol =
                new TableColumn<>("Open Time");
        openHolidayTimeCol.setMinWidth(100);
        openHolidayTimeCol.setCellValueFactory(
                new PropertyValueFactory<>("openTime"));
        openHolidayTimeCol.setCellFactory(TextFieldTableCell.<Occasion, String>forTableColumn((new TimeToStringConverter())));
        openHolidayTimeCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Occasion, LocalTime> t) -> {
                t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setOpenTime(t.getNewValue());
            });

        TableColumn<Occasion, LocalTime> closeHolidayTimeCol =
                new TableColumn<>("Close Time");
        closeHolidayTimeCol.setMinWidth(100);
        closeHolidayTimeCol.setCellValueFactory(
                new PropertyValueFactory<>("closeTime"));
        closeHolidayTimeCol.setCellFactory(TextFieldTableCell.<Occasion, String>forTableColumn((new TimeToStringConverter())));
        closeHolidayTimeCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Occasion, LocalTime> t) -> {
                t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setCloseTime(t.getNewValue());
            });

        ObservableList<Occasion> buildingTimeData = FXCollections.observableList(OccasionCommunication.getOccasions());
        tableHoliday.setItems(buildingTimeData);
        tableHoliday.getColumns().addAll(idBuildingTimeCol, buildingNameCol, dayCol, openHolidayTimeCol, closeHolidayTimeCol);

        //delete button
        deleteTimeBuilding.setOnAction(e -> {
            try {
                deleteTimeButtonClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //update button
        updateTimeBuilding.setOnAction(e -> {
            try {
                updateTimeButtonClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox hBoxAddDeleteUpdateTime = new HBox(10);
        hBoxAddDeleteUpdateTime.setPadding(new Insets(20, 20, 20, 0));
        hBoxAddDeleteUpdateTime.getChildren().setAll(deleteTimeBuilding, updateTimeBuilding);

        // adding a openTime for each building
        BorderPane borderPaneAddOpenTime = new BorderPane();
        VBox vBoxAddOpenTime = new VBox();

        ObservableList<Building> buildingNames = FXCollections.observableList(BuildingCommunication.getBuildings());
        ArrayList<String> buildingList = new ArrayList<>();

        for (Building name : buildingNames) {
            buildingList.add(name.getName() + ", " + name.getId());
        }
        ObservableList<String> bl = FXCollections.observableArrayList(buildingList);

        Text day = new Text("Day");
        Text openHolidayTime = new Text("Open Time");
        Text closeHolidayTime = new Text("Close Time");
        Text building = new Text("Building ID");

        TextField dayInput = new TextField();
        TextField openHolidayTimeInput = new TextField();
        TextField closeHolidayTimeInput = new TextField();
        TextField buildingInput = new TextField();

        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        Button addOpenTime = new Button("Add Open Time");

        choiceBox.setItems(bl);

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            String[] string = newValue.split(", ");
            buildingInput.setText(string[1]);

        });

        vBoxAddOpenTime.getChildren().addAll(day, dayInput, openHolidayTime, openHolidayTimeInput,
                closeHolidayTime, closeHolidayTimeInput, building, buildingInput, choiceBox, addOpenTime);
        vBoxAddOpenTime.setPadding(new Insets(0, 10, 10, 10));
        vBoxAddOpenTime.setSpacing(10);
        borderPaneAddOpenTime.setTop(vBoxAddOpenTime);

        addOpenTime.setOnAction(e -> {
            LocalDate dayInputText = LocalDate.parse(dayInput.getText());
            LocalTime openHolidayTimeInputText = LocalTime.parse(openHolidayTimeInput.getText());
            LocalTime closeHolidayTimeInputText = LocalTime.parse(closeHolidayTimeInput.getText());

            OccasionCommunication.addOccasion(dayInputText, openHolidayTimeInputText, closeHolidayTimeInputText, Long.parseLong(buildingInput.getText()));

            dayInput.setText(null);
            openHolidayTimeInput.setText(null);
            closeHolidayTimeInput.setText(null);
            buildingInput.setText(null);

            choiceBox.setValue(null);
        });

        // The below things are to load everything to the building TP
        VBox vBoxBuildingAndButtons = new VBox();
        VBox vBoxOpenTimeAndButtons = new VBox();

        HBox hBoxBuildingTP = new HBox();
        hBoxBuildingTP.setSpacing(100);
        HBox hBoxTimeTP  = new HBox();
        hBoxTimeTP.setSpacing(100);

        VBox vBoxMainBuildingTP = new VBox();
        vBoxMainBuildingTP.setPadding(new Insets(20, 20, 20, 20));

        vBoxBuildingAndButtons.getChildren().addAll(tableBuilding, hBoxAddDeleteUpdateBuilding);
        vBoxOpenTimeAndButtons.getChildren().addAll(tableHoliday, hBoxAddDeleteUpdateTime);

        hBoxBuildingTP.getChildren().addAll(vBoxBuildingAndButtons, borderPaneAddBuilding);
        hBoxTimeTP.getChildren().addAll(vBoxOpenTimeAndButtons, borderPaneAddOpenTime);
        vBoxMainBuildingTP.getChildren().addAll(hBoxBuildingTP, hBoxTimeTP);

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(vBoxMainBuildingTP);
        buildingTP.setContent(scroll);
    }

    /**
     * The method below is implemented for the rooms table which is loaded on the main accordion element in
     * the admin scene.
     */
    public static void roomView() {
        //clearing any previous tableView to avoid multiplication
        tableRoom.getColumns().clear();

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
            new TableColumn<>("capacityField");
        capacityCol.setMinWidth(100);
        capacityCol.setCellValueFactory(
            new PropertyValueFactory<>("capacity"));
        capacityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        capacityCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Room, Integer> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setCapacity(t.getNewValue()));
        capacityCol.setOnEditCommit((TableColumn.CellEditEvent<Room, Integer> t) ->
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setCapacity(t.getNewValue()));

        TableColumn<Room, String> buildingNameCol =
            new TableColumn<>("Building Name");
        buildingNameCol.setMinWidth(100);
        buildingNameCol.setCellValueFactory(
            new PropertyValueFactory<>("building"));
        buildingNameCol.setCellFactory(TextFieldTableCell.<Room, String>forTableColumn(new BuildingToStringConverter()));

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

        for (Building buildingName : buildingNames) {
            buildingList.add(buildingName.getName() + ", " + buildingName.getId());
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
            if (newValue == null) {
                return;
            }
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
        HBox hBoxRoomTP = new HBox();
        hBoxRoomTP.setSpacing(100);
        VBox vBoxRoomsTP = new VBox();

        hBoxRoomTP.getChildren().addAll(tableRoom, borderPaneAddRoom);
        vBoxRoomsTP.setPadding(new Insets(20, 20, 20, 20));
        vBoxRoomsTP.getChildren().addAll(hBoxRoomTP, hBoxAddDeleteUpdateRooms);

        ScrollPane scrollPaneRooms = new ScrollPane();
        scrollPaneRooms.setContent(vBoxRoomsTP);
        roomsTP.setContent(scrollPaneRooms);
    }

    /**
     * The method below is implemented for the restaurant table which is loaded on the main accordion element in
     * the admin scene.
     */
    public static void restaurantView() {
        //clearing any previous tableView to avoid multiplication
        tableRestaurant.getColumns().clear();

        // Table for restaurants
        tableRestaurant.setEditable(true);

        TableColumn<Restaurant, Long> idRestaurantCol =
            new TableColumn<>("id");
        idRestaurantCol.setMinWidth(100);
        idRestaurantCol.setCellValueFactory(
            new PropertyValueFactory<>("id"));

        TableColumn<Restaurant, String> restaurantNameCol =
                new TableColumn<>("Restaurant Name");
        restaurantNameCol.setMinWidth(100);
        restaurantNameCol.setCellValueFactory(
            new PropertyValueFactory<>("name"));
        restaurantNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        restaurantNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Restaurant, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setName(t.getNewValue()));
        restaurantNameCol.setOnEditCommit((TableColumn.CellEditEvent<Restaurant, String> t) ->
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setName(t.getNewValue()));

        TableColumn<Restaurant, Building> buildingNameRestaurantCol =
            new TableColumn<>("Building Name");
        buildingNameRestaurantCol.setMinWidth(100);
        buildingNameRestaurantCol.setCellValueFactory(
            new PropertyValueFactory<>("building"));
        buildingNameRestaurantCol.setCellFactory(TextFieldTableCell.<Restaurant, String>forTableColumn(new BuildingToStringConverter()));
        buildingNameRestaurantCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Restaurant, Building> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setBuilding(t.getNewValue()));

        TableColumn<Restaurant, LocalTime> timeOpenCol =
                new TableColumn<>("Opening Time");
        timeOpenCol.setMinWidth(100);
        timeOpenCol.setCellValueFactory(
                new PropertyValueFactory<>("timeOpen"));
        timeOpenCol.setCellFactory(TextFieldTableCell.<Restaurant, String>forTableColumn((new TimeToStringConverter())));
        timeOpenCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Restaurant, LocalTime> t) -> {
                t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setTimeOpen(t.getNewValue());
            });

        TableColumn<Restaurant, LocalTime> timeCloseCol =
                new TableColumn<>("Closing Time");
        timeCloseCol.setMinWidth(100);
        timeCloseCol.setCellValueFactory(
                new PropertyValueFactory<>("timeClose"));
        timeCloseCol.setCellFactory(TextFieldTableCell.<Restaurant, String>forTableColumn((new TimeToStringConverter())));
        timeCloseCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Restaurant, LocalTime> t) -> {
                t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setTimeClose(t.getNewValue());
            });
        buildingNameRestaurantCol.setOnEditCommit((TableColumn.CellEditEvent<Restaurant, Building> t) ->
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setBuilding(t.getNewValue()));

        ObservableList<Restaurant> restaurantData = FXCollections.observableList(RestaurantCommunication.getRestaurants());
        tableRestaurant.setItems(restaurantData);
        tableRestaurant.getColumns().addAll(idRestaurantCol, restaurantNameCol, buildingNameRestaurantCol, timeOpenCol, timeCloseCol);

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

        // adding a restaurant
        BorderPane borderPaneAddRestaurant = new BorderPane();
        VBox vBoxAddRestaurant = new VBox();

        ObservableList<Building> buildingNames = FXCollections.observableList(BuildingCommunication.getBuildings());
        ArrayList<String> buildingList = new ArrayList<>();

        for (Building name : buildingNames) {
            buildingList.add(name.getName() + ", " + name.getId());
        }
        ObservableList<String> bl = FXCollections.observableArrayList(buildingList);

        Text restaurantName = new Text("Restaurant Name");
        Text buildingName = new Text("Building Name");
        Text openingTime = new Text("Opening Time");
        Text closingTime = new Text("Closing Time");

        TextField restaurantNameInput = new TextField();
        TextField buildingNameInput = new TextField();
        TextField openingTimeInput = new TextField();
        TextField closingTimeInput = new TextField();

        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        Button addRestaurant = new Button("Add Restaurant");

        choiceBox.setItems(bl);

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            String[] string = newValue.split(", ");
            buildingNameInput.setText(string[1]);
        });

        vBoxAddRestaurant.getChildren().addAll(restaurantName, restaurantNameInput,
                buildingName, buildingNameInput, choiceBox, openingTime, openingTimeInput,
                closingTime, closingTimeInput, addRestaurant);
        vBoxAddRestaurant.setPadding(new Insets(0, 10, 10, 10));
        vBoxAddRestaurant.setSpacing(10);
        borderPaneAddRestaurant.setTop(vBoxAddRestaurant);

        addRestaurant.setOnAction(e -> {
            String restaurantNameInputText = restaurantNameInput.getText();
            LocalTime openingTimeInputText = LocalTime.parse(openingTimeInput.getText());
            LocalTime closingTimeInputText = LocalTime.parse(closingTimeInput.getText());

            RestaurantCommunication.addRestaurant(restaurantNameInputText, Long.parseLong(buildingNameInput.getText()), closingTimeInputText, openingTimeInputText);

            restaurantNameInput.setText(null);
            openingTimeInput.setText(null);
            closingTimeInput.setText(null);
            buildingNameInput.setText(null);

            choiceBox.setValue(null);
        });

        // HBox for the buttons under the table
        HBox hBoxAddDeleteUpdateRestaurants = new HBox(10);
        hBoxAddDeleteUpdateRestaurants.setPadding(new Insets(20, 20, 20, 0));
        hBoxAddDeleteUpdateRestaurants.getChildren().setAll(deleteButtonRestaurant, updateButtonRestaurant);

        // This VBox contains the table for the rooms and adding a room
        HBox hBoxRestaurantTP = new HBox();
        hBoxRestaurantTP.setSpacing(100);
        VBox vBoxRestaurantTP = new VBox();

        hBoxRestaurantTP.getChildren().addAll(tableRestaurant, borderPaneAddRestaurant);
        vBoxRestaurantTP.setPadding(new Insets(20, 20, 20, 20));
        vBoxRestaurantTP.getChildren().addAll(hBoxRestaurantTP, hBoxAddDeleteUpdateRestaurants);

        ScrollPane scrollPaneRestaurant = new ScrollPane();
        scrollPaneRestaurant.setContent(vBoxRestaurantTP);
        restaurantsTP.setContent(scrollPaneRestaurant);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
