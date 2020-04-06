package nl.tudelft.oopp.demo.helperclasses;

import java.util.List;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.entities.Room;

public class RoomBuildingNameToStringConverter extends StringConverter {

    @Override
    public String toString(Object object) {
        Room r = (Room) object;
        return "Room: " + r.getName() + ", " + "Building: " + r.getBuilding().getName();
    }

    @Override
    public Object fromString(String string) {
        List<Room> roomList = RoomCommunication.getRooms();
        for (Room r : roomList) {
            if (r.getName().equals(string)) {
                return r;
            }
        }
        return null;
    }
}
