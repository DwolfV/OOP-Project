package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInSceneController implements Initializable {

    public RegisterSceneController registerSceneController;
    public StartupSceneController startupSceneController;
    public Parent registerRoot;

    @FXML
    public BorderPane signInId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("SignInSceneController initialize");
    }

    public void setControllers(StartupSceneController startupSceneController, RegisterSceneController registerSceneController) {
        this.startupSceneController = startupSceneController;
        System.out.println("SignInSceneController -> "+ startupSceneController);
        this.registerSceneController = registerSceneController;
    }

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
}
