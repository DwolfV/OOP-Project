package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    @FXML private Label failedSignInLabelNoInternet;

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

        int authenticationFlag = UserCommunication.authenticate(username, password);
        if (authenticationFlag == 0) {
            // wrong password
            failedSignInLabelNoInternet.setVisible(false);
            failedSignInLabel.setVisible(true);
            passwordField.clear();
            passwordField.requestFocus();
            return;
        } else if (authenticationFlag == 2) {
            // then the user has connection issues
            failedSignInLabel.setVisible(false);
            failedSignInLabelNoInternet.setVisible(true);
            return;
        }

        failedSignInLabel.setVisible(false);
        failedSignInLabelNoInternet.setVisible(false);
        signedInLabel.setVisible(true);
        openMainStage();
    }

    /**
     * Open the main stage after logging in.
     */
    public void openMainStage() {
        startupSceneController.close();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Scenes/mainScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("TU Delft reservations");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
