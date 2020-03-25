package nl.tudelft.oopp.demo.helperclasses;

import java.util.List;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;

public class BuildingToStringConverter extends StringConverter {

    @Override
    public String toString(Object object) {
        Building b = (Building) object;
        return b.getName();
    }

    @Override
    public Object fromString(String string) {
        List<Building> buildingList = BuildingCommunication.getBuildings();
        for (Building b : buildingList) {
            if (b.getName().equals(string)) {
                return b;
            }
        }
        // TODO what happens when the admin tries to change the building name to a name that does not exist?
        return null;
    }
}
