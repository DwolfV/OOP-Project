package nl.tudelft.oopp.demo.helperclasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.controllers.AdminSceneController;

import java.util.ArrayList;

public class AdminRoomPane {

    private static TableView<Room> tableRoom;
    private static Button updateRoom = new Button("Update");
    private static Button deleteRoom = new Button("Delete");

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
     * Get the BorderPane of the Building info list.
     * @return BorderPane of Building Info
     */
    public static BorderPane getRoomBP() {
        //Reset TableView tableRoom
        tableRoom = new TableView<>();
        tableRoom.getColumns().clear();
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
        deleteRoom.setOnAction(e -> {
            try {
                deleteRoomButtonClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //update button
        updateRoom.setOnAction(e -> {
            try {
                updateRoomButtonClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // HBox for the buttons under the table
        HBox hboxBottom = new HBox(10);
        hboxBottom.setPadding(new Insets(20, 20, 20, 0));
        hboxBottom.getChildren().setAll(deleteRoom, updateRoom);

        // adding room scene
        ObservableList<Building> buildingNames = FXCollections.observableList(BuildingCommunication.getBuildings());
        ArrayList<String> buildingList = new ArrayList<>();

        for (Building buildingName : buildingNames) {
            buildingList.add(buildingName.getName() + ", " + buildingName.getId());
        }
        ObservableList<String> bl = FXCollections.observableArrayList(buildingList);

        BorderPane borderPaneAddRoom = new BorderPane();
        VBox vboxRight = new VBox();

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

        vboxRight.getChildren().addAll(roomName, roomNameField, capacity, capacityField, building, buildingField, choiceBox, addButton);
        vboxRight.setPadding(new Insets(0, 10, 10, 10));
        vboxRight.setSpacing(10);
        borderPaneAddRoom.setTop(vboxRight);

        addButton.setOnAction(e -> {
            String roomName1 = roomNameField.getText();
            int capacity1 = Integer.parseInt(capacityField.getText());

            RoomCommunication.addRoom(roomName1, capacity1, Long.parseLong(buildingField.getText()));

            buildingField.setText(null);
            roomNameField.setText(null);
            capacityField.setText(null);
            choiceBox.setValue(null);
        });

        tableRoom.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Pane paneCenter = new GridPane();
        paneCenter.getChildren().setAll(tableRoom);

        return AdminSceneController.getBorderPane(paneCenter, hboxBottom, vboxRight);
    }
}
