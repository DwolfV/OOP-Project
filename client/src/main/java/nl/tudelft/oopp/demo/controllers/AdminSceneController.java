package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.BuildingToStringConvertor;
import nl.tudelft.oopp.demo.helperclasses.Room;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminSceneController implements Initializable {

//    @FXML
//    private TextField BuildingName;
//    @FXML
//    private TextField StreetName;
//    @FXML
//    private TextField StreetNumber;
//    @FXML
//    private TextField ZipCode;
//    @FXML
//    private TextField City;

    final Button addButton = new Button("Add");
    final Button updateButton = new Button("Update");
    final Button deleteButton = new Button("Delete");
    final BorderPane borderPane = new BorderPane();

    private final TableView<Building> tableBuilding = new TableView<>();
    private final TableView<Room> tableRoom = new TableView<>();
    private ObservableList<Building> buildingData = FXCollections.observableArrayList();
    private ObservableList<Room> roomData = FXCollections.observableArrayList();

    public AdminSceneController() {
    }

    @FXML
    public void handleAddBuildingButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addBuildingScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("New Building");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddRoomButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addRoomScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("New Room");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    final HBox hb = new HBox();

    // View Building and Add a Building
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
        buildingCol.setCellFactory(TextFieldTableCell.<Building>forTableColumn());
        buildingCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> {
                    ((Building) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                });

        TableColumn<Building, String> streetNameCol =
                new TableColumn<>("Street Name");
        streetNameCol.setMinWidth(100);
        streetNameCol.setCellValueFactory(
                new PropertyValueFactory<>("streetName"));
        streetNameCol.setCellFactory(TextFieldTableCell.<Building>forTableColumn());
        streetNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> {
                    ((Building) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setStreetName(t.getNewValue());
                });

        TableColumn<Building, String> streetNumCol =
                new TableColumn<>("Street Number");
        streetNumCol.setMinWidth(100);
        streetNumCol.setCellValueFactory(
                new PropertyValueFactory<>("streetNumber"));
        streetNumCol.setCellFactory(TextFieldTableCell.<Building>forTableColumn());
        streetNumCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> {
                    ((Building) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setStreetNumber(t.getNewValue());
                });

        TableColumn<Building, String> zipCodeCol =
                new TableColumn<>("Zip Code");
        zipCodeCol.setMinWidth(100);
        zipCodeCol.setCellValueFactory(
                new PropertyValueFactory<>("zipCode"));
        zipCodeCol.setCellFactory(TextFieldTableCell.<Building>forTableColumn());
        zipCodeCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> {
                    ((Building) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setZipCode(t.getNewValue());
                });

        TableColumn<Building, String> cityCol =
                new TableColumn<>("City");
        cityCol.setMinWidth(100);
        cityCol.setCellValueFactory(
                new PropertyValueFactory<>("City"));
        cityCol.setCellFactory(TextFieldTableCell.<Building>forTableColumn());
        cityCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> {
                    ((Building) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setCity(t.getNewValue());
                });

        buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());
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
        hBox.setSpacing(5);
        hBox.setPadding(new Insets(10, 10, 0, 10));

        borderPane.setTop(label);
        borderPane.setCenter(tableBuilding);
        borderPane.setBottom(hBox);

        ((Group) scene.getRoot()).getChildren().addAll(borderPane);

        secondStage.setScene(scene);
        secondStage.show();
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

    // View Room and Add a Room
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
        roomCol.setCellFactory(TextFieldTableCell.<Room>forTableColumn());
        roomCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Room, String> t) -> {
                    ((Room) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                });

        TableColumn<Room, Integer> capacityCol =
                new TableColumn<>("Capacity");
        capacityCol.setMinWidth(100);
        capacityCol.setCellValueFactory(
                new PropertyValueFactory<>("capacity"));
        capacityCol.setCellFactory(TextFieldTableCell.<Room, Integer>forTableColumn(new IntegerStringConverter()));
        capacityCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Room, Integer> t) -> {
                    ((Room) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setCapacity(t.getNewValue());
                });

        TableColumn<Room, String> buildingNameCol =
                new TableColumn<>("Building Name");
        buildingNameCol.setMinWidth(100);
        buildingNameCol.setCellValueFactory(
                new PropertyValueFactory<>("building"));
        buildingNameCol.setCellFactory(TextFieldTableCell.<Room, String>forTableColumn(new BuildingToStringConvertor()));

        roomData = FXCollections.observableList(RoomCommunication.getRooms());
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
        hBox.setSpacing(5);
        hBox.setPadding(new Insets(10, 10, 0, 10));

        borderPane.setTop(label);
        borderPane.setCenter(tableRoom);
        borderPane.setBottom(hBox);

        ((Group) viewRoomScene.getRoot()).getChildren().addAll(borderPane);

        thirdStage.setScene(viewRoomScene);
        thirdStage.show();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
