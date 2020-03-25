package nl.tudelft.oopp.demo.controllers;

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
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HeaderSceneController implements Initializable {

    private MainSceneController mainSceneController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    public void changeLeft() {

        if(mainSceneController.borderPane.getLeft().equals(mainSceneController.hamburgerMenuRoot)) {
            mainSceneController.hamburgerMenuRoot.translateXProperty().set(0);

            Timeline timeline = new Timeline();
            KeyValue key = new KeyValue(mainSceneController.hamburgerMenuRoot.translateXProperty(), -200, Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.05), key);
            timeline.getKeyFrames().add(keyFrame);
            timeline.setOnFinished(event -> {
                //Pull Menu out
                mainSceneController.sidebar.translateXProperty().set(-200);
                mainSceneController.borderPane.setLeft(mainSceneController.sidebar);

                Timeline timeline1 = new Timeline();
                KeyValue key1 = new KeyValue(mainSceneController.sidebar.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.1), key1);
                timeline1.getKeyFrames().setAll(keyFrame1);
                timeline1.play();

            });
            timeline.play();

        } else {
            //Pull Menu in
            mainSceneController.sidebar.translateXProperty().set(0);

            Timeline timeline1 = new Timeline();
            KeyValue key1 = new KeyValue(mainSceneController.sidebar.translateXProperty(), -200, Interpolator.EASE_IN);
            KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.05), key1);
            timeline1.getKeyFrames().add(keyFrame1);
            timeline1.setOnFinished(event -> {
                //Pull Menu out
                mainSceneController.hamburgerMenuRoot.translateXProperty().set(-200);
                mainSceneController.borderPane.setLeft(mainSceneController.hamburgerMenuRoot);

                Timeline timeline = new Timeline();
                KeyValue key = new KeyValue(mainSceneController.hamburgerMenuRoot.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.1), key);
                timeline.getKeyFrames().setAll(keyFrame);
                timeline.play();

            });
            timeline1.play();
        }
    }
}