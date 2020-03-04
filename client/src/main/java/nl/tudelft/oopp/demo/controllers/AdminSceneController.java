package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Room;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminSceneController implements Initializable {

    @FXML
    private TextField BuildingID;
    @FXML
    private TextField BuildingName;
    @FXML
    private TextField StreetName;
    @FXML
    private TextField StreetNumber;
    @FXML
    private TextField ZipCode;
    @FXML
    private TextField City;


    @FXML
    private TextField RoomID;
    @FXML
    private TextField RoomName;
    @FXML
    private TextField Capacity;
    @FXML
    private TextField Building;


    private TableView<Building> tableBuilding = new TableView<>();
    private TableView<Room> tableRoom = new TableView<>();
    private ObservableList<Building> buildingData = FXCollections.observableArrayList();
    private ObservableList<Room> roomData = FXCollections.observableArrayList();

    public AdminSceneController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Loading User Data");
    }

    @FXML
    public void handleAddBuildingButton(ActionEvent event) throws Exception {
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
    public void handleAddRoomButton(ActionEvent event) throws Exception {
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
        secondStage.setWidth(450);
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
        Button deleteButtonBuilding = new Button("Delete");
        deleteButtonBuilding.setOnAction(e -> {
            deleteBuildingButtonClicked();
        });

        //update button
        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            updateBuildingButtonClicked();
        });

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, tableBuilding, deleteButtonBuilding, updateButton);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

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

        //buildingSelected.forEach(allBuildings::remove);
//        buildingSelected.forEach(building -> {
//            System.out.println(building.getId());
//            BuildingCommunication.removeBuilding(building.getId());
//        } );
        allBuildings.remove(building);
        BuildingCommunication.removeBuilding(building.getId());
    }

    @FXML
    private void handleTextFieldData(ActionEvent event){
        Long buildingId = Long.parseLong(BuildingID.getText());
        String buildingName = BuildingName.getText();
        String streetName = StreetName.getText();
        String streetNumber = StreetNumber.getText();
        String zipCode = ZipCode.getText();
        String city = City.getText();

        BuildingCommunication.addBuilding(buildingId, buildingName, streetName, streetNumber, zipCode, city);
    }

    // View Room and Add a Room
    @FXML
    public void handleViewRoomButtonClicked() {

        tableRoom.getColumns().clear();

        Stage thirdStage = new Stage();
        Scene viewRoomScene = new Scene(new Group());

        thirdStage.setTitle("Table View");
        thirdStage.setWidth(450);
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
                new PropertyValueFactory<>("name"));
        buildingNameCol.setCellFactory(TextFieldTableCell.<Room>forTableColumn());
        buildingNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Room, String> t) -> {
                    ((Room) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                });

        roomData = FXCollections.observableList(RoomCommunication.getRooms());
        tableRoom.setItems(roomData);
        tableRoom.getColumns().addAll(idCol, roomCol, capacityCol, buildingNameCol);

        //delete button
        Button deleteButtonRoom = new Button("Delete");
        deleteButtonRoom.setOnAction(e -> {
            deleteRoomButtonClicked();
        });

        //update button
        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            updateRoomButtonClicked();
        });

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, tableRoom, deleteButtonRoom, updateButton);

        ((Group) viewRoomScene.getRoot()).getChildren().addAll(vbox);

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

    @FXML
    private void handleTextFieldDataRoom(ActionEvent event){
        long roomID = Long.parseLong(BuildingID.getText());
        String roomName = RoomName.getText();
        Integer capacity = Integer.parseInt(Capacity.getText());

        RoomCommunication.addRoom(roomName, capacity, Long.parseLong(Building.getText()));
    }

}
