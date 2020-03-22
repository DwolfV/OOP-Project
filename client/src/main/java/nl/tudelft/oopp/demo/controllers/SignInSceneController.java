package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import nl.tudelft.oopp.demo.communication.UserCommunication;

public class SignInSceneController implements Initializable {

    private RegisterSceneController registerSceneController;
    private StartupSceneController startupSceneController;
    private Parent registerRoot;

    @FXML public BorderPane signInId;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button signInButton;

    @FXML private Label signingInLabel;
    @FXML private Label failedSignInLabel;
    @FXML private Label signedInLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("SignInSceneController initialize");

        Platform.runLater(() -> {
                usernameField.requestFocus();
                startupSceneController.signInOnEnter();
            });

    }

    /**
     * Set controllers.
     * @param startupSceneController controller of the startup Scene
     * @param registerSceneController controller of the register Scene
     */
    public void setControllers(StartupSceneController startupSceneController, RegisterSceneController registerSceneController) {
        this.startupSceneController = startupSceneController;
        System.out.println("SignInSceneController -> " + startupSceneController);
        this.registerSceneController = registerSceneController;
    }

    /**
     * What happens when you want to change from Sign in to Register.
     * @param event mouse event
     */
    @FXML
    public void selectUI(MouseEvent event) {
        System.out.println("SignInSceneController -> selectUI");

        FXMLLoader signInLoader = new FXMLLoader(getClass().getResource("/Scenes/registerScene.fxml"));
        try {
            registerRoot = signInLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        registerSceneController = signInLoader.getController();
        registerSceneController.setControllers(startupSceneController, this);
        startupSceneController.startupId.setCenter(registerRoot);
    }

    /**
     * Handles what happens when the client clicks on the Login button.
     */
    @FXML
    public void handleLoginButton() {
        System.out.println("handel");
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!UserCommunication.authenticate(username, password)) {
            failedSignInLabel.setVisible(true);
            passwordField.clear();
            passwordField.requestFocus();
            return;
        }

        failedSignInLabel.setVisible(false);
        signedInLabel.setVisible(true);
    }
}
