package nl.tudelft.oopp.demo.views;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nl.tudelft.oopp.demo.helperclasses.Building;

public class MainDisplay extends Application {

    public static Stage stg;

    @Override
    public void start(Stage loginStage) throws IOException { //change loginstage to stage

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
