package nl.tudelft.oopp.demo.controller.helperclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalTime;

import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RestaurantTest {

    private Restaurant r1;
    private Restaurant r2;
    private Restaurant r3;

    private Building b1;
    private Building b2;

    /**
     * Save restaurants so they can be used in the tests.
     */
    @BeforeEach
    public void save() {
        b1 = new Building("name1", "streetName1", "streetNumber1", "zipCode1", "city1");
        b2 = new Building("name1", "streetName1", "streetNumber1", "zipCode1", "city1");
        r1 = new Restaurant("name1", b1, LocalTime.parse("13:00"), LocalTime.parse("14:00"));
        r2 = new Restaurant("name2", b1, LocalTime.parse("14:00"), LocalTime.parse("15:00"));
        r3 = new Restaurant("name3", b2, LocalTime.parse("13:00"), LocalTime.parse("14:00"));
    }

    /**
     * Test the constructor to see if it is not null and if the getters return the correct values.
     */
    @Test
    public void testConstructor() {
        assertNotNull(r1);
        assertEquals("name1", r1.getName());
        assertEquals(LocalTime.parse("15:00"), r2.getTimeOpen());
        assertEquals(b2, r3.getBuilding());
    }
}