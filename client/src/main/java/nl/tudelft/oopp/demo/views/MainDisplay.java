package nl.tudelft.oopp.demo.views;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainDisplay extends Application {

    public Stage startupStage;
    public Stage mainStage;

    private double xpos;
    private double ypos;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Scenes/startupScene.fxml"));

        Scene scene = new Scene(root);

        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xpos = event.getSceneX();
                ypos = event.getSceneY();
            }
        });

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.show();
    }
}
