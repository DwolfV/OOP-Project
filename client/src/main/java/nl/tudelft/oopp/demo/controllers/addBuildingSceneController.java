package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;

import java.net.URL;
import java.util.ResourceBundle;

public class addBuildingSceneController  implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void handleTextFieldData(ActionEvent event){
        String buildingName = BuildingName.getText();
        String streetName = StreetName.getText();
        String streetNumber = StreetNumber.getText();
        String zipCode = ZipCode.getText();
        String city = City.getText();

        BuildingCommunication.addBuilding(buildingName, streetName, streetNumber, zipCode, city);

        BuildingName.setText(null);
        StreetName.setText(null);
        StreetNumber.setText(null);
        ZipCode.setText(null);
        City.setText(null);
    }
}
