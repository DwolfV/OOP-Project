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
    public void start(Stage loginStage) throws IOException {
        this.stg = loginStage;

        FXMLLoader loader = new FXMLLoader();
        Parent loginParent = loader.load(getClass().getResource("/loginScene.fxml"));
        Scene loginScene = new Scene(loginParent);

        loginStage.initStyle(StageStyle.UNDECORATED);
        loginStage.setTitle("Log In");
        loginStage.setScene(loginScene);
        loginStage.show();

    }


    public static void main(String[] args) { launch(args); }
}
