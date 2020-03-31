package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import nl.tudelft.oopp.demo.communication.Authenticator;
import nl.tudelft.oopp.demo.communication.SupplyCommunication;
import nl.tudelft.oopp.demo.communication.SupplyReservationCommunication;
import nl.tudelft.oopp.demo.helperclasses.*;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;

public class SupplySceneController implements Initializable {

    private TextField textFieldItem;

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
        ObservableList<Building> buildings= FXCollections.observableList(BuildingCommunication.getBuildings());
        ObservableList<Supply> supplies = FXCollections.observableList(SupplyCommunication.getSupplies());

        ObservableList<SupplyReservation> reservedSupplies = FXCollections.observableList(SupplyReservationCommunication.getSupplyReservationByUserId(Authenticator.ID));

        VBox veBoxDeleteAndList = new VBox();

        // This is a listView to list all the items, I am not sure if this is the best.
        ListView<SupplyReservation> reservedSuppliesList = new ListView<>();
        Button removeReservedSupply = new Button("Remove Selected");

        reservedSuppliesList.setItems(reservedSupplies);

        // a method for the remove button
        removeReservedSupply.setOnAction(event -> {
            final int selectedIdx = reservedSuppliesList.getSelectionModel().getSelectedIndex();
            if (selectedIdx != -1) {

                final int newSelectedIdx =
                    (selectedIdx == reservedSuppliesList.getItems().size() - 1)
                        ? selectedIdx - 1
                        : selectedIdx;

                reservedSuppliesList.getItems().remove(selectedIdx);
                reservedSuppliesList.getSelectionModel().select(newSelectedIdx);
            }
        });

        veBoxDeleteAndList.getChildren().addAll(reservedSuppliesList, removeReservedSupply);

        TitledPane[] tps = new TitledPane[buildings.size()];
        List<Button> buttons = new ArrayList<>();
        List<TextField> textFields = new ArrayList<>();

        //c - for tps
        int c = 0;
        int count = 0;

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

                    horizBox.getChildren().addAll(labelItem, textFieldItem, buttonItem);
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
            VBox box = new VBox(veBoxDeleteAndList, ac);
            box.setSpacing(20);
            borderPane.setCenter(box);
            borderPane.setPadding(new Insets(30, 5, 5, 10));
        }
    }
}
