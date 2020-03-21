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

public class RegisterSceneController implements Initializable {

    public SignInSceneController signInSceneController;
    public StartupSceneController startupSceneController;
    public Parent signInRoot;

    @FXML
    public BorderPane registerId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("RegisterSceneController initialize");
    }

    public void setControllers(StartupSceneController startupSceneController, SignInSceneController signInSceneController) {
        this.startupSceneController = startupSceneController;
        System.out.println("RegisterSceneController -> " + startupSceneController);
        this.signInSceneController = signInSceneController;
    }

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

        //registerId.setCenter(signInRoot);
    }
}
