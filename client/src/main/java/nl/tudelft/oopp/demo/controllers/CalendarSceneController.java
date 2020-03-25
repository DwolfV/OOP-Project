package nl.tudelft.oopp.demo.controllers;

import com.calendarfx.view.CalendarView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class CalendarSceneController implements Initializable {

    private MainSceneController mainSceneController;

    @FXML private CalendarView calendar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        calendar.setPrefWidth(screenBounds.getWidth() - 200);
        calendar.setPrefHeight(screenBounds.getHeight() - 170);
    }

    public void setController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }
}
