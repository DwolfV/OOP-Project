package nl.tudelft.oopp.demo.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    public Parent headerRoot;
    public Parent sidebarRoot;
    public Parent hamburgerMenuRoot;

    public FXMLLoader headerLoader;
    public FXMLLoader sidebarLoader;
    public FXMLLoader hamburgerMenuLoader;

    @FXML public BorderPane borderPane;
    @FXML public VBox vBox;
    @FXML public Pane tempPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize StartupScene");
        headerLoader = new FXMLLoader(getClass().getResource("/Scenes/headerScene.fxml"));
        sidebarLoader = new FXMLLoader(getClass().getResource("/Scenes/sidebarScene.fxml"));
        hamburgerMenuLoader = new FXMLLoader(getClass().getResource("/Scenes/hamburgerMenuScene.fxml"));
        try {
            headerRoot = headerLoader.load();
            sidebarRoot = sidebarLoader.load();
            hamburgerMenuRoot = hamburgerMenuLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPane.setTop(headerRoot);
        borderPane.setLeft(sidebarRoot);

        HeaderSceneController headerSceneController = headerLoader.getController();
        headerSceneController.setController(this);
    }

    //public void selectHamburgerMenu() {
    //    borderPane.setLeft(hamburgerMenuRoot);
    //}

    //public void selectSidebar() {
    //    borderPane.setLeft(sidebarRoot);
    //}

    //public void switchLeftBar() {
    //    if(borderPane.getLeft().equals(hamburgerMenuRoot))
    //        borderPane.setLeft(sidebarRoot);
    //    else if(borderPane.getLeft().equals(sidebarRoot))
    //        borderPane.setLeft(hamburgerMenuRoot);
    //}

    public void switchSidebar() {
        Scene scene = headerRoot.getScene();

        if(borderPane.getLeft().equals(hamburgerMenuRoot)) {
            hamburgerMenuRoot.translateXProperty().set(0);

            Timeline timeline = new Timeline();
            KeyValue key = new KeyValue(hamburgerMenuRoot.translateXProperty(), -150, Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.1), key);
            timeline.getKeyFrames().add(keyFrame);
            timeline.setOnFinished(event -> {
                //Pull Menu out
                sidebarRoot.translateXProperty().set(-150);
                borderPane.setLeft(sidebarRoot);

                Timeline timeline1 = new Timeline();
                KeyValue key1 = new KeyValue(sidebarRoot.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.2), key1);
                timeline1.getKeyFrames().setAll(keyFrame1);
                timeline1.play();

            });
            timeline.play();

        } else if(borderPane.getLeft().equals(sidebarRoot)){
            //Pull Menu in
            sidebarRoot.translateXProperty().set(0);

            Timeline timeline1 = new Timeline();
            KeyValue key1 = new KeyValue(sidebarRoot.translateXProperty(), -150, Interpolator.EASE_IN);
            KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.1), key1);
            timeline1.getKeyFrames().add(keyFrame1);
            timeline1.setOnFinished(event -> {
                //Pull Menu out
                hamburgerMenuRoot.translateXProperty().set(-150);
                borderPane.setLeft(hamburgerMenuRoot);

                Timeline timeline = new Timeline();
                KeyValue key = new KeyValue(hamburgerMenuRoot.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.2), key);
                timeline.getKeyFrames().setAll(keyFrame);
                timeline.play();

            });
            timeline1.play();
        }
    }


}
