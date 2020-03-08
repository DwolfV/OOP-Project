package nl.tudelft.oopp.demo.views;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Log In");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
