package nl.tudelft.oopp.demo.helperclasses;

import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.entities.Supply;

public class SupplyReservationRoomToStringConverter extends StringConverter {

    @Override
    public String toString(Object object) {
        Supply s = (Supply) object;
        return "Building: " + s.getBuilding().getName();
    }

    @Override
    public Object fromString(String string) {
        return null;
    }

}
