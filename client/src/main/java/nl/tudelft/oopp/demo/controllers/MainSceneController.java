package nl.tudelft.oopp.demo.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

public class MainSceneController {

    /**
     * Handles clicking the button.
     */
    public void buttonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("A list of all buildings");
        alert.setHeaderText("A list of all buildings:");
        alert.setResizable(true);
        alert.getDialogPane().setContent(new Label(ServerCommunication.getBuildings()));
        alert.showAndWait();
    }
}
