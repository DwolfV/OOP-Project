package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;

import java.net.URL;
import java.util.ResourceBundle;

public class addRoomSceneController implements Initializable {

    @FXML
    private TextField RoomName;
    @FXML
    private TextField Capacity;
    @FXML
    private TextField Building;

    private final TableView<Building> tableBuilding = new TableView<>();
    private ObservableList<Building> buildingData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void HandleBuildingNamesButton(ActionEvent event) {
        tableBuilding.getColumns().clear();

        Stage secondStage = new Stage();
        Scene scene = new Scene(new Group());

        secondStage.setTitle("Table View");
        secondStage.setWidth(250);
        secondStage.setHeight(400);

        tableBuilding.setEditable(true);

        TableColumn<Building, Long> idCol =
                new TableColumn<>("id");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn<Building, String> buildingCol =
                new TableColumn<>("Building Name");
        buildingCol.setMinWidth(100);
        buildingCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());
        tableBuilding.setItems(buildingData);
        tableBuilding.getColumns().addAll(idCol, buildingCol);

        tableBuilding.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        GridPane borderPane = new GridPane();
        borderPane.getChildren().add(tableBuilding);

        ((Group) scene.getRoot()).getChildren().addAll(borderPane);

        secondStage.setScene(scene);
        secondStage.show();
    }

    @FXML
    private void handleTextFieldDataRoom(ActionEvent event){
        String roomName = RoomName.getText();
        int capacity = Integer.parseInt(Capacity.getText());

        RoomCommunication.addRoom(roomName, capacity, Long.parseLong(Building.getText()));

        Building.setText(null);
        RoomName.setText(null);
        Capacity.setText(null);
    }
}
