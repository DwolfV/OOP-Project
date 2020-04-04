package nl.tudelft.oopp.demo.helperclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Occasion;
import org.junit.jupiter.api.Test;

public class DateToStringConverterTest {

    private Building b1 = new Building("name1", LocalTime.parse("08:00:00"), LocalTime.parse("20:00:00"), "street1", "streetNumber1", "zipCode1", "city1");
    private Occasion occasion = new Occasion(LocalDate.parse("2020-01-01"), LocalTime.parse("08:00:00"), LocalTime.parse("20:00:00"), b1);
    private LocalDate localDate = LocalDate.parse("2020-01-01");

    @Test
    public void testToString() {
        DateToStringConverter dateConverter = new DateToStringConverter();
        LocalDate expected = occasion.getDate();
        String actual = dateConverter.toString(localDate);

        assertEquals(expected.toString(), actual);
    }

}
