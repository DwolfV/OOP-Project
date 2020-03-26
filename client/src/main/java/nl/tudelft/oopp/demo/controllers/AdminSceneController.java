package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.converter.IntegerStringConverter;

import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.OccasionCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.BuildingToStringConverter;
import nl.tudelft.oopp.demo.helperclasses.DateToStringConverter;
import nl.tudelft.oopp.demo.helperclasses.Occasion;
import nl.tudelft.oopp.demo.helperclasses.Restaurant;
import nl.tudelft.oopp.demo.helperclasses.Room;
import nl.tudelft.oopp.demo.helperclasses.TimeToStringConverter;

public class AdminSceneController implements Initializable {
    public static final TitledPane buildingTP = new TitledPane("Buildings", new Button("View"));
    public static final TitledPane roomsTP = new TitledPane("Rooms", new Button("View"));
    public static final TitledPane restaurantsTP = new TitledPane("Restaurants", new Button("View"));
    static final Button updateButtonBuilding = new Button("Update");
    static final Button deleteButtonBuilding = new Button("Delete");
    static final Button updateTimeBuilding = new Button("Update");
    static final Button deleteTimeBuilding = new Button("Delete");
    static final Button updateButtonRoom = new Button("Update");
    static final Button deleteButtonRoom = new Button("Delete");
    static final Button updateButtonRestaurant = new Button("Update");
    static final Button deleteButtonRestaurant = new Button("Delete");
    private static final TableView<Building> tableBuilding = new TableView<>();
    private static final TableView<Occasion> tableHoliday = new TableView<>();
    private static final TableView<Room> tableRoom = new TableView<>();
    private static final TableView<Restaurant> tableRestaurant = new TableView<>();
    public MainSceneController mainSceneController;
    @FXML
    private Accordion ac;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildingTP.getStyleClass().setAll("table-pane");
        roomsTP.getStyleClass().setAll("table-pane");
        restaurantsTP.getStyleClass().setAll("table-pane");

