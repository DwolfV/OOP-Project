package nl.tudelft.oopp.demo.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.communication.UserCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.BuildingToStringConvertor;
import nl.tudelft.oopp.demo.helperclasses.Room;
import nl.tudelft.oopp.demo.views.MainDisplay;

import java.io.IOException;
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

    private final TableView<Building> tableBuilding = new TableView<>();
    private final TableView<Room> tableRoom = new TableView<>();

    final Button updateButtonBuilding = new Button("Update");
    final Button deleteButtonBuilding = new Button("Delete");

    final Button updateButtonRoom = new Button("Update");
    final Button deleteButtonRoom = new Button("Delete");

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
            List<Label> labels = new ArrayList<>();

            int count=0, c=0; // count - for lists, c - for tps

            // load the scene
            BorderPane rootScene = FXMLLoader.load(getClass().getResource("/reservationsScene.fxml"));

            // fill the accordion
            for(int i = 0; i < buildingData.size(); i++){

                //Look for rooms for the building i;
                ObservableList<Room> showRooms = FXCollections.observableArrayList();
                for(int k = 0; k < rooms.size(); k++){
                    if(rooms.get(k).getBuilding().getName().equals(buildingData.get(i).getName())){
                        showRooms.add(rooms.get(k));
                    }
                }

                //if there are rooms for the building i - show them;
                if(showRooms.size()!=0){
                    tps[c] = new TitledPane();
                    GridPane grid = new GridPane();
                    ColumnConstraints colConst = new ColumnConstraints();
                    colConst.setPercentWidth(100/2);
                    grid.getColumnConstraints().add(colConst);
                    grid.setVgap(4);
                    grid.setPadding(new Insets(5, 5, 5, 5));

                    for (Room room : rooms) {
                        System.out.println(buildingData.get(i).getName() + " " + room.getName());
                    }

                    for(int j = 0; j < showRooms.size(); j++){
                        Label label1 = new Label(showRooms.get(j).getName());
                        labels.add(label1);
                        Button button1 = new Button("Reserve");
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

    public void updateBuildingButtonClicked() {
        Building building = tableBuilding.getSelectionModel().getSelectedItem();
        BuildingCommunication.updateBuilding(building.getId(), building.getName(), building.getStreetName(), building.getStreetNumber(),building.getZipCode(), building.getCity());
    }

    public void deleteBuildingButtonClicked() {
        ObservableList<Building> buildingSelected, allBuildings;
        allBuildings = tableBuilding.getItems();
        Building building = tableBuilding.getSelectionModel().getSelectedItem();

        allBuildings.remove(building);
        BuildingCommunication.removeBuilding(building.getId());
    }

    public void updateRoomButtonClicked() {
        Room room = tableRoom.getSelectionModel().getSelectedItem();
        RoomCommunication.updateRoom(room.getId(), room.getName(), room.getCapacity(), room.getBuilding().getId());
    }

    public void deleteRoomButtonClicked() {
        ObservableList<Room> roomSelected, allRooms;
        allRooms = tableRoom.getItems();
        Room room = tableRoom.getSelectionModel().getSelectedItem();

        allRooms.remove(room);
        RoomCommunication.removeRoom(room.getId());
    }

    public void refreshBuildingTable() {

    }

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

        HBox hBoxAddDeleteUpdate = new HBox(10);
        hBoxAddDeleteUpdate.setPadding(new Insets(20, 20, 20, 0));
        hBoxAddDeleteUpdate.getChildren().setAll(deleteButtonBuilding, updateButtonBuilding);

        // adding a building
        BorderPane  borderPaneAddBuidling = new BorderPane();
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
        vBoxAddBuilding.setPadding(new Insets(10,10,10,10));
        vBoxAddBuilding.setSpacing(10);
        borderPaneAddBuidling.setTop(vBoxAddBuilding);

        addButtonBuilding.setOnAction((EventHandler) e -> {
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

        // This VBox contains the table for the rooms and adding a room
        VBox vBoxBuildingTP = new VBox();
        HBox hBoxBuildingTP = new HBox();
        hBoxBuildingTP.setSpacing(100);
        hBoxBuildingTP.getChildren().addAll(tableBuilding, borderPaneAddBuidling);
        vBoxBuildingTP.setPadding(new Insets(20, 20, 20, 20));
        vBoxBuildingTP.getChildren().addAll(hBoxBuildingTP, hBoxAddDeleteUpdate);
        buildingTP.setContent(vBoxBuildingTP);

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
        HBox hBoxAddDeleteUpdateRooms = new HBox(10);
        hBoxAddDeleteUpdateRooms.setPadding(new Insets(20, 20, 20, 0));
        hBoxAddDeleteUpdateRooms.getChildren().setAll(deleteButtonRoom, updateButtonRoom);

        // adding room scene
        ObservableList<Building> buildingNames = FXCollections.observableList(BuildingCommunication.getBuildings());
        ArrayList<String> buildingList = new ArrayList<String>();

        for (int i = 0; i < buildingNames.size(); i++) {
            buildingList.add(buildingNames.get(i).getName() + ", " + buildingNames.get(i).getId());
        }
        ObservableList<String> bl = FXCollections.observableArrayList(buildingList);

        BorderPane  borderPaneAddRoom = new BorderPane();
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
        vBoxAddRoom.setPadding(new Insets(10,10,10,10));
        vBoxAddRoom.setSpacing(10);
        borderPaneAddRoom.setTop(vBoxAddRoom);

        addButton.setOnAction((EventHandler) e -> {
            String roomName1 = RoomName.getText();
            Integer capacity1 = Integer.parseInt(Capacity.getText());

            RoomCommunication.addRoom(roomName1, capacity1, Long.parseLong(Building.getText()));

            Building.setText(null);
            RoomName.setText(null);
            Capacity.setText(null);
        });

        // This VBox contains the table for the rooms and adding a room
        VBox vBoxRoomsTP = new VBox();
        HBox hBoxRoomTP = new HBox();
        hBoxRoomTP.setSpacing(100);
        hBoxRoomTP.getChildren().addAll(tableRoom, borderPaneAddRoom);
        vBoxRoomsTP.setPadding(new Insets(20, 20, 20, 20));
        vBoxRoomsTP.getChildren().addAll(hBoxRoomTP, hBoxAddDeleteUpdateRooms);
        roomsTP.setContent(vBoxRoomsTP);

        // load everything
        VBox vBox = new VBox(ac);
        bPane.setCenter(vBox);
        bPane.setPadding(new Insets(10, 50, 10, 50));
        rootScene.setCenter(bPane);

        // show the scene
        MainDisplay.secondaryStage.setScene(new Scene(rootScene, screenSize.getWidth(), screenSize.getHeight()));
        MainDisplay.secondaryStage.setTitle("Admin");
        MainDisplay.secondaryStage.show();
        closeSecondaryStage();
    }

}
