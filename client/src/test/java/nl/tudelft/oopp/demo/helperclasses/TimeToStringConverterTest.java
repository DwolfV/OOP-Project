package nl.tudelft.oopp.demo.helperclasses;

import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeToStringConverterTest {

    private Building b1 = new Building("name1", LocalTime.parse("08:00:00"), LocalTime.parse("20:00:00"), "street1", "streetNumber1", "zipCode1", "city1");
    private LocalTime localTime = LocalTime.parse("08:00:00");

    @Test
    public void testToString() {
        TimeToStringConverter timeConverter = new TimeToStringConverter();
        LocalTime expected = b1.getOpenTime();
        String actual = timeConverter.toString(localTime);

        assertEquals(expected.format(DateTimeFormatter.ISO_TIME), actual);
    }

}
