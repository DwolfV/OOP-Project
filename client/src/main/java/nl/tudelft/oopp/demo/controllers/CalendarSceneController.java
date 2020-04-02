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
import nl.tudelft.oopp.demo.communication.EventCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.communication.RoomReservationCommunication;
import nl.tudelft.oopp.demo.helperclasses.Event;
import nl.tudelft.oopp.demo.helperclasses.Room;
import nl.tudelft.oopp.demo.helperclasses.RoomReservation;

public class CalendarSceneController implements Initializable {

    private MainSceneController mainSceneController;
    // this flag is used to prevent an infinite loop of events
    private int flag = 0;
    // this flag is used to prevent calling the *addEntry* function when the calendar view is populated
    //private int flagInitiallyPopulatingTheScene = 0;
    private LocalDate previousDate;
    private LocalTime previousStartTime;
    private LocalTime previousEndTime;


    private EventHandler<CalendarEvent> handlerRoomReservations;
    private EventHandler<CalendarEvent> handlerEvents;


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
        Calendar calendarCustomEvents = calendarView.getCalendarSources().get(0).getCalendars().get(0);

        calendarView.setShowAddCalendarButton(false);

        handlerRoomReservations = event -> {
            System.out.println(flag + " " + event.isEntryRemoved() + " " + event.getCalendar());
            if (flag == 0 && (!event.getEntry().getStartDate().equals(previousDate)
                || !event.getEntry().getStartTime().equals(previousStartTime)
                || !event.getEntry().getEndTime().equals(previousEndTime))
                && !event.isEntryRemoved()) {
                flag = 1;
                Entry entry = event.getEntry();

                previousDate = entry.getStartDate();
                previousStartTime = entry.getStartTime();
                previousEndTime = entry.getEndTime();
                if (event.getOldCalendar() == null && event.getEventType() == CalendarEvent.ENTRY_INTERVAL_CHANGED
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
            } else if (flag == 0 && event.isEntryRemoved() && event.getCalendar() == null) {
                flag = 1;
                RoomReservation reservationToRemove = (RoomReservation) event.getEntry().getUserObject();
                RoomReservationCommunication.removeRoomReservation(reservationToRemove.getId());
                flag = 0;
            }
        };

        calendarRoomReservations.addEventHandler(handlerRoomReservations);

        handlerEvents = event -> {
            // if it is a new event
            if (event.isEntryAdded()) {
                // if the scene has already been populated and now a user adds an entry
                Thread thread = new Thread(() -> {
                    Event addedEvent = EventCommunication.addEvent(event.getEntry().getTitle(),
                        event.getEntry().getId(), event.getEntry().getStartDate(), event.getEntry().getStartTime(),
                        event.getEntry().getEndTime());
                    event.getEntry().setId((String.valueOf(addedEvent.getId())));
                    System.out.println("entry added ");
                });
                thread.start();
            } else if (event.isEntryRemoved()) {
                // if an entry is deleted
                Thread thread = new Thread(() -> {
                    EventCommunication.removeEvent(Long.parseLong(event.getEntry().getId()));
                    System.out.println("event deleted");
                });
                thread.start();
            } else {
                // if an entry is updated
                Thread thread = new Thread(() -> {
                    EventCommunication.updateEvent(Long.parseLong(event.getEntry().getId()),
                        event.getEntry().getTitle(), event.getEntry().getId(), event.getEntry().getStartDate(),
                        event.getEntry().getStartTime(), event.getEntry().getEndTime());
                    System.out.println("event updated");
                });
                thread.start();
            }
        };

        calendarCustomEvents.addEventHandler(handlerEvents);

        // add new calendar for unavailable times
        CalendarSource calendarSource = new CalendarSource("Unavailable Rooms");
        calendarView.getCalendarSources().add(calendarSource);
        init();
    }

    /**
     * This method is used to load and reload the state of the calendar view when the user switched to the calendar scene.
     */
    public void init() {
        //flagInitiallyPopulatingTheScene = 0;
        //calendarView.getCalendarSources().get(0).getCalendars().get(0).removeEntries();

        // will get the Default calendar
        //Calendar calendar = calendarView.getCalendarSources().get(0).getCalendars().get(0);
        // get the calendar for the room reservations
        Calendar calendarRoomReservations = calendarView.getCalendarSources().get(0).getCalendars().get(1);
        calendarRoomReservations.removeEventHandler(handlerRoomReservations);
        calendarRoomReservations.clear();
        calendarRoomReservations.addEventHandler(handlerRoomReservations);

        List<RoomReservation> reservations = RoomReservationCommunication.getRoomReservationsByUserId(Authenticator.ID);
        for (RoomReservation reservation : reservations) {
            // if it is an old reservation, don't display it
            if (reservation.getDate().plusDays(1).compareTo(LocalDate.now()) < 0) {
                continue;
            }
            Interval interval = new Interval(reservation.getDate().plusDays(1), reservation.getStartTime(), reservation.getDate().plusDays(1), reservation.getEndTime());
            Entry<RoomReservation> calendarEntry = new Entry<>(reservation.getRoom().getName(), interval);
            calendarEntry.setUserObject(reservation);
            calendarRoomReservations.addEntry(calendarEntry);
        }
        // TODO move the calendarRoomReservation.addEventHandler here?

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
                // if it is an old reservation, don't display it
                if (reservation.getDate().plusDays(1).compareTo(LocalDate.now()) < 0) {
                    continue;
                }
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

        // remove the entries and handler
        calendarCustomEvents.removeEventHandler(handlerEvents);
        calendarCustomEvents.clear();
        for (Event event : EventCommunication.getEventsByUser(Authenticator.ID)) {
            Interval interval = new Interval(event.getDate().plusDays(1), event.getStartTime(), event.getDate().plusDays(1), event.getEndTime());
            Entry<Event> calendarEntry = new Entry<>(event.getName(), interval);
            calendarEntry.setId(String.valueOf(event.getId()));
            calendarEntry.setUserObject(event);
            calendarCustomEvents.addEntry(calendarEntry);
        }
        // add the handler to deal with users adding/deleting rooms
        calendarCustomEvents.addEventHandler(handlerEvents);

        //flagInitiallyPopulatingTheScene = 1;
    }

    public void setController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }
}
