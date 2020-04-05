package nl.tudelft.oopp.demo.helperclasses;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Accordion;
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
import javafx.stage.Screen;
import javafx.util.converter.IntegerStringConverter;

import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.SupplyCommunication;
import nl.tudelft.oopp.demo.controllers.AdminSceneController;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Supply;

public class AdminSupplyPane {

    public static TableView<Supply> tableSupplies;
    private static Rectangle2D screenBounds;
    private static Button updateButtonSupplies = new Button("Update");
    private static Button deleteButtonSupplies = new Button("Delete");

    /**
     * The method below is implemented for the update button under the supplies section in the admin scene.
     * When the user double clicks on a specific section of a row one will be able to change the details, and
     * after changing the details one will have to press on the update button to update the database.
     */
    public static void updateButtonSuppliesClicked() {
        Supply supply = tableSupplies.getSelectionModel().getSelectedItem();
        SupplyCommunication.updateSupply(supply.getId(), supply.getBuilding().getId(), supply.getName(), supply.getStock());
    }

    /**
     * The method below is implemented for the delete button under the supplies section in the admin scene.
     * When the user selects a row in the rooms table it will be deleted from the database.
     */
    public static void deleteButtonSuppliesClicked() {
        ObservableList<Supply> allSupplies;
        allSupplies = tableSupplies.getItems();
        Supply supply = tableSupplies.getSelectionModel().getSelectedItem();

        allSupplies.remove(supply);
        SupplyCommunication.removeSupply(supply.getId());
    }

    /**
     * Get the BorderPane of the Supply list.
     *
     * @return BorderPane of Supply Time
     */
    public static BorderPane getSupplyBP(Accordion ac) {
        screenBounds = Screen.getPrimary().getBounds();

        //Reset TableView tableSupplies
        tableSupplies = new TableView<>();
        tableSupplies.getColumns().clear();
        tableSupplies.setEditable(true);

        TableColumn<Supply, Long> idSupplyCol =
                new TableColumn<>("id");
        idSupplyCol.setMinWidth(100);
        idSupplyCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn<Supply, Building> buildingNameCol =
                new TableColumn<>("Building Name");
        buildingNameCol.setMinWidth(100);
        buildingNameCol.setCellValueFactory(
                new PropertyValueFactory<>("building"));
        buildingNameCol.setCellFactory(TextFieldTableCell.forTableColumn(new BuildingToStringConverter()));

        TableColumn<Supply, String> supplyNameCol =
                new TableColumn<>("Supply Name");
        supplyNameCol.setMinWidth(100);
        supplyNameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        supplyNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        supplyNameCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Supply, String> t) -> t.getTableView().getItems().get(
                t.getTablePosition().getRow()).setName(t.getNewValue()));

        TableColumn<Supply, Integer> supplyStockCol =
                new TableColumn<>("Stock");
        supplyStockCol.setMinWidth(100);
        supplyStockCol.setCellValueFactory(
                new PropertyValueFactory<>("stock"));
        supplyStockCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        supplyStockCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Supply, Integer> t) -> t.getTableView().getItems().get(
                t.getTablePosition().getRow()).setStock(t.getNewValue()));

        ObservableList<Supply> suppliesData = FXCollections.observableList(SupplyCommunication.getSupplies());
        tableSupplies.setItems(suppliesData);
        tableSupplies.getColumns().addAll(idSupplyCol, buildingNameCol, supplyNameCol, supplyStockCol);

        //delete button
        deleteButtonSupplies.setOnAction(e -> {
            try {
                deleteButtonSuppliesClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //update button
        updateButtonSupplies.setOnAction(e -> {
            try {
                updateButtonSuppliesClicked();
                AdminSceneController.loadSupplyTP(ac);
                ac.setExpandedPane(AdminSceneController.supplyTP);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // adding room scene
        BorderPane borderPaneAddSupplies = new BorderPane();
        VBox veBoxAddSupplies = new VBox();

        ObservableList<Building> buildingNames = FXCollections.observableList(BuildingCommunication.getBuildings());
        ArrayList<String> buildingList = new ArrayList<>();

        for (Building buildingName : buildingNames) {
            buildingList.add(buildingName.getName() + ", " + buildingName.getId());
        }
        ObservableList<String> bl = FXCollections.observableArrayList(buildingList);

        Text buildingName = new Text("Building Name");
        Text supplyName = new Text("Supply Name");
        Text stock = new Text("Stock");

        TextField buildingNameInput = new TextField();
        TextField supplyNameInput = new TextField();
        TextField stockInput = new TextField();

        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        Button addSupplies = new Button("Add Supplies");

        choiceBox.setItems(bl);

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            String[] string = newValue.split(", ");
            buildingNameInput.setText(string[1]);
        });

        veBoxAddSupplies.getChildren().addAll(buildingName, choiceBox,
                supplyName, supplyNameInput, stock, stockInput, addSupplies);
        veBoxAddSupplies.setSpacing(5);
        borderPaneAddSupplies.setTop(veBoxAddSupplies);

        addSupplies.setOnAction(e -> {
            String supplyNameInputText = supplyNameInput.getText();
            int stockInputText = Integer.parseInt(stockInput.getText());

            SupplyCommunication.addSupply(Long.parseLong(buildingNameInput.getText()), supplyNameInputText, stockInputText);

            supplyNameInput.setText(null);
            buildingNameInput.setText(null);
            stockInput.setText(null);

            choiceBox.setValue(null);
            AdminSceneController.loadSupplyTP(ac);
            ac.setExpandedPane(AdminSceneController.supplyTP);
        });

        // HBox for the buttons under the table
        HBox hoBoxAddDeleteUpdateSupplies = new HBox(5);
        hoBoxAddDeleteUpdateSupplies.getChildren().setAll(deleteButtonSupplies, updateButtonSupplies);

        // This VBox contains the table for the rooms and adding a room
        HBox hoBoxSuppliesTP = new HBox(5);
        VBox veBoxSuppliesTP = new VBox(5);

        hoBoxSuppliesTP.getChildren().addAll(tableSupplies, borderPaneAddSupplies);
        veBoxSuppliesTP.getChildren().addAll(hoBoxSuppliesTP, hoBoxAddDeleteUpdateSupplies);

        tableSupplies.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableSupplies.getStyleClass().add("center");
        hoBoxAddDeleteUpdateSupplies.getStyleClass().add("bottom");
        veBoxAddSupplies.getStyleClass().add("right");

        // All elements in BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("border-pane");
        borderPane.setCenter(tableSupplies);
        borderPane.setRight(veBoxAddSupplies);
        borderPane.setBottom(hoBoxAddDeleteUpdateSupplies);

        return borderPane;

    }
}
