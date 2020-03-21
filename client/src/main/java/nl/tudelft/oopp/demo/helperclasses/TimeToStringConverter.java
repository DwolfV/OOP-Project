package nl.tudelft.oopp.demo.helperclasses;

import java.sql.Time;
import javafx.util.StringConverter;

public class TimeToStringConverter extends StringConverter {

    @Override
    public String toString(Object object) {
        Time t = (Time) object;
        return t.toString();
    }

    @Override
    public Object fromString(String string) {
        return Time.valueOf(string);
    }
}