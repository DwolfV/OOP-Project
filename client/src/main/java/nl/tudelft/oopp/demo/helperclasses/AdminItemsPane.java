package nl.tudelft.oopp.demo.helperclasses;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.EquipmentCommunication;
import nl.tudelft.oopp.demo.communication.ItemCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.controllers.AdminSceneController;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Equipment;
import nl.tudelft.oopp.demo.entities.Item;
import nl.tudelft.oopp.demo.entities.Room;

public class AdminItemsPane {

    public static TableView<Item> tableItems;
    public static TableView<Equipment> tableEquipments;
    private static Button updateItemsButton = new Button("Update");
    private static Button deleteItemsButton = new Button("Delete");
    private static Button updateEquipmentButton = new Button("Update");
    private static Button deleteEquipmentButton = new Button("Delete");

    /**
     * The method below is implemented for the update button under the items section in the admin scene.
     * When the user double clicks on a specific section of a row one will be able to change the details, and
     * after changing the details one will have to press on the update button to update the database.
     */
    public static void updateItemsButtonClicked() {
        Item item = tableItems.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        String success = ItemCommunication.updateItem(item.getId(), item.getName());
        if (success.equals("Successful")) {
            alert.hide();
        } else {
            alert.setContentText(success);
            alert.showAndWait();
        }
    }

    /**
     * The method below is implemented for the delete button under the items section in the admin scene.
     * When the user selects a row in the building table it will be deleted from the database.
     */
    public static void deleteItemsButtonClicked() {
        ObservableList<Item> allItems;
        allItems = tableItems.getItems();
        Item item = tableItems.getSelectionModel().getSelectedItem();

        allItems.remove(item);
        ItemCommunication.removeItem(item.getId());
    }

