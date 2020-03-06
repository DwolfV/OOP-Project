package nl.tudelft.oopp.demo.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;

public class NewDisplay extends Application {

    public static Stage stg1;

    private static final String LEFT = "LEFT";

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stg1 = primaryStage;

        Parent loginParent = FXMLLoader.load(getClass().getResource("/homeScene.fxml"));
        Scene loginScene = new Scene(loginParent);

        stg1.setTitle("Log In");
        stg1.setScene(loginScene);
        stg1.show();

//        FlowPane borderPane = new FlowPane();
//        JFXButton leftButton = new JFXButton(LEFT);
//        borderPane.getChildren().addAll(leftButton);
//        borderPane.setMaxSize(200, 200);
//
//        JFXDrawer leftDrawer = new JFXDrawer();
//        VBox leftDrawerPane = new VBox();
//        leftDrawerPane.getChildren().addAll(new JFXButton("Home"), new JFXButton("Reservations"));
//        leftDrawer.setSidePane(leftDrawerPane);
//        leftDrawer.setDefaultDrawerSize(150);
//        leftDrawer.setResizeContent(true);
//        leftDrawer.setOverLayVisible(false);
//        leftDrawer.setResizableOnDrag(true);
//
//        JFXDrawersStack drawersStack = new JFXDrawersStack();
//        drawersStack.setContent(borderPane);
//
//        leftDrawer.setId(LEFT);
//
//        leftButton.addEventHandler(MOUSE_PRESSED, e -> drawersStack.toggle(leftDrawer));
//
//        final Scene scene = new Scene(drawersStack, 800, 800);
//
//        primaryStage.setTitle("JFX Drawer Demo");
//        primaryStage.setScene(scene);
//        primaryStage.setResizable(true);
//        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

