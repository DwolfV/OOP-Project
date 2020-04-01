package nl.tudelft.oopp.demo.controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.DishCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantDishCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Dish;
import nl.tudelft.oopp.demo.helperclasses.Restaurant;
import nl.tudelft.oopp.demo.helperclasses.RestaurantDish;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuSceneController implements Initializable {

    private static TableView tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public static void loadDishes() {
        // Get list of dishes and get Restaurant 249
        Restaurant restaurant = RestaurantCommunication.getRestaurantById(249);
        // Add all dishes to restaurant 249
        for(int i = 0; i < 10; i++) {
            DishCommunication.addDish("name" + i, "desc of [name" + i + "]", "food", 10 + i);
            List<Dish> dishes = DishCommunication.getDishes();
            Dish dish = dishes.get(i);
            RestaurantDishCommunication.addLinkRestaurantDish(dish, restaurant);
        }

        // Get list of all dishes
        List<Dish> dishes = DishCommunication.getDishes();
        System.out.println("DishList: " + dishes);
        // Get list of all dishes in restaurant 249
        List<Dish> resDishes = RestaurantDishCommunication.getDishesByRestaurant(249);
        System.out.println("RestaurantDishList: " + RestaurantDishCommunication.getDishesByRestaurant(249));
    }

    public static VBox loadMenu(Pane pane, long restaurantId) {
        tableView = new TableView();

        // Name Column
        TableColumn<Dish, String> nameCol = new TableColumn<>("Course");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Dish, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setName(t.getNewValue()));
        // Description Column
        TableColumn<Dish, String> descCol = new TableColumn<>();
        descCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
        descCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Dish, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setDescription(t.getNewValue()));
        // Price Column
        TableColumn<Dish, String> priceCol = new TableColumn<>("price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn());
        priceCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Dish, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setPrice(Float.parseFloat(t.getNewValue())));

        // Set Columns
        tableView.getColumns().addAll(nameCol, descCol, priceCol);
        List<Dish> dishList = RestaurantDishCommunication.getDishesByRestaurant(restaurantId);
        ObservableList<Dish> dishObservableList = FXCollections.observableArrayList(dishList);
        tableView.setItems(dishObservableList);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(tableView);
        return vbox;
    }
}
