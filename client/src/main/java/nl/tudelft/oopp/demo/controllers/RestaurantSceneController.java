package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;

public class RestaurantSceneController implements Initializable {

    @FXML
    private Accordion ac;

    /**
     * Loads all the content into the tables.
     * @param location  url location
     * @param resources resource bundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Building> buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());
        ObservableList<Restaurant> restaurants = FXCollections.observableList(RestaurantCommunication.getRestaurants());

        TitledPane[] tps = new TitledPane[buildingData.size()];
        List<Button> buttons = new ArrayList<>();
        List<Label> labels = new ArrayList<>();

        // count - for lists, c - for tps
        int count = 0;
        int c = 0;

        // load the scene
        //try {
        //    BorderPane rootScene = FXMLLoader.load(getClass().getResource("/restaurantsScene.fxml"));
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        //ac.getPanes().setAll()

        // fill the accordion
        for (int i = 0; i < buildingData.size(); i++) {

            //Look for restaurants for the building i;
            ObservableList<Restaurant> showRestaurants = FXCollections.observableArrayList();
            for (int k = 0; k < restaurants.size(); k++) {
                if (restaurants.get(k).getBuilding().getName().equals(buildingData.get(i).getName())) {
                    showRestaurants.add(restaurants.get(k));
                }
            }

            //if there are restaurants for the building i - show them;
            if (showRestaurants.size() != 0) {
                tps[c] = new TitledPane();
                GridPane grid = new GridPane();
                ColumnConstraints colConst = new ColumnConstraints();
                colConst.setPercentWidth(100 / 2);
                grid.getColumnConstraints().add(colConst);
                grid.setVgap(4);
                grid.setPadding(new Insets(5, 5, 5, 5));

                for (Restaurant restaurant : restaurants) {
                    System.out.println(buildingData.get(i).getName() + " " + restaurant.getName());
                }

                for (int j = 0; j < showRestaurants.size(); j++) {
                    Label label1 = new Label(showRestaurants.get(j).getName());
                    labels.add(label1);
                    Button button1 = new Button("Menu");
                    buttons.add(button1);

                    grid.add(labels.get(count), 0, j);
                    grid.add(buttons.get(count), 1, j);
                    count = count + 1;
                }
                tps[c].setText(buildingData.get(i).getName());
                tps[c].setContent(grid);
                ac.getPanes().add(tps[c]);
                c++;
            }
        }
    }
}
