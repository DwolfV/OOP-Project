package nl.tudelft.oopp.demo.helperclasses;

import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.communication.SupplyReservationCommunication;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.Supply;
import nl.tudelft.oopp.demo.entities.SupplyReservation;

import java.util.List;

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
