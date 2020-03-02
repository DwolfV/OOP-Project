package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminSceneController implements Initializable {

    @FXML
    private TextField BuildingID;
    @FXML
    private TextField BuildingName;
    @FXML
    private TextField StreetName;
    @FXML
    private TextField StreetNumber;
    @FXML
    private TextField ZipCode;
    @FXML
    private TextField City;

    public AdminSceneController() {
    }

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
    private ObservableList<Building> data = FXCollections.observableArrayList();
    final HBox hb = new HBox();

    @FXML
    public void handleViewBuildingButtonClicked() {
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
                new PropertyValueFactory<>("City"));
        cityCol.setCellFactory(TextFieldTableCell.<Building>forTableColumn());
        cityCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Building, String> t) -> {
                    ((Building) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setCity(t.getNewValue());
                });

        data = FXCollections.observableList(BuildingCommunication.getBuildings());
        table.setItems(data);
        table.getColumns().addAll(idCol, buildingCol, streetNameCol, streetNumCol, zipCodeCol, cityCol);

        //delete button
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            deleteButtonClicked();
        });

        //delete button
        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            updateButtonClicked();
        });

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, deleteButton, updateButton);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        secondStage.setScene(scene);
        secondStage.show();
    }

    private void updateButtonClicked() {
        Building building = table.getSelectionModel().getSelectedItem();
        BuildingCommunication.updateBuilding(building.getId(), building.getName(), building.getStreetName(), building.getStreetNumber(),building.getZipCode(), building.getCity());
    }

    public void deleteButtonClicked() {
        ObservableList<Building> buildingSelected, allBuildings;
        allBuildings = table.getItems();
        Building building = table.getSelectionModel().getSelectedItem();

        //buildingSelected.forEach(allBuildings::remove);
//        buildingSelected.forEach(building -> {
//            System.out.println(building.getId());
//            BuildingCommunication.removeBuilding(building.getId());
//        } );
        allBuildings.remove(building);
        BuildingCommunication.removeBuilding(building.getId());
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
