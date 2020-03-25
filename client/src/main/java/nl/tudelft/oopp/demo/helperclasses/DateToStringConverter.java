package nl.tudelft.oopp.demo.helperclasses;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.util.StringConverter;

public class DateToStringConverter extends StringConverter {
<<<<<<< HEAD
=======

>>>>>>> development
    @Override
    public String toString(Object object) {
        LocalDate t = (LocalDate) object;
        return t.format(DateTimeFormatter.ISO_DATE);
    }

    @Override
    public Object fromString(String string) {
        return LocalDate.parse(string);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> development
