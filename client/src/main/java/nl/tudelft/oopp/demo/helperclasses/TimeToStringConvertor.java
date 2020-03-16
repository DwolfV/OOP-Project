package nl.tudelft.oopp.demo.helperclasses;

import javafx.util.StringConverter;

import java.sql.Time;

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