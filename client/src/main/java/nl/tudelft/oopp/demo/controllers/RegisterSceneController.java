package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import nl.tudelft.oopp.demo.communication.UserCommunication;
import nl.tudelft.oopp.demo.entities.User;

public class RegisterSceneController implements Initializable {

    @FXML
    public BorderPane registerId;
    public TextField lastNameField;
    public TextField usernameField;
    public PasswordField passwordField;
    public PasswordField passwordConfirmField;
    private SignInSceneController signInSceneController;
    private StartupSceneController startupSceneController;
    private Parent signInRoot;
    @FXML
    private TextField firstNameField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> {
            firstNameField.requestFocus();
        });
    }

    /**
     * Set controllers.
     *
     * @param startupSceneController controller of the startup Scene
     * @param signInSceneController  controller of the sign in Scene
     */
    public void setControllers(StartupSceneController startupSceneController, SignInSceneController signInSceneController) {
        this.startupSceneController = startupSceneController;
        this.signInSceneController = signInSceneController;
    }

    /**
     * What happens when you want to change from Sign in to Register.
     *
     * @param event mouse event
     */
    @FXML
    public void selectUI(MouseEvent event) {

        FXMLLoader signInLoader = new FXMLLoader(getClass().getResource("/Scenes/signInScene.fxml"));
        try {
            signInRoot = signInLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        signInSceneController = signInLoader.getController();
        signInSceneController.setControllers(startupSceneController, this);
        startupSceneController.startupId.setCenter(signInRoot);
    }

    /**
     * Handles what happens when a user clicks on the 'Sign Up' button.
     */
    @FXML
    public void handleSignUpButton() {
        // have a sign up method on the server communication
        User user = new User("NO EMAIL", "user", firstNameField.getText(), lastNameField.getText());
        // TODO add a text field for username
        if (!passwordField.getText().equals(passwordConfirmField.getText())) {
            // the two passwords do not match
            passwordConfirmField.clear();
            passwordConfirmField.requestFocus();
            return;
        }
        if (usernameField.getText() == null || passwordField.getText() == null || firstNameField.getText() == null
            || lastNameField.getText() == null || usernameField.getText().equals("") || usernameField.getText().contains(" ")) {
            usernameField.clear();
            usernameField.requestFocus();
            return;
        }
        if (UserCommunication.createNewUser(user, usernameField.getText(), passwordField.getText())) {
            FXMLLoader signInLoader = new FXMLLoader(getClass().getResource("/Scenes/signInScene.fxml"));
            try {
                signInRoot = signInLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            signInSceneController = signInLoader.getController();
            signInSceneController.setControllers(startupSceneController, this);
            startupSceneController.startupId.setCenter(signInRoot);
        } else {
            // there is already a user with that username
            usernameField.clear();
            usernameField.requestFocus();
            return;
        }
    }
}
