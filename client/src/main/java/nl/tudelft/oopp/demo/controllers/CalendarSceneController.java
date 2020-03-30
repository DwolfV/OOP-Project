package nl.tudelft.oopp.demo.controllers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import nl.tudelft.oopp.demo.helperclasses.RoomReservation;

public class CalendarSceneController implements Initializable {

    private MainSceneController mainSceneController;

    @FXML private CalendarView calendar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        calendar.setPrefWidth(screenBounds.getWidth() - 200);
        calendar.setPrefHeight(screenBounds.getHeight() - 170);
        Calendar cal = new Calendar();
        Interval interval = new Interval(LocalDate.parse("2020-03-30"), LocalTime.parse("10:00"), LocalDate.parse("2020-03-30"), LocalTime.parse("11:00"));
        Entry<RoomReservation> reservation = new Entry<>("Reservation", interval);
        RoomReservation asdf = new RoomReservation();
        reservation.setUserObject(asdf);
        cal.addEntry(reservation);
        CalendarSource calendarSource = new CalendarSource("default");
        calendarSource.getCalendars().add(cal);
        calendar.getCalendarSources().add(calendarSource);
        calendar.setShowAddCalendarButton(false);

        // will probably set the calendar to null when deleting entry from calendar
        EventHandler<CalendarEvent> handler = event -> {
            System.out.println("EVEEENT");
            System.out.println(event);
            // can get old dates and times -> set them if the server responds with a 409 conflict
            // also populate the view with unavailable times.
        };

        cal.addEventHandler(handler);
    }

    public void setController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }
}
