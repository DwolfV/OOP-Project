package nl.tudelft.oopp.demo.helperclasses;

import java.sql.Time;
import javafx.util.StringConverter;

import javafx.util.StringConverter;

public class TimeToStringConvertor extends StringConverter {

    @Override
    public String toString(Object object) {
        Time t = (Time) object;
        return t.getHours() + ": " + t.getMinutes();
    }

    @Override
    public Object fromString(String string) {
        return null;
    }
}