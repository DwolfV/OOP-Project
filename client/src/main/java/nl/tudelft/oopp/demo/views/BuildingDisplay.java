package nl.tudelft.oopp.demo.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class BuildingDisplay extends Application {

    public void start(Stage buildingStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent buildingParent = loader.load(getClass().getResource("/mainScene.fxml"));
        Scene buildingScene = new Scene(buildingParent);

        buildingStage.setTitle("Log In");
        buildingStage.setScene(buildingScene);
        buildingStage.show();
    }

    public static void main(String[] args) { launch(args); }

}
