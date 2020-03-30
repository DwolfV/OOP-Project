package nl.tudelft.oopp.demo.controllers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import nl.tudelft.oopp.demo.communication.Authenticator;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.communication.RoomReservationCommunication;
import nl.tudelft.oopp.demo.helperclasses.Room;
import nl.tudelft.oopp.demo.helperclasses.RoomReservation;

public class CalendarSceneController implements Initializable {

    private MainSceneController mainSceneController;

    @FXML
    private CalendarView calendarView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        calendarView.setPrefWidth(screenBounds.getWidth() - 200);
        calendarView.setPrefHeight(screenBounds.getHeight() - 170);
        // will get the Default calendar
        Calendar calendar = calendarView.getCalendarSources().get(0).getCalendars().get(0);

        List<RoomReservation> reservations = RoomReservationCommunication.getRoomReservationsByUserId(Authenticator.ID);
        for (RoomReservation reservation : reservations) {
            Interval interval = new Interval(reservation.getDate(), reservation.getStartTime(), reservation.getDate(), reservation.getEndTime());
            Entry<RoomReservation> calendarEntry = new Entry<>(reservation.getRoom().getName(), interval);
            calendarEntry.setUserObject(reservation);
            calendar.addEntry(calendarEntry);
        }

        //        CalendarSource calendarSource = new CalendarSource("default");
        //        calendarSource.getCalendars().add(cal);
        //        calendar.getCalendarSources().add(calendarSource);
        calendarView.setShowAddCalendarButton(false);

        // will probably set the calendar to null when deleting entry from calendar
        EventHandler<CalendarEvent> handler = event -> {
            System.out.println("EVEEENT");
            System.out.println(event);
            // can get old dates and times -> set them if the server responds with a 409 conflict
            // also populate the view with unavailable times.
            // TODO
        };

        calendar.addEventHandler(handler);

        // add new calendar for unavailable times
        List<Room> allRooms = RoomCommunication.getRooms();
        CalendarSource calendarSource = new CalendarSource("Unavailable Rooms");
        calendarView.getCalendarSources().add(calendarSource);
        for (Room room : allRooms) {
            Calendar calendarUnavailableRoom = new Calendar(room.getName());
            calendarUnavailableRoom.setReadOnly(true);
            Calendar.Style style = Calendar.Style.STYLE2;
            calendarUnavailableRoom.setStyle(style);
            for (RoomReservation reservation : RoomReservationCommunication.getAllRoomReservationTimesPerRoom(room.getId())) {
                Interval interval = new Interval(reservation.getDate(), reservation.getStartTime(), reservation.getDate(), reservation.getEndTime());
                Entry<RoomReservation> calendarEntry = new Entry<>(reservation.getRoom().getName(), interval);
                calendarEntry.setUserObject(reservation);
                calendarUnavailableRoom.addEntry(calendarEntry);
            }
            calendarSource.getCalendars().add(calendarUnavailableRoom);
            calendarView.setCalendarVisibility(calendarUnavailableRoom, false);
        }

    }

    public void setController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }
}
