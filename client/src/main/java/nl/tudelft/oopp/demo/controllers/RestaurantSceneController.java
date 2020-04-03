package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Restaurant;

public class RestaurantSceneController implements Initializable {

    private static Restaurant restaurant;
    @FXML private Pane mainPane;
    @FXML private Accordion ac;
    @FXML private Pane pane;
    @FXML private HBox hbox;

    private MainSceneController mainSceneController;
    private HamburgerMenuSceneController hamburgerMenuSceneController;

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
        List<Label> labels = new ArrayList<>();

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
                GridPane grid = new GridPane();
                ColumnConstraints colConst = new ColumnConstraints();
                colConst.setPercentWidth(100 / 2);
                grid.getColumnConstraints().add(colConst);
                grid.setVgap(4);
                //grid.setPadding(new Insets(5, 5, 5, 5));

                for (Restaurant restaurant : restaurants) {
                    System.out.println(buildingData.get(i).getName() + " " + restaurant.getName());
                }

                for (int j = 0; j < showRestaurants.size(); j++) {
                    Label label1 = new Label(showRestaurants.get(j).getName());
                    long restaurantId = showRestaurants.get(j).getId();
                    labels.add(label1);
                    Button button1 = new Button("Menu");
                    buttons.add(button1);
                    buttonRestaurant.add(showRestaurants.get(j).getId());
                    restaurant = showRestaurants.get(j);

                    grid.add(labels.get(count), 0, j);
                    grid.add(buttons.get(count), 1, j);
                    count = count + 1;

                    button1.setOnAction(e -> {
                        try {
                            System.out.println("id:" + restaurantId + " | Name:" + label1.getText());
                            VBox vbox = MenuSceneController.loadMenu(pane, restaurantId);
                            pane.getChildren().setAll(vbox);
                            ac.setPrefWidth((screenBounds.getWidth() - 400) * 0.50);
                            OrderSceneController orderSceneController = new OrderSceneController();
                            orderSceneController.clearBasket();
                            orderSceneController.setRestaurant(restaurant);
                            hamburgerMenuSceneController.openOrder();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                }
                tps[c].setText(buildingData.get(i).getName());
                tps[c].setContent(grid);
                ac.getPanes().add(tps[c]);
                c++;
            }
        }
        MenuSceneController.loadMenu(pane, -1);
        double mainPaneWidth = screenBounds.getWidth() - 400;
        mainPane.setPrefWidth(mainPaneWidth);
        hbox.setPrefWidth(mainPaneWidth);
        hbox.setPrefHeight(screenBounds.getHeight() - 200);
        ac.setPrefWidth(screenBounds.getWidth());
        pane.setPrefWidth(mainPaneWidth * 0.50);
    }

    public void setController(MainSceneController mainSceneController, HamburgerMenuSceneController hamburgerMenuSceneController){
        this.mainSceneController = mainSceneController;
        this.hamburgerMenuSceneController = hamburgerMenuSceneController;
    }
}
