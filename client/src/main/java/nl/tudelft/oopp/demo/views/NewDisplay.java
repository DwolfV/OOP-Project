package nl.tudelft.oopp.demo.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;

public class NewDisplay extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/homeScene.fxml"));
        Scene loginScene = new Scene(root);

        primaryStage.setTitle("Log In");
        primaryStage.setScene(loginScene);
        primaryStage.show();

//        JFXHamburger hamburger = new JFXHamburger();
//        HamburgerBasicCloseTransition transition = new HamburgerBasicCloseTransition(hamburger);
//        transition.setRate(-1);
//        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//            transition.setRate(transition.getRate() * -1);
//            transition.play();
//        });
//
//        FlowPane flowPane = new FlowPane();
//        flowPane.getChildren().addAll(hamburger);
//        flowPane.setMaxSize(200, 200);
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
//        drawersStack.setContent(flowPane);
//
//        hamburger.addEventHandler(MOUSE_PRESSED, e -> drawersStack.toggle(leftDrawer));
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

