package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Restaurant;
import nl.tudelft.oopp.demo.helperclasses.TimeToStringConverter;

public class RestaurantSceneController implements Initializable {

    @FXML private Pane mainPane;
    @FXML private Accordion ac;
    @FXML private Pane pane;
    @FXML private HBox hbox;

    private static Rectangle2D screenBounds;
    public static List<Long> buttonRestaurant;

    /**
     * Loads all the content into the tables.
     * @param location  url location
     * @param resources resource bundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        screenBounds = Screen.getPrimary().getBounds();
        ObservableList<Building> buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());
        ObservableList<Restaurant> restaurants = FXCollections.observableList(RestaurantCommunication.getRestaurants());

        TitledPane[] tps = new TitledPane[buildingData.size()];
        List<Button> buttons = new ArrayList<>();
        buttonRestaurant = new ArrayList<>();
        List<Label> labels;

        // count - for lists, c - for tps
        int count = 0;
        int c = 0;

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
                VBox table = new VBox();
                //grid.setPadding(new Insets(5, 5, 5, 5));

                for (Restaurant restaurant : restaurants) {
                    System.out.println(buildingData.get(i).getName() + " " + restaurant.getName());
                }

                TimeToStringConverter timeToString = new TimeToStringConverter();
                for (int j = 0; j < showRestaurants.size(); j++) {
                    labels = new ArrayList<>();
                    Label resName = new Label(showRestaurants.get(j).getName());
                    labels.add(resName);
                    Label resOpen = new Label("Opens: " + timeToString.toString(showRestaurants.get(j).getTimeOpen()));
                    labels.add(resOpen);
                    Label resClose = new Label("Closes: " + timeToString.toString(showRestaurants.get(j).getTimeClose()));
                    labels.add(resClose);


                    Button button1 = new Button("Menu");
                    button1.getStyleClass().setAll("restaurant-menu-button");
                    buttons.add(button1);
                    buttonRestaurant.add(showRestaurants.get(j).getId());

                    GridPane grid = new GridPane();
                    ColumnConstraints constraint1 = new ColumnConstraints();
                    constraint1.setPercentWidth(100/3.5);
                    ColumnConstraints constraint2 = new ColumnConstraints();
                    constraint2.setPercentWidth(100/3.5);
                    ColumnConstraints constraint3 = new ColumnConstraints();
                    constraint3.setPercentWidth(100/3.5);
                    grid.getColumnConstraints().setAll(
                            constraint1,
                            constraint2,
                            constraint3
                    );
                    grid.setVgap(10);
                    grid.add(labels.get(0), 0, j);
                    grid.add(labels.get(1), 1, j);
                    grid.add(labels.get(2), 2, j);
                    grid.add(buttons.get(count), 3, j);
                    buttons.get(count).setAlignment(Pos.CENTER_RIGHT);
                    count = count + 1;

                    SplitPane splitPane = new SplitPane();
                    splitPane.getStyleClass().add("restaurant-split-pane");
                    table.getChildren().add(grid);
                    table.getChildren().add(splitPane);

                    long restaurantId = showRestaurants.get(j).getId();
                    button1.setOnAction(e -> {
                        try {
                            System.out.println("id:" + restaurantId + " | Name:" + resName.getText());
                            VBox vbox = MenuSceneController.loadMenu(restaurantId);
                            ac.setPrefWidth((screenBounds.getWidth() - 400) * 0.50);
                            hbox.getChildren().setAll(ac, vbox);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                }
                Label titledPaneTitle = new Label(buildingData.get(i).getName());
                tps[c].setText(buildingData.get(i).getName());
                tps[c].setContent(table);
                ac.getPanes().add(tps[c]);
                c++;
            }
        }
        //MenuSceneController.loadMenu(-1);
        double mainPaneWidth = screenBounds.getWidth() - 400;
        //mainPane.setPrefWidth(mainPaneWidth);
        hbox.setPrefWidth(mainPaneWidth);
        hbox.setPrefHeight(screenBounds.getHeight() - 200);
        ac.setPrefWidth(screenBounds.getWidth());
        pane.setPrefWidth(mainPaneWidth * 0.50);
    }
}
