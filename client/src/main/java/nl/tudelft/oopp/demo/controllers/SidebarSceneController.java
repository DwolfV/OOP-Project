package nl.tudelft.oopp.demo.controllers;

import static java.time.LocalDate.now;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import nl.tudelft.oopp.demo.communication.ItemCommunication;
import nl.tudelft.oopp.demo.entities.Item;

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
