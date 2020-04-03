package nl.tudelft.oopp.demo.helperclasses;

import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildingToStringConverterTest {

    private Building b1 = new Building("name1", LocalTime.parse("08:00"), LocalTime.parse("20:00"), "street1", "streetNumber1", "zipCode1", "city1");
    private Building b2 = new Building("name2", LocalTime.parse("08:00"), LocalTime.parse("20:00"), "street2", "streetNumber2", "zipCode2", "city2");

    @Test
    public void testToString() {
        BuildingToStringConverter buildConverter = new BuildingToStringConverter();
        String expected = b1.getName();
        String actual = buildConverter.toString(b1);

        assertEquals(expected, actual);
    }

}
