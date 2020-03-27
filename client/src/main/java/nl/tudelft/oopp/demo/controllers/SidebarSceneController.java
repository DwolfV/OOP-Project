package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SidebarSceneController implements Initializable {

    @FXML public AnchorPane anchorPane;
    @FXML public DatePicker dp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPane = anchorPane;
    }

    public LocalDate onPickDate(){
        return dp.getValue();
    }
}
