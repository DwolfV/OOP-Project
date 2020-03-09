package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import nl.tudelft.oopp.demo.views.MainDisplay;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterSceneController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void handleSignUpButton(ActionEvent event) {
        // have a sign up method on the server communication side
        MainDisplay.registerStage.close();
    }
}
