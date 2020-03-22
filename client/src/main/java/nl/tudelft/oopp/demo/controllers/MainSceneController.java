package nl.tudelft.oopp.demo.controllers;

import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    public Parent headerRoot;
    public Parent sidebarRoot;
    public Parent hamburgerMenuRoot;

    public HeaderSceneController headerSceneController;
    public SidebarSceneController sidebarSceneController;
    public HamburgerMenuSceneController hamburgerMenuSceneController;

    @FXML public BorderPane borderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize StartupScene");
        FXMLLoader headerLoader = new FXMLLoader(getClass().getResource("/Scenes/headerScene.fxml"));
        FXMLLoader sidebarLoader = new FXMLLoader(getClass().getResource("/Scenes/sidebarScene.fxml"));
        FXMLLoader hamburgerMenuLoader = new FXMLLoader(getClass().getResource("/Scenes/hamburgerMenuScene.fxml"));
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

    public void switchLeftBar() throws IOException {
        borderPane.setLeft(hamburgerMenuRoot);
        HamburgerBasicCloseTransition transition = new HamburgerBasicCloseTransition(hamburgerMenuSceneController.hamburger);
        transition.setRate(-1);
        hamburgerMenuSceneController.hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();
            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.open();
                drawer.setTranslateX(140);
            }
        });
    }


}
