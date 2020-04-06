package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BuildingTest {

    private Building b1;
    private Building b2;
    private Building b3;

    /**
     * Save buildings so they can be used in the tests.
     */
    @BeforeEach
    public void save() {
        b1 = new Building("name1", LocalTime.parse("08:00"), LocalTime.parse("20:00"), "streetName1", "streetNumber1", "zipCode1", "city1");
        b2 = new Building("name2", LocalTime.parse("08:00"), LocalTime.parse("20:00"), "streetName2", "streetNumber2", "zipCode2", "city2");
        b3 = new Building("name3", LocalTime.parse("08:00"), LocalTime.parse("20:00"), "streetName3", "streetNumber3", "zipCode3", "city3");
        b1.setId(1L);
        b2.setId(2L);
        b3.setId(3L);
    }

    /**
     * Test the constructor to see if it is not null and if the getters return the correct values.
     */
    @Test
    public void testConstructor() {
        assertNotNull(b1);
        assertEquals("name1", b1.getName());
        assertEquals("streetName2", b2.getStreetName());
        assertEquals("streetNumber3", b3.getStreetNumber());
    }

    /**
     * Test the equals method.
     */
    @Test
    public void testEquals() {
        Building b1Equals = new Building("name1", LocalTime.parse("08:00"), LocalTime.parse("20:00"), "streetName1", "streetNumber1", "zipCode1", "city1");
        assertEquals(b1Equals, b1);
    }
}
