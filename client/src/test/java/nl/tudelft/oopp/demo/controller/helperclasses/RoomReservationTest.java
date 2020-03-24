package nl.tudelft.oopp.demo.controller.helperclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Room;
import nl.tudelft.oopp.demo.helperclasses.RoomReservation;
import nl.tudelft.oopp.demo.helperclasses.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoomReservationTest {

    private RoomReservation rr1;
    private RoomReservation rr2;
    private RoomReservation rr3;

    private User u1;
    private User u2;

    private Room r1;
    private Room r2;

    private Building b1;
    private Building b2;

    /**
     * Save restaurants so they can be used in the tests.
     */
    @BeforeEach
    public void save() {
        u1 = new User("email1", "student", "firstName1", "lastName1");
        u2 = new User("email2", "employee", "firstName2", "lastName2");
        b1 = new Building("name1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"streetName1", "streetNumber1", "zipCode1", "city1");
        b2 = new Building("name1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"streetName1", "streetNumber1", "zipCode1", "city1");
        r1 = new Room("name1", 1, b1);
        r2 = new Room("name2", 2, b2);
        rr1 = new RoomReservation(new Date(1), new Time(2), new Time(3), u1);
        rr2 = new RoomReservation(new Date(2), new Time(3), new Time(4), u1);
        rr3 = new RoomReservation(new Date(3), new Time(4), new Time(5), u2);
    }

    /**
     * Test the constructor to see if it is not null and if the getters return the correct values.
     */
    @Test
    public void testConstructor() {
        assertNotNull(rr1);
        assertEquals(new Time(3), rr2.getStartTime());
        assertEquals(u1, rr1.getUser());
        assertEquals(u2, rr3.getUser());
    }
}