package nl.tudelft.oopp.demo.helperclasses;

import javafx.util.StringConverter;

public class BuildingToStringConvertor extends StringConverter {

    @Override
    public String toString(Object object) {
        Building b = (Building) object;
        return b.getName();
    }

    @Override
    public Object fromString(String string) {
        return null;
    }
}
