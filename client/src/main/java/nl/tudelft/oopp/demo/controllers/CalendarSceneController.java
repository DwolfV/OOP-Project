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
    private int flag = 0;

    private EventHandler<CalendarEvent> handler;

    @FXML
    private CalendarView calendarView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        calendarView.setPrefWidth(screenBounds.getWidth() - 200);
        calendarView.setPrefHeight(screenBounds.getHeight() - 170);
        // will get the Default calendar
        Calendar calendar = calendarView.getCalendarSources().get(0).getCalendars().get(0);

        calendarView.setShowAddCalendarButton(false);

        handler = event -> {
            System.out.println(event);
            if (flag == 0) {
                // can get old dates and times -> set them if the server responds with a 409 conflict
                // TODO too much event.getEntry
                flag = 1;
                Entry entry = event.getEntry();
                if (event.isEntryRemoved() && event.getCalendar() == null) {

                    RoomReservation reservationToRemove = (RoomReservation) entry.getUserObject();
                    RoomReservationCommunication.removeRoomReservation(reservationToRemove.getId());

                } else if (event.getOldCalendar() == null && event.getEventType() == CalendarEvent.ENTRY_INTERVAL_CHANGED
                    && !(event.getOldInterval().equals(entry.getInterval()))) {

                    // if the user did not change the calendar of the room
                    RoomReservation reservationToUpdate = (RoomReservation) entry.getUserObject();
                    boolean succeed = RoomReservationCommunication.updateRoomReservation(reservationToUpdate.getId(),
                        entry.getStartDate(), entry.getStartTime(), entry.getEndTime(),
                        reservationToUpdate.getRoom().getId());

                    if (!succeed) {
                        // need to revert the changes from the entry
                        Interval oldInterval = event.getOldInterval();
                        event.getEntry().setInterval(oldInterval);
                        event.consume();
                        System.out.println("COULD NOT UPDATE THE RESERVATION TIME");
                        flag = 0;
                    }
                }
                flag = 0;
            }
        };

        calendar.addEventHandler(handler);

        // add new calendar for unavailable times
        CalendarSource calendarSource = new CalendarSource("Unavailable Rooms");
        calendarView.getCalendarSources().add(calendarSource);
        init();
    }

    /**
     * This method is used to load and reload the state of the calendar view when the user switched to the calendar scene.
     */
    public void init() {
        //calendarView.getCalendarSources().get(0).getCalendars().get(0).removeEntries();

        // will get the Default calendar
        Calendar calendar = calendarView.getCalendarSources().get(0).getCalendars().get(0);
        calendar.removeEventHandler(handler);
        calendar.clear();
        calendar.addEventHandler(handler);

        List<RoomReservation> reservations = RoomReservationCommunication.getRoomReservationsByUserId(Authenticator.ID);
        for (RoomReservation reservation : reservations) {
            Interval interval = new Interval(reservation.getDate().plusDays(1), reservation.getStartTime(), reservation.getDate().plusDays(1), reservation.getEndTime());
            Entry<RoomReservation> calendarEntry = new Entry<>(reservation.getRoom().getName(), interval);
            calendarEntry.setUserObject(reservation);
            calendar.addEntry(calendarEntry);
        }


        // load the new calendar for unavailable times
        List<Room> allRooms = RoomCommunication.getRooms();

        CalendarSource calendarSource = calendarView.getCalendarSources().get(1);
        // calendarSource.getCalendars().get(0).removeEntries(); try catch?

        calendarSource.getCalendars().clear();

        for (Room room : allRooms) {
            Calendar calendarUnavailableRoom = new Calendar(room.getName());
            calendarUnavailableRoom.setReadOnly(true);
            Calendar.Style style = Calendar.Style.STYLE2;
            calendarUnavailableRoom.setStyle(style);
            for (RoomReservation reservation : RoomReservationCommunication.getAllRoomReservationTimesPerRoom(room.getId())) {
                Interval interval = new Interval(reservation.getDate().plusDays(1), reservation.getStartTime(), reservation.getDate().plusDays(1), reservation.getEndTime());
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
