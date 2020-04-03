package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.converter.FloatStringConverter;
import nl.tudelft.oopp.demo.communication.RestaurantDishCommunication;
import nl.tudelft.oopp.demo.helperclasses.Dish;

public class MenuSceneController implements Initializable {

    public static TableView<Dish> tableView;
    private static Rectangle2D screenBounds;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Load the menu in a table.
     * @param pane Pane it should be loaded into
     * @param restaurantId Id of the restaurant to get the menu from
     * @return A VBox with a table inside
     */
    public static VBox loadMenu(Pane pane, long restaurantId) {
        tableView = new TableView<Dish>();
        tableView.getStyleClass().setAll("restaurant-menu");

        // Name Column
        TableColumn<Dish, String> nameCol = new TableColumn<>("Course");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Dish, String> t) -> t.getTableView().getItems().get(
                t.getTablePosition().getRow()).setName(t.getNewValue()));
        // Description Column
        TableColumn<Dish, String> descCol = new TableColumn<>();
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Dish, String> t) -> t.getTableView().getItems().get(
                t.getTablePosition().getRow()).setDescription(t.getNewValue()));
        // Price Column
        TableColumn<Dish, Float> priceCol = new TableColumn<>("price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        priceCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Dish, Float> t) -> t.getTableView().getItems().get(
                t.getTablePosition().getRow()).setPrice(t.getNewValue()));
        priceCol.getStyleClass().add("price-col");

        // Set styleClass to rows
        tableView.setRowFactory(tv -> {
            TableRow<Dish> row = new TableRow<Dish>();
            row.getStyleClass().setAll("restaurant-menu-row");
            return row;
            }
        );

        // Set Columns
        tableView.getColumns().addAll(nameCol, descCol, priceCol);
        List<Dish> dishList = RestaurantDishCommunication.getDishesByRestaurant(restaurantId);
        ObservableList<Dish> dishObservableList = FXCollections.observableArrayList(dishList);
        tableView.setItems(dishObservableList);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(tableView);
        screenBounds = Screen.getPrimary().getBounds();
        tableView.setPrefWidth((screenBounds.getWidth() - 400) / 2);
        tableView.setPrefHeight(screenBounds.getHeight() - 200);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return vbox;
    }
}
