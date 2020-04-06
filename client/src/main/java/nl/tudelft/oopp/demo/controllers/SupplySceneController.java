package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import nl.tudelft.oopp.demo.communication.Authenticator;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.SupplyCommunication;
import nl.tudelft.oopp.demo.communication.SupplyReservationCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Supply;
import nl.tudelft.oopp.demo.entities.SupplyReservation;
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
     *
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
        tableReservedSupplies.getStyleClass().setAll("restaurant-menu");
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
        removeReservedSupply.getStyleClass().setAll("restaurant-menu-button");

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

        // Set styleClass to rows
        tableReservedSupplies.setRowFactory(tv -> {
                TableRow<SupplyReservation> row = new TableRow<>();
                row.getStyleClass().setAll("restaurant-menu-row");
                return row;
            }
        );

        veBoxDeleteAndTable = new VBox();
        veBoxDeleteAndTable.setSpacing(10);
        veBoxDeleteAndTable.getChildren().addAll(tableReservedSupplies, removeReservedSupply);
    }

    /**
     * The method is used to set up the supplies the user can reserve.
     */
    public void loadSuppliesAccordion() {
        ObservableList<Building> buildings = FXCollections.observableList(BuildingCommunication.getBuildings());
        ObservableList<Supply> supplies = FXCollections.observableList(SupplyCommunication.getSupplies());

        TitledPane[] tps = new TitledPane[buildings.size()];

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

                    // Name Label
                    Label labelItem = new Label(showSupplies.get(j).getName());
                    // Quantity Label
                    Label labelQuantity = new Label("Quantity: " + showSupplies.get(j).getStock());
                    // Quantity Input
                    TextField textFieldItem = new TextField();
                    textFieldItem.setPromptText("quantity");
                    // Reserve Button
                    Button reserveSupplyButton = new Button("reserve");
                    reserveSupplyButton.getStyleClass().setAll("restaurant-menu-button");

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
                            alert.setContentText(success);
                            alert.showAndWait();
                        }

                        textFieldItem.setText(null);

                        loadReservedSupply();
                        ac.getPanes().clear();
                        loadSuppliesAccordion();
                    });

                    GridPane grid = new GridPane();
                    ColumnConstraints constraint1 = new ColumnConstraints();
                    constraint1.setPercentWidth(100 / 4);
                    ColumnConstraints constraint2 = new ColumnConstraints();
                    constraint2.setPercentWidth(100 / 4);
                    ColumnConstraints constraint3 = new ColumnConstraints();
                    constraint3.setPercentWidth(100 / 4);
                    ColumnConstraints constraint4 = new ColumnConstraints();
                    constraint4.setPercentWidth(100 / 4);
                    grid.getColumnConstraints().setAll(
                            constraint1,
                            constraint2,
                            constraint3,
                            constraint4
                    );
                    grid.add(labelItem, 0, j);
                    grid.add(labelQuantity, 1, j);
                    grid.add(textFieldItem, 2, j);
                    grid.add(reserveSupplyButton, 3, j);
                    reserveSupplyButton.setAlignment(Pos.CENTER_RIGHT);

                    SplitPane splitPane = new SplitPane();
                    splitPane.getStyleClass().add("restaurant-split-pane");

                    vertBox.getChildren().add(grid);
                    vertBox.getChildren().add(splitPane);
                }
                vertBox.setSpacing(5);
                tps[c].setText(buildings.get(i).getName());
                tps[c].setContent(vertBox);
                ac.getPanes().add(tps[c]);
                c++;
            }
            // load the accordion into the scene
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            ac.setPrefWidth(screenBounds.getWidth()-460);
            ac.setPadding(new Insets(0,0,20,0));

            ScrollPane scrollPane = new ScrollPane(ac);
            scrollPane.setMaxHeight(400);
            scrollPane.setPadding(new Insets(0,0,20,0));

            VBox box = new VBox(veBoxDeleteAndTable, scrollPane);
            box.setSpacing(20);

            borderPane.setCenter(box);
            borderPane.setPadding(new Insets(20, 20, 20, 20));
        }
    }

}
