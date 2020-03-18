package nl.tudelft.oopp.demo.controller.helperclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Time;

import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.OpenTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OpenTimeTest {

    private OpenTime o1;
    private OpenTime o2;
    private OpenTime o3;

    private Building b1;
    private Building b2;

    /**
     * Save open times so they can be used in the tests.
     */
    @BeforeEach
    public void save() {
        b1 = new Building("name1", "streetName1", "streetNumber1", "zipCode1", "city1");
        b2 = new Building("name1", "streetName1", "streetNumber1", "zipCode1", "city1");
        o1 = new OpenTime("monday", new Time(1), new Time(2), b1);
        o2 = new OpenTime("tuesday", new Time(2), new Time(3), b1);
        o3 = new OpenTime("monday", new Time(1), new Time(2), b2);
    }

    /**
     * Test the constructor to see if it is not null and if the getters return the correct values.
     */
    @Test
    public void testConstructor() {
        assertNotNull(o1);
        assertEquals("monday", o1.getDay());
        assertEquals(new Time(2), o2.getOpenTime());
        assertEquals(b2, o3.getBuilding());
    }
}
