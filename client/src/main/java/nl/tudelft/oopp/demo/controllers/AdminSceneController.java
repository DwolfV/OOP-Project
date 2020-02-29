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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.helperclasses.Building;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminSceneController implements Initializable {

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

    private final TableView<Building> table = new TableView<>();
    private final ObservableList<Building> data =
            FXCollections.observableArrayList(
                    new Building("10", "Daniel", "Daniel", "29", "2828", "Delft"),
                    new Building("11", "Daniel", "Daniel", "29", "2828", "Delft"),
                    new Building("12", "Daniel", "Daniel", "29", "2828", "Delft"),
                    new Building("13", "Daniel", "Daniel", "29", "2828", "Delft"),
                    new Building("14", "Daniel", "Daniel", "29", "2828", "Delft"));
    final HBox hb = new HBox();

    @FXML
    public void handleViewButtonClicked() {
        Stage secondStage = new Stage();
        Scene scene = new Scene(new Group());

        secondStage.setTitle("Table View Sample");
        secondStage.setWidth(450);
        secondStage.setHeight(550);

        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn firstNameCol = new TableColumn("id");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn lastNameCol = new TableColumn("name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        TableColumn emailCol = new TableColumn("streetName");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<>("streetName"));

        TableColumn CityCol = new TableColumn("streetNumber");
        CityCol.setMinWidth(200);
        CityCol.setCellValueFactory(
                new PropertyValueFactory<>("streetNumber"));

        TableColumn ZipCol = new TableColumn("zipCode");
        ZipCol.setMinWidth(200);
        ZipCol.setCellValueFactory(
                new PropertyValueFactory<>("zipCode"));

        TableColumn StreetCol = new TableColumn("city");
        StreetCol.setMinWidth(200);
        StreetCol.setCellValueFactory(
                new PropertyValueFactory<>("city"));

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol, CityCol, ZipCol, StreetCol);

        final TextField addIdName = new TextField();
        addIdName.setPromptText("id Name");
        addIdName.setMaxWidth(firstNameCol.getPrefWidth());
        final TextField addLastName = new TextField();
        addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        addLastName.setPromptText("Last Name");
        final TextField addEmail = new TextField();
        addEmail.setMaxWidth(emailCol.getPrefWidth());
        addEmail.setPromptText("Email");
        final TextField addCity = new TextField();
        addCity.setMaxWidth(emailCol.getPrefWidth());
        addCity.setPromptText("Email");
        final TextField addZip = new TextField();
        addZip.setMaxWidth(emailCol.getPrefWidth());
        addZip.setPromptText("Email");
        final TextField addStreet = new TextField();
        addStreet.setMaxWidth(emailCol.getPrefWidth());
        addStreet.setPromptText("Email");

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            data.add(new Building(
                    addIdName.getId(),
                    addLastName.getText(),
                    addEmail.getText(),
                    addCity.getText(),
                    addZip.getText(),
                    addStreet.getText()));
            addIdName.clear();
            addLastName.clear();
            addEmail.clear();
            addCity.clear();
            addZip.clear();
            addStreet.clear();
        });

        hb.getChildren().addAll(addIdName, addLastName, addEmail, addCity, addZip, addStreet, addButton);
        hb.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        secondStage.setScene(scene);
        secondStage.show();

    }

}
