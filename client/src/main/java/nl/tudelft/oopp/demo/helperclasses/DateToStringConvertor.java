package nl.tudelft.oopp.demo.helperclasses;

import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateToStringConvertor extends StringConverter {

    @Override
    public String toString(Object object) {
        LocalDate t = (LocalDate) object;
        return t.format(DateTimeFormatter.ISO_DATE);
    }

    @Override
    public Object fromString(String string) {
        return LocalDate.parse(string);
    }
}