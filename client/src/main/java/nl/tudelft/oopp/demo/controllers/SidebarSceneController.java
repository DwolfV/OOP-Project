package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import nl.tudelft.oopp.demo.communication.ItemCommunication;
import nl.tudelft.oopp.demo.helperclasses.Item;

import static java.time.LocalDate.*;
import static java.util.Calendar.MONDAY;

public class SidebarSceneController implements Initializable {

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public DatePicker dp;
    @FXML
    public Button searchId;
    @FXML
    public TextField capacityId;
    @FXML
    public CheckBox whiteboardId;
    @FXML
    public CheckBox tableId;
    @FXML
    public CheckBox tvId;
    @FXML
    public CheckBox projectorId;
    @FXML
    public CheckBox chairsId;
    @FXML
    public CheckBox deliveryId;
    @FXML
    public CheckBox takeAwayId;
    @FXML
    private VBox box;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPane = anchorPane;
        datePickerFilter();
        for (Item i : ItemCommunication.getItems()) {
            System.out.println("items: " + i);
            CheckBox item = new CheckBox(i.getName());
            Insets insets = new Insets(10, 10, 10, 10);
            item.setPadding(insets);
            item.setTextFill(Paint.valueOf("white"));
            box.getChildren().add(item);
        }
    }

    /**
     * This method restricts the DatePicker.
     */
    public void datePickerFilter() {
        LocalDate today = LocalDate.now();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        TimeZone tz = c.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        LocalDateTime.ofInstant(c.toInstant(), zid);

        LocalDate monday = LocalDate.from(LocalDateTime.ofInstant(c.toInstant(), zid));

        dp.setValue(today);
        dp.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(ChronoLocalDate.from(today))) {
                    setDisable(true);
                }
                if (empty || date.isAfter(monday.plusDays(13))) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });
    }

    /**
     * Gets a date.
     * @return A date from the datepicker.
     */
    public LocalDate onPickDate() {
        if (dp.getValue() == null) {
            return now();
        } else {
            return dp.getValue();
        }
    }

    /**
     * Gets a capacity of the room.
     * @return A capacity.
     */
    public int getCapacity() {
        if (capacityId.getText().equals("")) {
            return 1;
        } else {
            return Integer.parseInt(capacityId.getText());
        }
    }

    /**
     * Handles the "Search" button click.
     * @return A list of the selected items.
     */
    @FXML
    public List handleSearchClick() {
        List<String> selectedItems = new ArrayList<>();
        for (Node node : box.getChildren()) {
            CheckBox item = (CheckBox) node;
            if (item.isSelected()) {
                selectedItems.add(item.getText());
            }
        }
        return selectedItems;
    }

}
