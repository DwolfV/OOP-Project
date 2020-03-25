package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class RegisterSceneController implements Initializable {

    private SignInSceneController signInSceneController;
    private StartupSceneController startupSceneController;
    private Parent signInRoot;

    @FXML public BorderPane registerId;
    @FXML private TextField firstNameField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("RegisterSceneController initialize");

        Platform.runLater(() -> {
            firstNameField.requestFocus();
        });
    }

    /**
     * Set controllers.
     * @param startupSceneController controller of the startup Scene
     * @param signInSceneController controller of the sign in Scene
     */
    public void setControllers(StartupSceneController startupSceneController, SignInSceneController signInSceneController) {
        this.startupSceneController = startupSceneController;
        System.out.println("RegisterSceneController -> " + startupSceneController);
        this.signInSceneController = signInSceneController;
    }

    /**
     * What happens when you want to change from Sign in to Register.
     * @param event mouse event
     */
    @FXML
    public void selectUI(MouseEvent event) {
        System.out.println("RegisterSceneController -> selectUI");

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
}
