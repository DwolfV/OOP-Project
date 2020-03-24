package nl.tudelft.oopp.demo.helperclasses;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.util.StringConverter;

import javafx.util.StringConverter;

public class TimeToStringConvertor extends StringConverter {

    @Override
    public String toString(Object object) {
        LocalTime t = (LocalTime) object;
        return t.format(DateTimeFormatter.ISO_TIME);
    }

    @Override
    public Object fromString(String string) {
        return null;
    }
}