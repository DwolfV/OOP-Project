package nl.tudelft.oopp.demo.views;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainDisplay extends Application {

    public static Stage stg;

    @Override
    public void start(Stage loginStage) throws IOException { //change loginstage to stage

        this.stg = loginStage;

        FXMLLoader loader = new FXMLLoader();
        Parent loginParent = loader.load(getClass().getResource("/loginScene.fxml"));
        Scene loginScene = new Scene(loginParent);

        stg.initStyle(StageStyle.UNDECORATED);
        stg.setTitle("Log In");
        stg.setScene(loginScene);
        stg.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
