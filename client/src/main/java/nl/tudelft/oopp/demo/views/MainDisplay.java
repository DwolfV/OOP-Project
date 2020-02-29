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

//        this.stg = loginStage;
//
//        FXMLLoader loader = new FXMLLoader();
//        Parent loginParent = loader.load(getClass().getResource("/loginScene.fxml"));
//        Scene loginScene = new Scene(loginParent);
//
//        loginStage.initStyle(StageStyle.UNDECORATED);
//        loginStage.setTitle("Log In");
//        loginStage.setScene(loginScene);
//        loginStage.show();

        window = loginStage;
        window.setTitle("thenewboston - JavaFX");

        //Name column
        TableColumn<Building, Long> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        //Price column
        TableColumn<Building, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Quantity column
        TableColumn<Building, String> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setMinWidth(100);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        //OneColumn column
        TableColumn<Building, String> OneColumn = new TableColumn<>("Free");
        OneColumn.setMinWidth(100);
        OneColumn.setCellValueFactory(new PropertyValueFactory<>("Free"));

        //OneColumn column
        TableColumn<Building, String> TwoColumn = new TableColumn<>("Four");
        TwoColumn.setMinWidth(100);
        TwoColumn.setCellValueFactory(new PropertyValueFactory<>("Four"));

        //OneColumn column
        TableColumn<Building, String> ThreeColumn = new TableColumn<>("Three");
        ThreeColumn.setMinWidth(100);
        ThreeColumn.setCellValueFactory(new PropertyValueFactory<>("Three"));


        table = new TableView<>();
        table.setItems(getProduct());
        table.getColumns().addAll(nameColumn, priceColumn, quantityColumn, OneColumn, TwoColumn, ThreeColumn);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();

    }

    public ObservableList<Building> getProduct(){
        ObservableList<Building> buildings = FXCollections.observableArrayList();
        buildings.add(new Building(5, "5", "5","5","S","5"));
        return buildings;
    }

    public static void main(String[] args) { launch(args); }
}
