package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.BuildingToStringConvertor;
import nl.tudelft.oopp.demo.helperclasses.Room;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminSceneController implements Initializable {

    @FXML
    private TextField RoomName;
    @FXML
    private TextField Capacity;
    @FXML
    private TextField Building;
    @FXML
    private ChoiceBox<Building> building;

    final Button addButton = new Button("Add");
    final Button updateButton = new Button("Update");
    final Button deleteButton = new Button("Delete");
    final BorderPane borderPane = new BorderPane();

    private final TableView<Building> tableBuilding = new TableView<>();
    private final TableView<Room> tableRoom = new TableView<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    // View, update, add and delete Building

    @FXML
    public void handleAddBuildingButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addBuildingScene.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("New Building");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void updateBuildingButtonClicked() {
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

    @FXML
    public void handleViewBuildingButtonClicked() {

        tableBuilding.getColumns().clear();

        Stage secondStage = new Stage();
        Scene scene = new Scene(new Group());

        secondStage.setTitle("Table View");
        secondStage.setWidth(640);
        secondStage.setHeight(550);

        final Label label = new Label("Buildings");
        label.setFont(new Font("Arial", 20));

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
        deleteButton.setOnAction(e -> {
            deleteBuildingButtonClicked();
        });

        //update button
        updateButton.setOnAction(e -> {
            updateBuildingButtonClicked();
        });

        // add button
        addButton.setOnAction(e -> {
            try {
                handleAddBuildingButton();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        borderPane.setPadding(new Insets(10,10,10,10));

        final HBox hBox = new HBox(deleteButton, updateButton, addButton);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10, 10, 0, 10));

        borderPane.setTop(label);
        borderPane.setCenter(tableBuilding);
        borderPane.setBottom(hBox);

        ((Group) scene.getRoot()).getChildren().addAll(borderPane);

        secondStage.setScene(scene);
        secondStage.show();
    }

    // View, update, add and delete Room

    @FXML
    public void handleAddRoomButton() {
        ObservableList<Building> buildingNames = FXCollections.observableList(BuildingCommunication.getBuildings());
        ArrayList<String> buildingList = new ArrayList<String>();

        for (int i = 0; i < buildingNames.size(); i++) {
            buildingList.add(buildingNames.get(i).getName() + ", " + buildingNames.get(i).getId());
        }
        ObservableList<String> bl = FXCollections.observableArrayList(buildingList);


        Stage stage = new Stage();

        BorderPane  borderPane = new BorderPane();
        VBox vBox = new VBox();

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

        vBox.getChildren().addAll(roomName, RoomName, capacity, Capacity, building, Building, choiceBox, addButton);
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.setSpacing(10);
        borderPane.setTop(vBox);

        stage.setScene(new Scene(borderPane, 300, 300));
        stage.setTitle("New Room");
        stage.show();

        addButton.setOnAction((EventHandler) event -> {
            String roomName1 = RoomName.getText();
            Integer capacity1 = Integer.parseInt(Capacity.getText());

            RoomCommunication.addRoom(roomName1, capacity1, Long.parseLong(Building.getText()));

            Building.setText(null);
            RoomName.setText(null);
            Capacity.setText(null);
        });
    }

    public void deleteRoomButtonClicked() {
        ObservableList<Room> roomSelected, allRooms;
        allRooms = tableRoom.getItems();
        Room room = tableRoom.getSelectionModel().getSelectedItem();

        allRooms.remove(room);
        RoomCommunication.removeRoom(room.getId());
    }

    private void updateRoomButtonClicked() {
        Room room = tableRoom.getSelectionModel().getSelectedItem();
        RoomCommunication.updateRoom(room.getId(), room.getName(), room.getCapacity(), room.getBuilding().getId());
    }

    @FXML
    public void handleViewRoomButtonClicked() {

        tableRoom.getColumns().clear();

        Stage thirdStage = new Stage();
        Scene viewRoomScene = new Scene(new Group());

        thirdStage.setTitle("Table View");
        thirdStage.setWidth(440);
        thirdStage.setHeight(550);

        final Label label = new Label("Rooms");
        label.setFont(new Font("Arial", 20));

        tableRoom.setEditable(true);

        TableColumn<Room, Long> idCol =
                new TableColumn<>("id");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(
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
        tableRoom.getColumns().addAll(idCol, roomCol, capacityCol, buildingNameCol);

        //delete button
        deleteButton.setOnAction(e -> {
            deleteRoomButtonClicked();
        });

        //update button
        updateButton.setOnAction(e -> {
            updateRoomButtonClicked();
        });

        // add button
        addButton.setOnAction(e -> {
            try {
                handleAddRoomButton();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        borderPane.setPadding(new Insets(10,10,10,10));

        final HBox hBox = new HBox(deleteButton, updateButton, addButton);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10, 10, 0, 10));

        borderPane.setTop(label);
        borderPane.setCenter(tableRoom);
        borderPane.setBottom(hBox);

        ((Group) viewRoomScene.getRoot()).getChildren().addAll(borderPane);

        thirdStage.setScene(viewRoomScene);
        thirdStage.show();
    }

}
