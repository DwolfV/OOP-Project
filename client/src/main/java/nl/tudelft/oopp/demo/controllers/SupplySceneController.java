package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.*;
import nl.tudelft.oopp.demo.entities.*;
import nl.tudelft.oopp.demo.helperclasses.BuildingToStringConverter;
import nl.tudelft.oopp.demo.helperclasses.RoomBuildingNameToStringConverter;
import nl.tudelft.oopp.demo.helperclasses.SupplyReservationRoomToStringConverter;
import nl.tudelft.oopp.demo.helperclasses.SupplyToStringConverter;

public class SupplySceneController implements Initializable {

    private static TableView<SupplyReservation> tableReservedSupplies;
    private static VBox veBoxDeleteAndTable;
    @FXML
    private Accordion ac = new Accordion();
    @FXML
    private BorderPane borderPane;

    /**
     * Loads all the content into the tables.
     * @param location  url location
     * @param resources resource bundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadReservedSupply();
        loadSuppliesAccordion();
    }

    /**
     * The method is used to set up the reserved supply of a user.
     */
    public void loadReservedSupply() {
        //Reset TableView tableReservedSupplies
        tableReservedSupplies = new TableView<>();
        tableReservedSupplies.getColumns().clear();
        tableReservedSupplies.setEditable(true);

        TableColumn<SupplyReservation, Long> supplyNameCol =
            new TableColumn<>("Supply");
        supplyNameCol.setMinWidth(100);
        supplyNameCol.setCellValueFactory(
            new PropertyValueFactory<>("supply"));
        supplyNameCol.setCellFactory(TextFieldTableCell.forTableColumn(new SupplyToStringConverter()));
        supplyNameCol.setEditable(false);

        TableColumn<SupplyReservation, Integer> amountCol =
            new TableColumn<>("Amount");
        amountCol.setMinWidth(100);
        amountCol.setCellValueFactory(
            new PropertyValueFactory<>("amount"));

        TableColumn<SupplyReservation, LocalDate> dateCol =
            new TableColumn<>("Order Date");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(
            new PropertyValueFactory<>("date"));

        TableColumn<SupplyReservation, Supply> buildingRoomCol =
            new TableColumn<>("Building Name");
        buildingRoomCol.setMinWidth(100);
        buildingRoomCol.setCellValueFactory(
            new PropertyValueFactory<>("supply"));
        buildingRoomCol.setCellFactory(TextFieldTableCell.forTableColumn(new SupplyReservationRoomToStringConverter()));
        buildingRoomCol.setEditable(false);

        ObservableList<SupplyReservation> reservedSupplies = FXCollections.observableList(SupplyReservationCommunication.getSupplyReservationByUserId(Authenticator.ID));
        tableReservedSupplies.setItems(reservedSupplies);
        tableReservedSupplies.getColumns().addAll(supplyNameCol, amountCol, dateCol, buildingRoomCol);
        tableReservedSupplies.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableReservedSupplies.setPrefHeight(200);
        tableReservedSupplies.setPlaceholder(new Label("Currently you have made no supply reservations"));

        Button removeReservedSupply = new Button("Remove Selected");

        // a method for the remove button
        removeReservedSupply.setOnAction(event -> {
            ObservableList<SupplyReservation> allReservedSupplies;
            allReservedSupplies = tableReservedSupplies.getItems();
            SupplyReservation supplyReservation = tableReservedSupplies.getSelectionModel().getSelectedItem();

            allReservedSupplies.remove(supplyReservation);
            SupplyReservationCommunication.removeSupplyReservation(supplyReservation.getId());

            ac.getPanes().clear();
            loadSuppliesAccordion();
            loadReservedSupply();
        });

        veBoxDeleteAndTable = new VBox();
        veBoxDeleteAndTable.getChildren().addAll(tableReservedSupplies, removeReservedSupply);
        veBoxDeleteAndTable.setPrefWidth(400);
        veBoxDeleteAndTable.setPadding(new Insets(0,300,10,0));
    }

    /**
     * The method is used to set up the supplies the user can reserve.
     */
    public void loadSuppliesAccordion() {
        ObservableList<Building> buildings = FXCollections.observableList(BuildingCommunication.getBuildings());
        ObservableList<Supply> supplies = FXCollections.observableList(SupplyCommunication.getSupplies());

        TitledPane[] tps = new TitledPane[buildings.size()];
        List<Button> buttons = new ArrayList<>();
        List<TextField> textFields = new ArrayList<>();

        //c - for tps
        int c = 0;

        // fill the accordion
        for (int i = 0; i < buildings.size(); i++) {

            //Look for items for the building i;
            ObservableList<Supply> showSupplies = FXCollections.observableArrayList();
            for (int k = 0; k < supplies.size(); k++) {
                if (supplies.get(k).getBuilding().getName().equals(buildings.get(i).getName())) {
                    showSupplies.add(supplies.get(k));
                }
            }

            //if there are items for the building i - show them;
            if (showSupplies.size() != 0) {
                VBox vertBox = new VBox();
                tps[c] = new TitledPane();

                for (Supply supplies1 : supplies) {
                    System.out.println(buildings.get(i).getName() + " " + supplies1.getName());
                }

                for (int j = 0; j < showSupplies.size(); j++) {
                    HBox horizBox = new HBox();

                    Label labelItem = new Label(showSupplies.get(j).getName());
                    Label labelQuantity = new Label("Quantity: " + showSupplies.get(j).getStock());

                    TextField textFieldItem = new TextField();
                    textFieldItem.setPromptText("quantity");
                    textFields.add(textFieldItem);

                    Button reserveSupplyButton = new Button("reserve");
                    buttons.add(reserveSupplyButton);

                    long supplyID = showSupplies.get(j).getId();

                    reserveSupplyButton.setOnAction(e -> {
                        LocalDate today = LocalDate.now();
                        int amount = Integer.parseInt(textFieldItem.getText());

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText(null);
                        String success = SupplyReservationCommunication.addSupplyReservation(today, amount, supplyID);
                        if (success.equals("Successful")) {
                            alert.hide();
                        } else {
                            alert.showAndWait();
                        }

                        textFieldItem.setText(null);

                        loadReservedSupply();
                        ac.getPanes().clear();
                        loadSuppliesAccordion();
                    });

                    HBox.setHgrow(labelItem, Priority.ALWAYS);
                    HBox.setHgrow(labelQuantity, Priority.ALWAYS);
                    HBox.setHgrow(textFieldItem, Priority.ALWAYS);
                    HBox.setHgrow(reserveSupplyButton, Priority.ALWAYS);

                    labelItem.setMinWidth(100);
                    labelQuantity.setMinWidth(100);
                    textFieldItem.setMinWidth(75);
                    textFieldItem.setMaxWidth(75);
                    reserveSupplyButton.setMinWidth(75);

                    horizBox.getChildren().addAll(labelItem, labelQuantity, textFieldItem, reserveSupplyButton);
                    horizBox.setSpacing(150);
                    horizBox.setStyle("-fx-padding: 8;" + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;" + "-fx-border-color: lightgrey;");
                    vertBox.getChildren().add(horizBox);
                }
                tps[c].setText(buildings.get(i).getName());
                tps[c].setContent(vertBox);
                ac.getPanes().add(tps[c]);
                c++;
            }
            // load the accordion into the scene
            VBox box = new VBox(veBoxDeleteAndTable, ac);
            box.setSpacing(20);
            borderPane.setCenter(box);
            borderPane.setPadding(new Insets(30, 5, 5, 10));
        }
    }

}
