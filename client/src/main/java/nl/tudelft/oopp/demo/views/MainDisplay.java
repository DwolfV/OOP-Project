package nl.tudelft.oopp.demo.views;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nl.tudelft.oopp.demo.helperclasses.Building;

public class MainDisplay extends Application {

    public static Stage stg;

    Stage window;
    TableView<Building> table;

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
