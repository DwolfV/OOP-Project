package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StartupSceneController implements Initializable {

    private SignInSceneController signInSceneController;
    private RegisterSceneController registerSceneController;
    private Parent signInRoot;
    private Parent registerRoot;

    @FXML public BorderPane startupId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader signInLoader = new FXMLLoader(getClass().getResource("/Scenes/signInScene.fxml"));
        FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("/Scenes/registerScene.fxml"));
        try {
            signInRoot = signInLoader.load();
            registerRoot = registerLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startupId.setCenter(signInRoot);

        signInSceneController = signInLoader.getController();
        registerSceneController = registerLoader.getController();

        signInSceneController.setControllers(this, registerSceneController);
        registerSceneController.setControllers(this, signInSceneController);
    }

    /**
     * handels the signing in when you press enter.
     */
    public void signInOnEnter() {
        signInRoot.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                System.out.println("Key Pressed: " + ev.getCode());
                signInSceneController.handleLoginButton();
            }
        });
    }

    /**
     * Closes the start up Stage.
     */
    @FXML
    public void close() {
        Stage stage = (Stage)startupId.getScene().getWindow();
        stage.close();
    }
}
