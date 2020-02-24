package nl.tudelft.oopp.demo.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

public class MainSceneController {

    /**
     * Handles clicking the button.
     */

    public Button button;

    public void logInButtonClicked() {
        System.out.println("User logged in");

//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Quote for you");
//        alert.setHeaderText(null);
//        alert.setContentText(ServerCommunication.getQuote());
//        alert.showAndWait();


    }
}
