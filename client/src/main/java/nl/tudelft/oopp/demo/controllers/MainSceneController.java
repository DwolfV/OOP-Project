package nl.tudelft.oopp.demo.controllers;

//import com.jfoenix.controls.JFXHamburger;
//import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.*;


import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.views.MainDisplay;

import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    @FXML
    private Label closeButton;
    @FXML
    private Pane Details_1, Details_2, Details_3, Details_4, Details_5, Details_6, Details_7, Details_8, Details_9;

//    @FXML
//    private JFXHamburger menu;
//
//    @FXML
//    public void handleMenu(MouseEvent event) {
//        HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(menu);
//        transition.setRate(-1);
//        menu.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
//            transition.setRate(transition.getRate()*-1);
//            transition.play();
//        });
//    }

    @FXML
    public void handleCloseButtonAction(MouseEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
//        Platform.exit();
//        System.exit(0);
    }

//     handles for now both home and login buttons
    @FXML
    public void handleHomeButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/calendarScene.fxml"));
            Parent calendarParent = (Parent) fxmlLoader.load();
            Stage calendarStage = new Stage();

            calendarStage.setScene(new Scene(calendarParent));
            calendarStage.setTitle("Home");
            calendarStage.show();
            MainDisplay.stg.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleReservationButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reservationsScene.fxml"));
            Parent reservationParent = (Parent) fxmlLoader.load();
            Stage reservationsStage = new Stage();

            reservationsStage.setScene(new Scene(reservationParent));
            reservationsStage.setTitle("Reservations");
            reservationsStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRestaurantsButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/restaurantsScene.fxml"));
            Parent restaurantsParent = (Parent) fxmlLoader.load();
            Stage restaurantsStage = new Stage();

            restaurantsStage.setScene(new Scene(restaurantsParent));
            restaurantsStage.setTitle("Restaurants");
            restaurantsStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleFriendsButton(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/friendsScene.fxml"));
            Parent friendsParent = (Parent) fxmlLoader.load();
            Stage friendsStage = new Stage();

            friendsStage.setScene(new Scene(friendsParent));
            friendsStage.setTitle("Friends");
            friendsStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSettingsButton(ActionEvent event) throws Exception {
        try {
            URL location = getClass().getResource("/settingsScene.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            Parent settingsParent = (Parent) fxmlLoader.load();
            Stage settingsStage = new Stage();

            settingsStage.setScene(new Scene(settingsParent));
            settingsStage.setTitle("Settings");
            settingsStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleAdminButton (ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adminScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("Admin");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handlePopUp(MouseEvent event) throws Exception {
        try {
            Tooltip details1 = new Tooltip();
            Tooltip details2 = new Tooltip();
            Tooltip details3 = new Tooltip();

            details1.setText("Facilities:\n - chairs \n - tables \n - couch \n - table");
            details1.setStyle("-fx-font-size: 15");

            details2.setText("Facilities:\n - chairs \n - blackboard \n - table");
            details2.setStyle("-fx-font-size: 15");

            details3.setText("Facilities:\n - chairs \n - table");
            details3.setStyle("-fx-font-size: 15");

            Tooltip.install(Details_1, details1);
            Tooltip.install(Details_2, details2);
            Tooltip.install(Details_3, details3);
            Tooltip.install(Details_4, details1);
            Tooltip.install(Details_5, details2);
            Tooltip.install(Details_6, details3);
            Tooltip.install(Details_7, details1);
            Tooltip.install(Details_8, details2);
            Tooltip.install(Details_9, details3);

        } catch(Exception e) {
            e.printStackTrace();
        }

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

//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("A list of all buildings");
//        alert.setHeaderText("A list of all buildings:");
//        alert.setResizable(true);
//        alert.getDialogPane().setContent(new Label(ServerCommunication.getBuildings()));
//        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Loading User Data");
    }

}
