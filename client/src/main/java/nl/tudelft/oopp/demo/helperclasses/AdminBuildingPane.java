package nl.tudelft.oopp.demo.helperclasses;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.OccasionCommunication;
import nl.tudelft.oopp.demo.controllers.AdminSceneController;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Occasion;

public class AdminBuildingPane {

    public static TableView<Building> tableBuilding;
    public static TableView<Occasion> tableHoliday;
    static ObservableList<Building> buildingData;
    private static Rectangle2D screenBounds;
    private static Button updateInfoButton = new Button("Update");
    private static Button deleteInfoButton = new Button("Delete");
    private static Button updateTimeButton = new Button("Update");
    private static Button deleteTimeButton = new Button("Delete");

    /**
     * The method below is implemented for the update button under the building section in the admin scene.
     * When the user double clicks on a specific section of a row one will be able to change the details, and
     * after changing the details one will have to press on the update button to update the database.
     */
    public static void updateBuildingButtonClicked() {
        Building building = tableBuilding.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        String success = BuildingCommunication.updateBuilding(building.getId(), building.getName(), building.getOpenTime(),
            building.getCloseTime(), building.getStreetName(), building.getStreetNumber(),
            building.getZipCode(), building.getCity());
        if (success.equals("Successful")) {
            alert.hide();
        } else {
            alert.setContentText(success);
            alert.showAndWait();
        }
    }

    /**
     * The method below is implemented for the delete button under the building section in the admin scene.
     * When the user selects a row in the building table it will be deleted from the database.
     */
    public static void deleteBuildingButtonClicked() {
        ObservableList<Building> allBuildings;
        allBuildings = tableBuilding.getItems();
        Building building = tableBuilding.getSelectionModel().getSelectedItem();

        allBuildings.remove(building);
        BuildingCommunication.removeBuilding(building.getId());
    }

