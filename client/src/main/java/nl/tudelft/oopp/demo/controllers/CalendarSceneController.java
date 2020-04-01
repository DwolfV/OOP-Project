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
    private LocalDate previousDate;
    private LocalTime previousStartTime;
    private LocalTime previousEndTime;


    private EventHandler<CalendarEvent> handler;

    @FXML
    private CalendarView calendarView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        calendarView.setPrefWidth(screenBounds.getWidth() - 200);
        calendarView.setPrefHeight(screenBounds.getHeight() - 170);

        //create a new calendar for room reservations
        Calendar calendarRoomReservations = new Calendar("Room Reservations");
        calendarRoomReservations.setStyle(Calendar.Style.STYLE3);
        calendarView.getCalendarSources().get(0).getCalendars().add(calendarRoomReservations);
        // will get the Default calendar
        //Calendar calendar = calendarView.getCalendarSources().get(0).getCalendars().get(0);

        calendarView.setShowAddCalendarButton(false);

        handler = event -> {
            System.out.println(flag);
            if (flag == 0 && (!event.getEntry().getStartDate().equals(previousDate)
                || !event.getEntry().getStartTime().equals(previousStartTime)
                || !event.getEntry().getEndTime().equals(previousEndTime))) {
                flag = 1;
                Entry entry = event.getEntry();

                previousDate = entry.getStartDate();
                previousStartTime = entry.getStartTime();
                previousEndTime = entry.getEndTime();

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

        calendarRoomReservations.addEventHandler(handler);

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
        //Calendar calendar = calendarView.getCalendarSources().get(0).getCalendars().get(0);
        // get the calendar for the room reservations
        Calendar calendarRoomReservations = calendarView.getCalendarSources().get(0).getCalendars().get(1);
        calendarRoomReservations.removeEventHandler(handler);
        calendarRoomReservations.clear();
        calendarRoomReservations.addEventHandler(handler);

        List<RoomReservation> reservations = RoomReservationCommunication.getRoomReservationsByUserId(Authenticator.ID);
        for (RoomReservation reservation : reservations) {
            Interval interval = new Interval(reservation.getDate().plusDays(1), reservation.getStartTime(), reservation.getDate().plusDays(1), reservation.getEndTime());
            Entry<RoomReservation> calendarEntry = new Entry<>(reservation.getRoom().getName(), interval);
            calendarEntry.setUserObject(reservation);
            calendarRoomReservations.addEntry(calendarEntry);
        }


        // load the new calendar for unavailable times
        List<Room> allRooms = RoomCommunication.getRooms();

        CalendarSource calendarSource = calendarView.getCalendarSources().get(1);

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

        // get the default calendar and populate it with the custom events
        Calendar calendarCustomEvents = calendarView.getCalendarSources().get(0).getCalendars().get(0);

    }

    public void setController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }
}
