package nl.tudelft.oopp.demo.helperclasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.ItemCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.controllers.AdminSceneController;
import nl.tudelft.oopp.demo.entities.*;

import java.time.LocalDate;

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
        if (ItemCommunication.updateItem(item.getId(), item.getName()).equals("Successful")) {
            alert.hide();
        } else {
            alert.setContentText(ItemCommunication.updateItem(item.getId(), item.getName()));
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
            new TableColumn<>("Room Name");
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
        hboxBottom.getChildren().setAll(deleteInfoButton, updateInfoButton);

        // adding an item scene
        BorderPane borderPaneAddItems = new BorderPane();
        VBox vboxRight = new VBox();

        Text itemName = new Text("Item Name");

        TextField itemField = new TextField();

        Button addItem = new Button("Add Item");

        vboxRight.getChildren().addAll(roomName, itemField, addItem);
        vboxRight.setSpacing(5);
        borderPaneAddItems.setTop(vboxRight);

        addItem.setOnAction(e -> {
            String itemFieldText = itemField.getText();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            if (ItemCommunication.addItem(itemFieldText).equals("Successful")) {
                alert.hide();
            } else {
                alert.setContentText(ItemCommunication.addItem(itemFieldText));
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

        // All elements in BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("border-pane");
        borderPane.setCenter(tableItems);
        borderPane.setRight(vboxRight);
        borderPane.setBottom(hboxBottom);

        return borderPane;
    }
    }
}
