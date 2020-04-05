package nl.tudelft.oopp.demo.helperclasses;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.controllers.AdminSceneController;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;

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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        if (RoomCommunication.updateRoom(room.getId(), room.getName(), room.getCapacity(),
            room.getBuilding().getId()).equals("Successful")) {
            alert.hide();
        } else {
            alert.setContentText(RoomCommunication.updateRoom(room.getId(), room.getName(), room.getCapacity(), room.getBuilding().getId()));
            alert.showAndWait();
        }
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
     *
     * @return BorderPane of Building Info
     */
    public static BorderPane getRoomBP(Accordion ac) {
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
                AdminSceneController.loadRoomTP(ac);
                ac.setExpandedPane(AdminSceneController.roomTP);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // HBox for the buttons under the table
        HBox hboxBottom = new HBox(5);
        hboxBottom.getChildren().setAll(deleteRoom, updateRoom);

        // adding room scene
        ObservableList<Building> buildingNames = AdminBuildingPane.buildingData;
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

        vboxRight.getChildren().addAll(roomName, roomNameField, capacity, capacityField, building, choiceBox, addButton);
        vboxRight.setSpacing(5);
        borderPaneAddRoom.setTop(vboxRight);

        addButton.setOnAction(e -> {
            String roomName1 = roomNameField.getText();
            int capacity1 = Integer.parseInt(capacityField.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            if (RoomCommunication.addRoom(roomName1, capacity1, Long.parseLong(buildingField.getText())).equals("Successful")) {
                alert.hide();
            } else {
                alert.setContentText(RoomCommunication.addRoom(roomName1, capacity1, Long.parseLong(buildingField.getText())));
                alert.showAndWait();
            }

            buildingField.setText(null);
            roomNameField.setText(null);
            capacityField.setText(null);
            choiceBox.setValue(null);
            AdminSceneController.loadRoomTP(ac);
            ac.setExpandedPane(AdminSceneController.roomTP);
        });

        tableRoom.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableRoom.getStyleClass().add("center");
        hboxBottom.getStyleClass().add("bottom");
        vboxRight.getStyleClass().add("right");

        // All elements in BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("border-pane");
        borderPane.setCenter(tableRoom);
        borderPane.setRight(vboxRight);
        borderPane.setBottom(hboxBottom);

        return borderPane;
    }
}
