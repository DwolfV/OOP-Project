package nl.tudelft.oopp.demo.helperclasses;

import javafx.util.StringConverter;

import java.sql.Time;

public class TimeToStringConverter extends StringConverter {

    @Override
    public String toString(Object object) {
        Time t = (Time) object;
        return t.getHours() + ":" + t.getMinutes() + ":" + t.getSeconds();
    }

    @Override
    public Object fromString(String string) {
        return null;
    }
}