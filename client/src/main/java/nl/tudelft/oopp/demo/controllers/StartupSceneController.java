package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartupSceneController implements Initializable {

    public SignInSceneController signInSceneController;
    public RegisterSceneController registerSceneController;
    public Parent signInRoot;
    public Parent registerRoot;

    @FXML
    public BorderPane startupId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize StartupScene");
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

    @FXML
    public void loadSignIn() {
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage)startupId.getScene().getWindow();
        stage.close();
    }
}
