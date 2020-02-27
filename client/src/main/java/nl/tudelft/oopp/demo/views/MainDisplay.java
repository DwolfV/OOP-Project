package nl.tudelft.oopp.demo.views;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nl.tudelft.oopp.demo.controllers.MainSceneController;

public class MainDisplay extends Application {

    public static Stage stg;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stg = primaryStage;

        FXMLLoader loader = new FXMLLoader();
        Parent firstPane = loader.load(getClass().getResource("/loginScene.fxml"));
        Scene mainScene = new Scene(firstPane);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Log In");
        primaryStage.setScene(mainScene);
        primaryStage.show();

    }


    public static void main(String[] args) { launch(args); }
}
