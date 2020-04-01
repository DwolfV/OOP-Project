package nl.tudelft.oopp.demo.controllers;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.*;
import nl.tudelft.oopp.demo.helperclasses.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SupplySceneController implements Initializable {

    private TextField textFieldItem;
    private static TableView<SupplyReservation> tableReservedSupplies;

    @FXML
    private Accordion ac = new Accordion();;

    @FXML
    private BorderPane borderPane;

    /**
     * Loads all the content into the tables.
     * @param location  url location
     * @param resources resource bundle
     */
    @Override
    public void initialize (URL location, ResourceBundle resources) {
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

        ObservableList<SupplyReservation> reservedSupplies = FXCollections.observableList(SupplyReservationCommunication.getSupplyReservationByUserId(Authenticator.ID));
        tableReservedSupplies.setItems(reservedSupplies);
        tableReservedSupplies.getColumns().addAll(supplyNameCol, amountCol, dateCol);
        tableReservedSupplies.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableReservedSupplies.setPrefHeight(200);
        tableReservedSupplies.setPlaceholder(new Label("Currently you have made no supply reservations"));

        ObservableList<Building> buildings= FXCollections.observableList(BuildingCommunication.getBuildings());
        ObservableList<Supply> supplies = FXCollections.observableList(SupplyCommunication.getSupplies());

        Button removeReservedSupply = new Button("Remove Selected");

        // a method for the remove button
        removeReservedSupply.setOnAction(event -> {
            ObservableList<SupplyReservation> allReservedSupplies;
            allReservedSupplies = tableReservedSupplies.getItems();
            SupplyReservation supplyReservation = tableReservedSupplies.getSelectionModel().getSelectedItem();

            allReservedSupplies.remove(supplyReservation);
            SupplyReservationCommunication.removeSupplyReservation(supplyReservation.getId());
        });

        VBox veBoxDeleteAndTable = new VBox();
        veBoxDeleteAndTable.getChildren().addAll(tableReservedSupplies, removeReservedSupply);
        veBoxDeleteAndTable.setPrefWidth(400);
        veBoxDeleteAndTable.setPadding(new Insets(0,300,10,0));

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

                    textFieldItem = new TextField();
                    textFieldItem.setPromptText("quantity");
                    textFields.add(textFieldItem);

                    // stripping non-numeric from text
                    textFieldItem.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
                        if (!newValue.matches("\\d*")) {
                            textFieldItem.setText(newValue.replaceAll("[^\\d]", ""));
                        }
                    });

                    Button buttonItem = new Button("reserve");
                    buttons.add(buttonItem);

                    buttonItem.setOnAction(e -> {
                        LocalDate today = LocalDate.now();
//                        SupplyReservationCommunication.addSupplyReservation();
                    });

                    horizBox.getChildren().addAll(labelItem, labelQuantity, textFieldItem, buttonItem);
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
