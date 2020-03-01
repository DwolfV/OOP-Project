package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminSceneController implements Initializable {

    private TextField BuildingID;
    private TextField BuildingName;
    private TextField BuildingNumber;
    private TextField StreetName;
    private TextField StreetNumber;
    private TextField ZipCode;
    private TextField City;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Loading User Data");
    }

    @FXML
    public void handleAddBuildingButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addBuildingScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("New Building");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddRoomButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addRoomScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("New Room");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private TableView<Building> table = new TableView<>();
    private ObservableList<Building> data = FXCollections.observableArrayList(
                    new Building((long) 10, "Daniel", "Daniel", "29", "2828", "Delft"),
                    new Building((long) 11, "Daniel", "Daniel", "29", "2828", "Delft"),
                    new Building((long) 12, "Daniel", "Daniel", "29", "2828", "Delft"),
                    new Building((long) 13, "Daniel", "Daniel", "29", "2828", "Delft"),
                    new Building((long) 14, "Daniel", "Daniel", "29", "2828", "Delft"));
    final HBox hb = new HBox();

    @FXML
    public void handleViewButtonClicked() {
        Stage secondStage = new Stage();
        Scene scene = new Scene(new Group());

        secondStage.setTitle("Table View");
        secondStage.setWidth(450);
        secondStage.setHeight(550);

        final Label label = new Label("Buildings");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

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
        buildingCol.setCellFactory(TextFieldTableCell.<Building>forTableColumn());
        buildingCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> {
                    ((Building) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                });

        TableColumn<Building, String> streetNameCol =
                new TableColumn<>("Street Name");
        streetNameCol.setMinWidth(100);
        streetNameCol.setCellValueFactory(
                new PropertyValueFactory<>("streetName"));
        streetNameCol.setCellFactory(TextFieldTableCell.<Building>forTableColumn());
        streetNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> {
                    ((Building) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setStreetName(t.getNewValue());
                });

        TableColumn<Building, String> streetNumCol =
                new TableColumn<>("Street Number");
        streetNumCol.setMinWidth(100);
        streetNumCol.setCellValueFactory(
                new PropertyValueFactory<>("streetNumber"));
        streetNumCol.setCellFactory(TextFieldTableCell.<Building>forTableColumn());
        streetNumCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> {
                    ((Building) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setStreetNumber(t.getNewValue());
                });

        TableColumn<Building, String> zipCodeCol =
                new TableColumn<>("Zip Code");
        zipCodeCol.setMinWidth(100);
        zipCodeCol.setCellValueFactory(
                new PropertyValueFactory<>("zipCode"));
        zipCodeCol.setCellFactory(TextFieldTableCell.<Building>forTableColumn());
        zipCodeCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> {
                    ((Building) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setZipCode(t.getNewValue());
                });

        TableColumn<Building, String> cityCol =
                new TableColumn<>("City");
        cityCol.setMinWidth(100);
        cityCol.setCellValueFactory(
                new PropertyValueFactory<>("city"));
        cityCol.setCellFactory(TextFieldTableCell.<Building>forTableColumn());
        cityCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> {
                    ((Building) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setCity(t.getNewValue());
                });

        table.setItems(data);
        table.getColumns().addAll(idCol, buildingCol, streetNameCol, streetNumCol, zipCodeCol, cityCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        secondStage.setScene(scene);
        secondStage.show();
    }

    @FXML
    private void handleTextFieldData(ActionEvent event){
        Long buildingId = Long.parseLong(BuildingID.getText());
        String buildingName = BuildingName.getText();
        String streetName = StreetName.getText();
        String streetNumber = StreetNumber.getText();
        String zipCode = ZipCode.getText();
        String city = City.getText();

        BuildingCommunication.addBuilding(buildingId, buildingName, streetName, streetNumber, zipCode, city);
    }



}
