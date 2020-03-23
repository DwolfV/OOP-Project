package nl.tudelft.oopp.demo.controllers;

import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import nl.tudelft.oopp.demo.communication.UserCommunication;
import nl.tudelft.oopp.demo.helperclasses.User;
import nl.tudelft.oopp.demo.views.MainDisplay;

public class RegisterSceneController implements Initializable {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void handleSignUpButton() {
        // have a sign up method on the server communication side
        User user = new User(email.getText(),"user", firstName.getText(), lastName.getText());
        // TODO add a text field for username
        if (UserCommunication.createNewUser(user, firstName.getText(), password.getText())){
            MainDisplay.registerStage.close();
        }
        else {
            // there is already a user with that username
            return;
        }
    }
}