        buildingView();
        roomView();
        restaurantView();

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        ac.setPrefWidth(screenBounds.getWidth() - 200);
        ac.getPanes().addAll(buildingTP, roomsTP, restaurantsTP);
    }

    public void setControllers(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

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
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        VBox vbox = new VBox();
        vbox.setSpacing(40);
        BorderPane buildingInfoBP = getBuildingInfoBP();
        BorderPane buildingTimesBP = getBuildingTimesBP();
        buildingInfoBP.getStyleClass().setAll("border-pane-admin");
        buildingTimesBP.getStyleClass().setAll("border-pane-admin");
        buildingInfoBP.setPrefWidth(screenBounds.getWidth() - 300);
        buildingTimesBP.setPrefWidth(screenBounds.getWidth() - 300);
        vbox.getChildren().setAll(buildingInfoBP, buildingTimesBP);

        ScrollPane scroll = new ScrollPane();
        scroll.setMaxHeight(screenBounds.getHeight()-300);
        scroll.setContent(vbox);
        scroll.setPadding(new Insets(30));
        scroll.getStyleClass().setAll("scroll-pane-admin");
        buildingTP.setContent(scroll);
    }

    public static BorderPane getBuildingInfoBP() {

        //Reset TableView tableBuilding
        tableBuilding.getColumns().clear();
        tableBuilding.setEditable(true);

        // Table of Buildings with Info
        // Pane Center
        TableColumn<Building, Long> idCol = new TableColumn<>("id");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Building, String> buildingCol = new TableColumn<>("Building Name");
        buildingCol.setMinWidth(100);
        buildingCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        buildingCol.setCellFactory(TextFieldTableCell.forTableColumn());
        buildingCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setName(t.getNewValue()));
        buildingCol.setOnEditCommit((TableColumn.CellEditEvent<Building, String> t) ->
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setName(t.getNewValue()));

        TableColumn<Building, LocalTime> openTimeCol = new TableColumn<>("Open Time");
        openTimeCol.setMinWidth(100);
        openTimeCol.setCellValueFactory(new PropertyValueFactory<>("openTime"));
        openTimeCol.setCellFactory(TextFieldTableCell.forTableColumn(new TimeToStringConverter()));
        openTimeCol.setOnEditCommit((TableColumn.CellEditEvent<Building, LocalTime> t) ->
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setOpenTime(t.getNewValue()));

        TableColumn<Building, LocalTime> closeTimeCol = new TableColumn<>("Close Time");
        closeTimeCol.setMinWidth(100);
        closeTimeCol.setCellValueFactory(new PropertyValueFactory<>("closeTime"));
        closeTimeCol.setCellFactory((TextFieldTableCell.forTableColumn(new TimeToStringConverter())));
        closeTimeCol.setOnEditCommit((TableColumn.CellEditEvent<Building, LocalTime> t) ->
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setCloseTime(t.getNewValue()));

        TableColumn<Building, String> streetNameCol = new TableColumn<>("Street Name");
        streetNameCol.setMinWidth(100);
        streetNameCol.setCellValueFactory(new PropertyValueFactory<>("streetName"));
        streetNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        streetNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) ->
                        t.getTableView().getItems().get(t.getTablePosition().getRow()).setStreetName(t.getNewValue()));

        TableColumn<Building, String> streetNumCol = new TableColumn<>("Street Number");
        streetNumCol.setMinWidth(100);
        streetNumCol.setCellValueFactory(new PropertyValueFactory<>("streetNumber"));
        streetNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        streetNumCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setStreetNumber(t.getNewValue()));

        TableColumn<Building, String> zipCodeCol = new TableColumn<>("Zip Code");
        zipCodeCol.setMinWidth(100);
        zipCodeCol.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        zipCodeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        zipCodeCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setZipCode(t.getNewValue()));

        TableColumn<Building, String> cityCol = new TableColumn<>("City");
        cityCol.setMinWidth(100);
        cityCol.setCellValueFactory(new PropertyValueFactory<>("City"));
        cityCol.setCellFactory(TextFieldTableCell.forTableColumn());
        cityCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setCity(t.getNewValue()));

        ObservableList<Building> buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());
        tableBuilding.setItems(buildingData);
        tableBuilding.getColumns().addAll(idCol, buildingCol, openTimeCol, closeTimeCol, streetNameCol, streetNumCol, zipCodeCol, cityCol);

        // HBox for deleting and updating buildings
        // Pane bottom

        // Delete Button
        deleteButtonBuilding.setOnAction(e -> {
            try {
                deleteBuildingButtonClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Update button
        updateButtonBuilding.setOnAction(e -> {
            try {
                updateBuildingButtonClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox hboxBottom = new HBox();
        hboxBottom.setPadding(new Insets(20, 20, 20, 0));
        hboxBottom.getChildren().setAll(deleteButtonBuilding, updateButtonBuilding);

        // VBox to add a new building
        // Pane Right
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

        VBox vboxRight = new VBox();
        vboxRight.setPadding(new Insets(0, 10, 10, 10));
        vboxRight.setSpacing(10);
        vboxRight.getChildren().addAll(buildingName, buildingNameInput, openTime, openTimeInput,
                closeTime, closeTimeInput, streetName, streetNameInput, streetNumber, streetNumberInput,
                zipCode, zipCodeInput, city, cityInput, addButtonBuilding);

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

        // Style classes panes
        ScrollPane centerScroll = new ScrollPane();
        centerScroll.setContent(tableBuilding);

        centerScroll.getStyleClass().setAll("admin-center");
        hboxBottom.getStyleClass().setAll("admin-bottom");
        vboxRight.getStyleClass().setAll("admin-right");
        addButtonBuilding.getStyleClass().setAll("admin-pane-button");
        deleteButtonBuilding.getStyleClass().setAll("admin-pane-button");
        updateButtonBuilding.getStyleClass().setAll("admin-pane-button");

        tableBuilding.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Return BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tableBuilding);
        borderPane.setBottom(hboxBottom);
        borderPane.setRight(vboxRight);
        return borderPane;
    }

    public static BorderPane getBuildingTimesBP() {

        // Reset TableView tableHoliday
        tableHoliday.getColumns().clear();
        tableHoliday.setEditable(true);

        // Set table of buildings with changed times
        // Center Pane
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

        // VBox to delete and update rows from buidling time table
        // Bottom Pane

        // Delete button
        deleteTimeBuilding.setOnAction(e -> {
            try {
                deleteTimeButtonClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //Update button
        updateTimeBuilding.setOnAction(e -> {
            try {
                updateTimeButtonClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox hboxBottom = new HBox(10);
        hboxBottom.setPadding(new Insets(20, 20, 20, 0));
        hboxBottom.getChildren().setAll(deleteTimeBuilding, updateTimeBuilding);

        // Adding a openTime for a building on a date
        // Right Pane
        VBox vboxRight = new VBox();

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

        vboxRight.getChildren().addAll(day, dayInput, openHolidayTime, openHolidayTimeInput,
                closeHolidayTime, closeHolidayTimeInput, building, buildingInput, choiceBox, addOpenTime);
        vboxRight.setPadding(new Insets(0, 10, 10, 10));
        vboxRight.setSpacing(10);

        // Add open time button
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

        // Style classes panes
        ScrollPane centerScroll = new ScrollPane();
        centerScroll.setContent(tableHoliday);

        centerScroll.getStyleClass().setAll("admin-center");
        hboxBottom.getStyleClass().setAll("admin-bottom");
        vboxRight.getStyleClass().setAll("admin-right");

        // Return border pane
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().setAll("admin-border-pane");
        borderPane.setCenter(centerScroll);
        borderPane.setBottom(hboxBottom);
        borderPane.setRight(vboxRight);
        return borderPane;
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
        HBox hoBoxAddDeleteUpdateRooms = new HBox(10);
        hoBoxAddDeleteUpdateRooms.setPadding(new Insets(20, 20, 20, 0));
        hoBoxAddDeleteUpdateRooms.getChildren().setAll(deleteButtonRoom, updateButtonRoom);

        // adding room scene
        ObservableList<Building> buildingNames = FXCollections.observableList(BuildingCommunication.getBuildings());
        ArrayList<String> buildingList = new ArrayList<>();

        for (Building buildingName : buildingNames) {
            buildingList.add(buildingName.getName() + ", " + buildingName.getId());
        }
        ObservableList<String> bl = FXCollections.observableArrayList(buildingList);

        BorderPane borderPaneAddRoom = new BorderPane();
        VBox veBoxAddRoom = new VBox();

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
            if (newValue == null) {
                return;
            }
            String[] string = newValue.split(", ");
            buildingField.setText(string[1]);
        });

        veBoxAddRoom.getChildren().addAll(roomName, roomNameField, capacity, capacityField, building, buildingField, choiceBox, addButton);
        veBoxAddRoom.setPadding(new Insets(0, 10, 10, 10));
        veBoxAddRoom.setSpacing(10);
        borderPaneAddRoom.setTop(veBoxAddRoom);

        addButton.setOnAction(e -> {
            String roomName1 = roomNameField.getText();
            int capacity1 = Integer.parseInt(capacityField.getText());

            RoomCommunication.addRoom(roomName1, capacity1, Long.parseLong(buildingField.getText()));

            buildingField.setText(null);
            roomNameField.setText(null);
            capacityField.setText(null);
            choiceBox.setValue(null);
        });

        // This VBox contains the table for the rooms and adding a room
        HBox hboxRoomTP = new HBox();
        hboxRoomTP.setSpacing(100);
        VBox vboxRoomTP = new VBox();

        hboxRoomTP.getChildren().addAll(tableRoom, borderPaneAddRoom);
        vboxRoomTP.setPadding(new Insets(20, 20, 20, 20));
        vboxRoomTP.getChildren().addAll(hboxRoomTP, hoBoxAddDeleteUpdateRooms);

        ScrollPane scrollPaneRooms = new ScrollPane();
        scrollPaneRooms.setContent(vboxRoomTP);
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
        VBox veBoxAddRestaurant = new VBox();

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

        veBoxAddRestaurant.getChildren().addAll(restaurantName, restaurantNameInput,
                buildingName, buildingNameInput, choiceBox, openingTime, openingTimeInput,
                closingTime, closingTimeInput, addRestaurant);
        veBoxAddRestaurant.setPadding(new Insets(0, 10, 10, 10));
        veBoxAddRestaurant.setSpacing(10);
        borderPaneAddRestaurant.setTop(veBoxAddRestaurant);

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
        HBox hoBoxAddDeleteUpdateRestaurants = new HBox(10);
        hoBoxAddDeleteUpdateRestaurants.setPadding(new Insets(20, 20, 20, 0));
        hoBoxAddDeleteUpdateRestaurants.getChildren().setAll(deleteButtonRestaurant, updateButtonRestaurant);

        // This VBox contains the table for the rooms and adding a room
        HBox hoBoxRestaurantTP = new HBox();
        hoBoxRestaurantTP.setSpacing(100);
        VBox veBoxRestaurantTP = new VBox();

        hoBoxRestaurantTP.getChildren().addAll(tableRestaurant, borderPaneAddRestaurant);
        veBoxRestaurantTP.setPadding(new Insets(20, 20, 20, 20));
        veBoxRestaurantTP.getChildren().addAll(hoBoxRestaurantTP, hoBoxAddDeleteUpdateRestaurants);

        ScrollPane scrollPaneRestaurant = new ScrollPane();
        scrollPaneRestaurant.setContent(veBoxRestaurantTP);
        restaurantsTP.setContent(scrollPaneRestaurant);
    }
}
