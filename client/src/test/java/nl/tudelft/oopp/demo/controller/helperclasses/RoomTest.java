package nl.tudelft.oopp.demo.controller.helperclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalTime;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoomTest {

    private Room r1;
    private Room r2;
    private Room r3;

    private Building b1;
    private Building b2;

    /**
     * Save restaurants so they can be used in the tests.
     */
    @BeforeEach
    public void save() {
        b1 = new Building("name1", LocalTime.parse("08:00"), LocalTime.parse("20:00"), "streetName1", "streetNumber1", "zipCode1", "city1");
        b2 = new Building("name1", LocalTime.parse("08:00"), LocalTime.parse("20:00"), "streetName1", "streetNumber1", "zipCode1", "city1");
        r1 = new Room("name1", 1, b1);
        r2 = new Room("name2", 2, b1);
        r3 = new Room("name3", 3, b2);
    }

    /**
     * Test the constructor to see if it is not null and if the getters return the correct values.
     */
    @Test
    public void testConstructor() {
        assertNotNull(r1);
        assertEquals("name1", r1.getName());
        assertEquals(2, r2.getCapacity());
        assertEquals(b2, r3.getBuilding());
    }
}