package nl.tudelft.oopp.demo.helperclasses;

import java.time.LocalTime;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.controllers.AdminSceneController;

public class AdminRestaurantPane {

    private static TableView<Restaurant> tableRestaurant;
    private static Button updateRestaurant = new Button("Update");
    private static Button deleteRestaurant = new Button("Delete");

    /**
     * The method below is implemented for the update button under the restaurant section in the admin scene.
     * When the user double clicks on a specific section of a row one will be able to change the details, and
     * after changing the details one will have to press on the update button to update the database.
     */
    public static void updateButtonRestaurantClicked() {
        Restaurant restaurant = tableRestaurant.getSelectionModel().getSelectedItem();
        RestaurantCommunication.updateRestaurant(restaurant.getId(), restaurant.getName(), restaurant.getBuilding().getId(), restaurant.getTimeClose(), restaurant.getTimeOpen());
    }

    /**
     * The method below is implemented for the delete button under the restaurant section in the admin scene.
     * When the user selects a row in the rooms table it will be deleted from the database.
     */
    public static void deleteButtonRestaurantClicked() {
        ObservableList<Restaurant> allRestaurants;
        allRestaurants = tableRestaurant.getItems();
        Restaurant restaurant = tableRestaurant.getSelectionModel().getSelectedItem();

        allRestaurants.remove(restaurant);
        RestaurantCommunication.removeRestaurant(restaurant.getId());
    }

    /**
     * Get the BorderPane of the Building info list.
     * @return BorderPane of Building Info
     */
    public static BorderPane getRestaurantBP(Accordion ac) {
        //Reset TableView tableRestaurant
        tableRestaurant = new TableView<>();
        tableRestaurant.getColumns().clear();
        tableRestaurant.setEditable(true);

        TableColumn<Restaurant, Long> idRestaurantCol =
                new TableColumn<>("id");
        idRestaurantCol.setMinWidth(100);
        idRestaurantCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn<Restaurant, String> restaurantNameCol =
                new TableColumn<>("Restaurant Name");
        restaurantNameCol.setMinWidth(100);
        restaurantNameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        restaurantNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        restaurantNameCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Restaurant, String> t) -> t.getTableView().getItems().get(
                t.getTablePosition().getRow()).setName(t.getNewValue()));

        TableColumn<Restaurant, Building> buildingNameRestaurantCol =
                new TableColumn<>("Building Name");
        buildingNameRestaurantCol.setMinWidth(100);
        buildingNameRestaurantCol.setCellValueFactory(
                new PropertyValueFactory<>("building"));
        buildingNameRestaurantCol.setCellFactory(TextFieldTableCell.<Restaurant, String>forTableColumn(new BuildingToStringConverter()));
        buildingNameRestaurantCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Restaurant, Building> t) -> t.getTableView().getItems().get(
                t.getTablePosition().getRow()).setBuilding(t.getNewValue()));

        TableColumn<Restaurant, LocalTime> timeOpenCol =
                new TableColumn<>("Opening Time");
        timeOpenCol.setMinWidth(100);
        timeOpenCol.setCellValueFactory(
                new PropertyValueFactory<>("timeOpen"));
        timeOpenCol.setCellFactory(TextFieldTableCell.<Restaurant, String>forTableColumn((new TimeToStringConverter())));
        timeOpenCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Restaurant, LocalTime> t) -> {
                t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setTimeOpen(t.getNewValue());
            });

        TableColumn<Restaurant, LocalTime> timeCloseCol =
                new TableColumn<>("Closing Time");
        timeCloseCol.setMinWidth(100);
        timeCloseCol.setCellValueFactory(
                new PropertyValueFactory<>("timeClose"));
        timeCloseCol.setCellFactory(TextFieldTableCell.<Restaurant, String>forTableColumn((new TimeToStringConverter())));
        timeCloseCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Restaurant, LocalTime> t) -> {
                t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setTimeClose(t.getNewValue());
            });
        buildingNameRestaurantCol.setOnEditCommit((TableColumn.CellEditEvent<Restaurant, Building> t) ->
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setBuilding(t.getNewValue()));

        ObservableList<Restaurant> restaurantData = FXCollections.observableList(RestaurantCommunication.getRestaurants());
        tableRestaurant.setItems(restaurantData);
        tableRestaurant.getColumns().addAll(idRestaurantCol, restaurantNameCol, buildingNameRestaurantCol, timeOpenCol, timeCloseCol);

        //delete button
        deleteRestaurant.setOnAction(e -> {
            try {
                deleteButtonRestaurantClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //update button
        updateRestaurant.setOnAction(e -> {
            try {
                updateButtonRestaurantClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        ObservableList<Building> buildingNames = AdminBuildingPane.buildingData;
        ArrayList<String> buildingList = new ArrayList<>();

        for (Building name : buildingNames) {
            buildingList.add(name.getName() + ", " + name.getId());
        }
        ObservableList<String> bl = FXCollections.observableArrayList(buildingList);

        Text restaurantName = new Text("Restaurant Name");
        Text buildingName = new Text("Building Name");
        Text openingTime = new Text("Opening Time");
        Text closingTime = new Text("Closing Time");

        TextField restaurantNameInput = new TextField();
        TextField buildingNameInput = new TextField();
        TextField openingTimeInput = new TextField();
        TextField closingTimeInput = new TextField();

        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        Button addRestaurant = new Button("Add Restaurant");

        choiceBox.setItems(bl);

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            String[] string = newValue.split(", ");
            buildingNameInput.setText(string[1]);
        });

        VBox vboxRight = new VBox(5);
        vboxRight.getChildren().addAll(restaurantName, restaurantNameInput,
            buildingName, choiceBox, openingTime, openingTimeInput,
            closingTime, closingTimeInput, addRestaurant);

        addRestaurant.setOnAction(e -> {
            String restaurantNameInputText = restaurantNameInput.getText();
            LocalTime openingTimeInputText = LocalTime.parse(openingTimeInput.getText());
            LocalTime closingTimeInputText = LocalTime.parse(closingTimeInput.getText());

            RestaurantCommunication.addRestaurant(restaurantNameInputText, Long.parseLong(buildingNameInput.getText()), closingTimeInputText, openingTimeInputText);

            restaurantNameInput.setText(null);
            openingTimeInput.setText(null);
            closingTimeInput.setText(null);
            buildingNameInput.setText(null);

            choiceBox.setValue(null);
            AdminSceneController.loadAdminScene(ac);
            ac.setExpandedPane(AdminSceneController.restaurantTP);
        });

        // HBox for the buttons under the table
        HBox hboxBottom = new HBox(5);
        hboxBottom.getChildren().setAll(deleteRestaurant, updateRestaurant);

        tableRestaurant.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableRestaurant.getStyleClass().add("center");
        hboxBottom.getStyleClass().add("bottom");
        vboxRight.getStyleClass().add("right");

        // All elements in BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("border-pane");
        borderPane.setCenter(tableRestaurant);
        borderPane.setRight(vboxRight);
        borderPane.setBottom(hboxBottom);

        return borderPane;
    }
}