    /**
     * Get the BorderPane of the Building info list.
     *
     * @return BorderPane of Building Info
     */
    public static BorderPane getBuildingInfoBP(Accordion ac) {
        screenBounds = Screen.getPrimary().getBounds();

        //Reset TableView tableBuilding
        tableBuilding = new TableView<>();
        tableBuilding.getColumns().clear();
        tableBuilding.setEditable(true);

        // Table of Buildings with Info
        // Pane Center
        TableColumn<Building, Long> idCol = new TableColumn<>("id");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.getStyleClass().setAll("first-col");

        TableColumn<Building, String> buildingCol = new TableColumn<>("Building Name");
        buildingCol.setMinWidth(100);
        buildingCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        buildingCol.setCellFactory(TextFieldTableCell.forTableColumn());
        buildingCol.setOnEditCommit((TableColumn.CellEditEvent<Building, String> t) ->
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setName(t.getNewValue()));

        TableColumn<Building, LocalTime> openTimeCol = new TableColumn<>("Open Time");
        openTimeCol.setMinWidth(100);
        openTimeCol.setCellValueFactory(new PropertyValueFactory<>("openTime"));
        openTimeCol.setCellFactory(TextFieldTableCell.forTableColumn(new TimeToStringConverter()));
        openTimeCol.setOnEditCommit((TableColumn.CellEditEvent<Building, LocalTime> t) ->
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setOpenTime(t.getNewValue()));

        TableColumn<Building, LocalTime> closeTimeCol = new TableColumn<>("Close Time");
        closeTimeCol.setMinWidth(100);
        closeTimeCol.setCellValueFactory(new PropertyValueFactory<>("closeTime"));
        closeTimeCol.setCellFactory((TextFieldTableCell.forTableColumn(new TimeToStringConverter())));
        closeTimeCol.setOnEditCommit((TableColumn.CellEditEvent<Building, LocalTime> t) ->
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setCloseTime(t.getNewValue()));

        TableColumn<Building, String> streetNameCol = new TableColumn<>("Street Name");
        streetNameCol.setMinWidth(100);
        streetNameCol.setCellValueFactory(new PropertyValueFactory<>("streetName"));
        streetNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        streetNameCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Building, String> t) ->
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setStreetName(t.getNewValue()));

        TableColumn<Building, String> streetNumCol = new TableColumn<>("Street Number");
        streetNumCol.setMinWidth(100);
        streetNumCol.setCellValueFactory(new PropertyValueFactory<>("streetNumber"));
        streetNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        streetNumCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Building, String> t) -> t.getTableView().getItems().get(
                t.getTablePosition().getRow()).setStreetNumber(t.getNewValue()));

        TableColumn<Building, String> zipCodeCol = new TableColumn<>("Zip Code");
        zipCodeCol.setMinWidth(100);
        zipCodeCol.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        zipCodeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        zipCodeCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Building, String> t) -> t.getTableView().getItems().get(
                t.getTablePosition().getRow()).setZipCode(t.getNewValue()));

        TableColumn<Building, String> cityCol = new TableColumn<>("City");
        cityCol.setMinWidth(100);
        cityCol.setCellValueFactory(new PropertyValueFactory<>("City"));
        cityCol.setCellFactory(TextFieldTableCell.forTableColumn());
        cityCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Building, String> t) -> t.getTableView().getItems().get(
                t.getTablePosition().getRow()).setCity(t.getNewValue()));
        cityCol.getStyleClass().setAll("last-col");

        buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());
        tableBuilding.setItems(buildingData);
        tableBuilding.getColumns().addAll(idCol, buildingCol, openTimeCol, closeTimeCol, streetNameCol, streetNumCol, zipCodeCol, cityCol);

        HBox hboxBottom = new HBox();
        hboxBottom.setPadding(new Insets(20, 20, 20, 0));
        hboxBottom.getChildren().setAll(deleteInfoButton, updateInfoButton);

        // VBox to add a new building
        // Pane Right
        Text buildingName = new Text("Building Name");
        Text openTime = new Text("Open Time");
        Text closeTime = new Text("Close Time");
        Text streetName = new Text("Street Name");
        Text streetNumber = new Text("Street Number");
        Text zipCode = new Text("Zip Code");
        Text city = new Text("City");


        ObservableList<LocalTime> time = generateTime();
        TextField buildingNameInput = new TextField();
        ComboBox<LocalTime> openTimeInput = new ComboBox<>();
        openTimeInput.setItems(time);
        ComboBox<LocalTime> closeTimeInput = new ComboBox<>();
        closeTimeInput.setItems(time);
        TextField streetNameInput = new TextField();
        TextField streetNumberInput = new TextField();
        TextField zipCodeInput = new TextField();
        TextField cityInput = new TextField();

        Button addButtonBuilding = new Button("Add Building");

        VBox vboxRight = new VBox(5);
        vboxRight.getChildren().addAll(buildingName, buildingNameInput, openTime, openTimeInput,
            closeTime, closeTimeInput, streetName, streetNameInput, streetNumber, streetNumberInput,
            zipCode, zipCodeInput, city, cityInput, addButtonBuilding);

        // Delete Button
        deleteInfoButton.setOnAction(e -> {
            try {
                deleteBuildingButtonClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Update Button
        updateInfoButton.setOnAction(e -> {
            try {
                updateBuildingButtonClicked();
                AdminSceneController.loadBuildingTP(ac);
                ac.setExpandedPane(AdminSceneController.buildingTP);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Add Button
        addButtonBuilding.setOnAction(e -> {
            String buildingNameInputText = buildingNameInput.getText();
            String openTimeInputText = openTimeInput.getValue().toString();
            String closeTimeInputText = closeTimeInput.getValue().toString();
            String streetNameInputText = streetNameInput.getText();
            String streetNumberInputText = streetNumberInput.getText();
            String zipCodeInputText = zipCodeInput.getText();
            String cityInputText = cityInput.getText();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            String success = BuildingCommunication.addBuilding(buildingNameInputText, LocalTime.parse(openTimeInputText), LocalTime.parse(closeTimeInputText),
                streetNameInputText, streetNumberInputText, zipCodeInputText, cityInputText);
            if (success.equals("Successful")) {
                alert.hide();
            } else {
                alert.setContentText(success);
                alert.showAndWait();
            }

            buildingNameInput.setText(null);
            openTimeInput.setValue(null);
            closeTimeInput.setValue(null);
            streetNameInput.setText(null);
            streetNumberInput.setText(null);
            zipCodeInput.setText(null);
            cityInput.setText(null);
            AdminSceneController.loadBuildingTP(ac);
            ac.setExpandedPane(AdminSceneController.buildingTP);
        });

        tableBuilding.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableBuilding.getStyleClass().add("center");
        hboxBottom.getStyleClass().add("bottom");
        vboxRight.getStyleClass().add("right");

        // All elements in BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("border-pane");
        borderPane.setCenter(tableBuilding);
        borderPane.setRight(vboxRight);
        borderPane.setBottom(hboxBottom);

        return borderPane;
    }

    /**
     * The method below is implemented for the update button under the time section in the admin scene.
     * When the user double clicks on a specific section of a row one will be able to change the details, and
     * after changing the details one will have to press on the update button to update the database.
     */
    public static void updateTimeButtonClicked() {
        Occasion occasion = tableHoliday.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        String success = OccasionCommunication.updateOccasion(occasion.getId(), occasion.getDate(), occasion.getOpenTime(),
            occasion.getCloseTime(), occasion.getBuilding().getId());
        if (success.equals("Successful")) {
            alert.hide();
        } else {
            alert.setContentText(success);
            alert.showAndWait();
        }
    }

    /**
     * The method below is implemented for the delete button under the time section in the admin scene.
     * When the user selects a row in the rooms table it will be deleted from the database.
     */
    public static void deleteTimeButtonClicked() {
        ObservableList<Occasion> allTimes;
        allTimes = tableHoliday.getItems();
        Occasion occasion = tableHoliday.getSelectionModel().getSelectedItem();

        allTimes.remove(occasion);
        OccasionCommunication.removeOccasion(occasion.getId());
    }

    /**
     * Get the BorderPane of the Building Time list.
     *
     * @return BorderPane of Building Time
     */
    public static BorderPane getBuildingTimesBP(Accordion ac) {

        // Reset TableView tableHoliday
        tableHoliday = new TableView<>();
        tableHoliday.getColumns().clear();
        tableHoliday.setEditable(true);

        // Set table of buildings with changed times
        // Center Pane
        TableColumn<Occasion, Long> idBuildingTimeCol =
            new TableColumn<>("id");
        idBuildingTimeCol.setMinWidth(100);
        idBuildingTimeCol.setCellValueFactory(
            new PropertyValueFactory<>("id"));

        TableColumn<Occasion, String> buildingNameCol =
            new TableColumn<>("Building Name");
        buildingNameCol.setMinWidth(100);
        buildingNameCol.setCellValueFactory(
            new PropertyValueFactory<>("building"));
        buildingNameCol.setCellFactory(TextFieldTableCell.<Occasion, String>forTableColumn(new BuildingToStringConverter()));
        buildingNameCol.setEditable(false);

        TableColumn<Occasion, LocalDate> dayCol =
            new TableColumn<>("Day");
        dayCol.setMinWidth(100);
        dayCol.setCellValueFactory(
            new PropertyValueFactory<>("date"));
        dayCol.setCellFactory(TextFieldTableCell.forTableColumn(new DateToStringConverter()));
        dayCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Occasion, LocalDate> t) -> t.getTableView().getItems().get(
                t.getTablePosition().getRow()).setDate(t.getNewValue()));

        TableColumn<Occasion, LocalTime> openHolidayTimeCol =
            new TableColumn<>("Open Time");
        openHolidayTimeCol.setMinWidth(100);
        openHolidayTimeCol.setCellValueFactory(
            new PropertyValueFactory<>("openTime"));
        openHolidayTimeCol.setCellFactory(TextFieldTableCell.<Occasion, String>forTableColumn((new TimeToStringConverter())));
        openHolidayTimeCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Occasion, LocalTime> t) -> {
                t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setOpenTime(t.getNewValue());
            });

        TableColumn<Occasion, LocalTime> closeHolidayTimeCol =
            new TableColumn<>("Close Time");
        closeHolidayTimeCol.setMinWidth(100);
        closeHolidayTimeCol.setCellValueFactory(
            new PropertyValueFactory<>("closeTime"));
        closeHolidayTimeCol.setCellFactory(TextFieldTableCell.<Occasion, String>forTableColumn((new TimeToStringConverter())));
        closeHolidayTimeCol.setOnEditCommit(
            (TableColumn.CellEditEvent<Occasion, LocalTime> t) -> {
                t.getTableView().getItems().get(
                    t.getTablePosition().getRow()).setCloseTime(t.getNewValue());
            });

        ObservableList<Occasion> buildingTimeData = FXCollections.observableList(OccasionCommunication.getOccasions());
        tableHoliday.setItems(buildingTimeData);
        tableHoliday.getColumns().addAll(idBuildingTimeCol, buildingNameCol, dayCol, openHolidayTimeCol, closeHolidayTimeCol);

        // VBox to delete and update rows from buidling time table
        // Bottom Pane

        // Delete button
        deleteTimeButton.setOnAction(e -> {
            try {
                deleteTimeButtonClicked();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //Update button
        updateTimeButton.setOnAction(e -> {
            try {
                updateTimeButtonClicked();
                AdminSceneController.loadBuildingTP(ac);
                ac.setExpandedPane(AdminSceneController.buildingTP);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox hboxBottom = new HBox(5);
        hboxBottom.getChildren().setAll(deleteTimeButton, updateTimeButton);

        // Adding a openTime for a building on a date
        // Right Pane

        ObservableList<Building> buildingNames = buildingData;
        ArrayList<String> buildingList = new ArrayList<>();

        for (Building name : buildingNames) {
            buildingList.add(name.getName() + ", " + name.getId());
        }
        ObservableList<String> bl = FXCollections.observableArrayList(buildingList);

        Text day = new Text("Date");
        Text openHolidayTime = new Text("Open Time");
        Text closeHolidayTime = new Text("Close Time");
        Text building = new Text("Building ID");

        ObservableList<LocalTime> time = generateTime();
        DatePicker datePicker = new DatePicker();
        ComboBox<LocalTime> openHolidayTimeInput = new ComboBox<>();
        openHolidayTimeInput.setItems(time);
        ComboBox<LocalTime> closeHolidayTimeInput = new ComboBox<>();
        closeHolidayTimeInput.setItems(time);
        TextField buildingInput = new TextField();

        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        Button addOpenTime = new Button("Add Open Time");

        choiceBox.setItems(bl);

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            String[] string = newValue.split(", ");
            buildingInput.setText(string[1]);

        });

        VBox vboxRight = new VBox(5);
        vboxRight.getChildren().addAll(day, datePicker, openHolidayTime, openHolidayTimeInput,
            closeHolidayTime, closeHolidayTimeInput, building, choiceBox, addOpenTime);

        // Add open time button
        addOpenTime.setOnAction(e -> {
            LocalDate dayInputText = (datePicker.getValue());
            LocalTime openHolidayTimeInputText = openHolidayTimeInput.getValue();
            LocalTime closeHolidayTimeInputText = closeHolidayTimeInput.getValue();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            String success = OccasionCommunication.addOccasion(dayInputText, openHolidayTimeInputText, closeHolidayTimeInputText,
                Long.parseLong(buildingInput.getText()));
            if (success.equals("Successful")) {
                alert.hide();
            } else {
                alert.setContentText(success);
                alert.showAndWait();
            }

            datePicker.setValue(null);
            openHolidayTimeInput.setValue(null);
            closeHolidayTimeInput.setValue(null);
            buildingInput.setText(null);
            choiceBox.setValue(null);
            AdminSceneController.loadBuildingTP(ac);
            ac.setExpandedPane(AdminSceneController.buildingTP);
        });

        tableHoliday.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableHoliday.getStyleClass().add("center");
        hboxBottom.getStyleClass().add("bottom");
        vboxRight.getStyleClass().add("right");

        // All elements in BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("border-pane");
        borderPane.setCenter(tableHoliday);
        borderPane.setRight(vboxRight);
        borderPane.setBottom(hboxBottom);

        return borderPane;
    }

    /**
     * The method is generating time for comboboxes.
     *
     * @return an observable list of times.
     */
    public static ObservableList<LocalTime> generateTime() {
        ArrayList<LocalTime> timeList = new ArrayList<>();
        for (LocalTime tm = LocalTime.MIN; tm.isBefore(LocalTime.parse("23:30")); tm = tm.plusMinutes(30)) {
            timeList.add(tm);
        }
        timeList.add(LocalTime.parse("23:30"));
        return FXCollections.observableArrayList(timeList);
    }

}
