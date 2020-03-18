package nl.tudelft.oopp.demo.views;

import java.io.IOException;

import com.jfoenix.controls.JFXDrawer;
import javafx.application.Application;
import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import nl.tudelft.oopp.demo.controllers.MainSceneController;

public class MainDisplay extends Application {

    public static Stage primaryStage;
    public static Stage secondaryStage;
    public static Stage registerStage;
    public static Stage adminStage;

    @Override
    public void start(Stage loginStage) throws IOException { //change loginstage to stage

        primaryStage = loginStage;
        Parent loginParent = FXMLLoader.load(getClass().getResource("/loginScene.fxml"));
        Scene loginScene = new Scene(loginParent);

        loginStage.initStyle(StageStyle.UNDECORATED);
        loginStage.setTitle("Log In");
        loginStage.setScene(loginScene);
        loginStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
