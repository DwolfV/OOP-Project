package nl.tudelft.oopp.demo.helperclasses;

import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;

import java.util.List;

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