    /**
     * Get the BorderPane of the Items info list.
     * @return BorderPane of Item Info
     */
    public static BorderPane getItemsBP(Accordion ac) {
        //Reset TableView tableItems
        tableItems = new TableView<>();
        tableItems.getColumns().clear();
        tableItems.setEditable(true);

        TableColumn<Item, Long> idColItems =
            new TableColumn<>("id");
        idColItems.setMinWidth(100);
        idColItems.setCellValueFactory(
            new PropertyValueFactory<>("id"));

        TableColumn<Item, String> itemCol =
            new TableColumn<>("Item Name");
        itemCol.setMinWidth(100);
        itemCol.setCellValueFactory(
            new PropertyValueFactory<>("name"));
        itemCol.setCellFactory(TextFieldTableCell.forTableColumn());
        itemCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Item, String> t) -> t.getTableView().getItems().get(
                t.getTablePosition().getRow()).setName(t.getNewValue()));

        ObservableList<Item> itemsData = FXCollections.observableList(ItemCommunication.getItems());
        tableItems.setItems(itemsData);
        tableItems.getColumns().addAll(idColItems, itemCol);

        //delete button
        deleteItemsButton.setOnAction(e -> {
            try {
                deleteItemsButtonClicked();
                AdminSceneController.loadItemsTP(ac);
                ac.setExpandedPane(AdminSceneController.itemsTP);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //update button
        updateItemsButton.setOnAction(e -> {
            try {
                updateItemsButtonClicked();
                AdminSceneController.loadItemsTP(ac);
                ac.setExpandedPane(AdminSceneController.itemsTP);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // HBox for the buttons under the table
        HBox hboxBottom = new HBox(5);
        hboxBottom.getChildren().setAll(deleteItemsButton, updateItemsButton);

        // adding an item scene
        BorderPane borderPaneAddItems = new BorderPane();
        VBox vboxRight = new VBox();

        Text itemName = new Text("Item Name");

        TextField itemField = new TextField();

        Button addItem = new Button("Add Item");

        vboxRight.getChildren().addAll(itemName, itemField, addItem);
        vboxRight.setSpacing(5);
        borderPaneAddItems.setTop(vboxRight);

        addItem.setOnAction(e -> {
            String itemFieldText = itemField.getText();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            String success = ItemCommunication.addItem(itemFieldText);
            if (success.equals("Successful")) {
                alert.hide();
            } else {
                alert.showAndWait();
            }

            itemField.setText(null);
            AdminSceneController.loadItemsTP(ac);
            ac.setExpandedPane(AdminSceneController.itemsTP);
        });

        tableItems.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableItems.getStyleClass().add("center");
        hboxBottom.getStyleClass().add("bottom");
        vboxRight.getStyleClass().add("right");
        addItem.getStyleClass().setAll("restaurant-menu-button");
        // All elements in BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("admin-border-pane");
        borderPane.setCenter(tableItems);
        borderPane.setRight(vboxRight);
        borderPane.setBottom(hboxBottom);

        return borderPane;
    }

    /**
     * The method below is implemented for the update button under the equipment section in the admin scene.
     * When the user double clicks on a specific section of a row one will be able to change the details, and
     * after changing the details one will have to press on the update button to update the database.
     */
    public static void updateEquipmentsButtonClicked() {
        Equipment equipment = tableEquipments.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        String success = EquipmentCommunication.updateEquipmentStock(equipment.getId(), equipment.getRoom(), equipment.getItem(), equipment.getAmount());
        if (success.equals("Successful")) {
            alert.hide();
        } else {
            alert.setContentText(success);
            alert.showAndWait();
        }

    }

    /**
     * The method below is implemented for the delete button under the equipment section in the admin scene.
     * When the user selects a row in the building table it will be deleted from the database.
     */
    public static void deleteEquipmentsButtonClicked() {
        ObservableList<Equipment> allEquipments;
        allEquipments = tableEquipments.getItems();
        Equipment equipment = tableEquipments.getSelectionModel().getSelectedItem();

        allEquipments.remove(equipment);
        EquipmentCommunication.removeEquipment(equipment.getId());
    }

    /**
     * Get the BorderPane of the Equipment info list.
     * @return BorderPane of Equipment Info
     */
    public static BorderPane getEquipmentBP(Accordion ac) {
        //Reset TableView tableRoom
        tableEquipments = new TableView<>();
        tableEquipments.getColumns().clear();
        tableEquipments.setEditable(true);

        TableColumn<Equipment, Long> idColEquipment =
            new TableColumn<>("id");
        idColEquipment.setMinWidth(100);
        idColEquipment.setCellValueFactory(
            new PropertyValueFactory<>("id"));

        TableColumn<Equipment, Room> idColRoom =
            new TableColumn<>("Room Name");
        idColRoom.setMinWidth(150);
        idColRoom.setCellValueFactory(
            new PropertyValueFactory<>("room"));
        idColRoom.setCellFactory(TextFieldTableCell.forTableColumn(new RoomBuildingNameToStringConverter()));
        idColRoom.setEditable(false);

        TableColumn<Equipment, Item> itemCol =
            new TableColumn<>("Item Name");
        itemCol.setMinWidth(100);
        itemCol.setCellValueFactory(
            new PropertyValueFactory<>("item"));
        itemCol.setCellFactory(TextFieldTableCell.forTableColumn(new ItemToStringConverter()));
        itemCol.setEditable(false);

        TableColumn<Equipment, Integer> amountCol =
            new TableColumn<>("Amount");
        amountCol.setMinWidth(50);
        amountCol.setCellValueFactory(
            new PropertyValueFactory<>("amount"));
        amountCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        amountCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Equipment, Integer> t) -> t.getTableView().getItems().get(
                t.getTablePosition().getRow()).setAmount(t.getNewValue()));

        ObservableList<Equipment> equipmentData = FXCollections.observableList(EquipmentCommunication.getAllEquipment());
        tableEquipments.setItems(equipmentData);
        tableEquipments.getColumns().addAll(idColEquipment, itemCol, amountCol, idColRoom);

        //delete button
        deleteEquipmentButton.setOnAction(e -> {
            try {
                deleteEquipmentsButtonClicked();
                AdminSceneController.loadItemsTP(ac);
                ac.setExpandedPane(AdminSceneController.itemsTP);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //update button
        updateEquipmentButton.setOnAction(e -> {
            try {
                updateEquipmentsButtonClicked();
                AdminSceneController.loadItemsTP(ac);
                ac.setExpandedPane(AdminSceneController.itemsTP);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // HBox for the buttons under the table
        HBox hboxBottom = new HBox(5);
        hboxBottom.getChildren().setAll(deleteEquipmentButton, updateEquipmentButton);

        // adding an item scene
        BorderPane borderPaneAddEquipment = new BorderPane();
        VBox vboxRight = new VBox();

        Text rooms = new Text("Room Name");
        Text itemName = new Text("Item Name");
        Text quantity = new Text("Quantity");

        TextField roomField = new TextField();

        ComboBox<String> roomComboBox = new ComboBox<>();
        // get a list of buildings
        List<Building> buildingList = BuildingCommunication.getBuildings();
        // string that will be displayed
        List<String> roomString = new ArrayList<>();
        // string that we will use for comparison later
        List<String> idList = new ArrayList<>();
        for (Building building: buildingList) {
            idList.add(building.getName() + ", " + building.getId());
            List<Room> roomList = RoomCommunication.getRoomsByBuildingId(building.getId());
            if (roomList != null) {
                for (Room room : roomList) {
                    roomString.add("Building: " + building.getName() + " Room: " + room.getName());
                }
            }
        }
        ObservableList<String> roomStringObservableList = FXCollections.observableList(roomString);
        roomComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            roomField.setText(newValue);
        });
        roomComboBox.setItems(roomStringObservableList);

        TextField quantityField = new TextField();

        List<Item> itemArrayList = ItemCommunication.getItems();
        List<String> itemString = new ArrayList<>();
        for (Item item: itemArrayList) {
            itemString.add(item.getName());
        }
        ObservableList<String> items = FXCollections.observableList(itemString);

        ComboBox<String> itemComboBox = new ComboBox<>();
        itemComboBox.setItems(items);

        Button addEquipment = new Button("Add Equipment");

        vboxRight.getChildren().addAll(itemName, itemComboBox, quantity, quantityField, rooms, roomField, roomComboBox, addEquipment);
        vboxRight.setSpacing(5);
        borderPaneAddEquipment.setTop(vboxRight);

        addEquipment.setOnAction(e -> {
            String pickedItem = itemComboBox.getValue();
            String quantityFieldText = (quantityField.getText());
            String pickedRoom = roomField.getText();
            // get the id of the building by comparing the selected name and the names inside the list that contains the ids
            String buildingId = null;
            String buildingName = pickedRoom.split(" ")[1];
            for (String buildingNameId: idList) {
                if (buildingNameId.split(", ")[0].equals(buildingName)) {
                    buildingId = buildingNameId.split(", ")[1];
                }
            }
            //get all the rooms by building id and look for the room that has the same name as the selected one
            Long id = Long.parseLong(buildingId);
            Room room = null;
            for (Room r : RoomCommunication.getRoomsByBuildingId(id)) {
                if (r.getName().equals(pickedRoom.split(" ")[3])) {
                    room = r;
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            String success = EquipmentCommunication.addEquipmentToRoom(room, ItemCommunication.getItemByName(pickedItem),
                Integer.parseInt(quantityFieldText));
            if (success.equals("Successful")) {
                alert.hide();
            } else {
                alert.setContentText(success);
                alert.showAndWait();
            }

            itemComboBox.setValue(null);
            quantityField.setText(null);
            roomComboBox.setValue(null);
            AdminSceneController.loadItemsTP(ac);
            ac.setExpandedPane(AdminSceneController.itemsTP);
        });

        tableEquipments.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableEquipments.getStyleClass().add("center");
        hboxBottom.getStyleClass().add("bottom");
        vboxRight.getStyleClass().add("right");
        deleteEquipmentButton.getStyleClass().setAll("restaurant-menu-button");
        deleteItemsButton.getStyleClass().setAll("restaurant-menu-button");
        updateEquipmentButton.getStyleClass().setAll("restaurant-menu-button");
        updateItemsButton.getStyleClass().setAll("restaurant-menu-button");
        addEquipment.getStyleClass().setAll("restaurant-menu-button");

        // All elements in BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("border-pane");
        borderPane.setCenter(tableEquipments);
        borderPane.setRight(vboxRight);
        borderPane.setBottom(hboxBottom);

        return borderPane;
    }
}
