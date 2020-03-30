package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import nl.tudelft.oopp.demo.communication.SupplyCommunication;
import nl.tudelft.oopp.demo.helperclasses.Supply;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;

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

        TitledPane[] tps = new TitledPane[buildings.size()];
        List<Button> buttons = new ArrayList<>();
        List<Label> labels = new ArrayList<>();
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

                for (int j = 0; j < showSupplies.size(); j++) {
                    HBox horizBox = new HBox();

                    Label labelItem = new Label(showSupplies.get(j).getName());
                    labels.add(labelItem);

                    textFieldItem = new TextField();
                    textFields.add(textFieldItem);

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
                tps[c].setText(supplies.get(i).getName());
                tps[c].setContent(vertBox);
                ac.getPanes().add(tps[c]);
                c++;
            }
            // load the accordion into the scene
            VBox box = new VBox(ac);
            borderPane.setCenter(box);
            borderPane.setPadding(new Insets(30, 5, 5, 10));
        }
    }
}
