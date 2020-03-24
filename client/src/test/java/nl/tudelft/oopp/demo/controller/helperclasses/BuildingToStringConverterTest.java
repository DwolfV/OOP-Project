package nl.tudelft.oopp.demo.controller.helperclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.BuildingToStringConvertor;
import org.junit.jupiter.api.Test;

public class BuildingToStringConverterTest {

    private Building b1 = new Building("name1", LocalTime.parse("08:00"), LocalTime.parse("20:00"), "street1", "streetNumber1", "zipCode1", "city1");
    private Building b2 = new Building("name2", LocalTime.parse("08:00"), LocalTime.parse("20:00"), "street2", "streetNumber2", "zipCode2", "city2");

    @Test
    public void testToString() {
        BuildingToStringConvertor buildConverter = new BuildingToStringConvertor();
        String expected = b1.getName();
        String actual = buildConverter.toString(b1);

        assertEquals(expected, actual);
    }

}
