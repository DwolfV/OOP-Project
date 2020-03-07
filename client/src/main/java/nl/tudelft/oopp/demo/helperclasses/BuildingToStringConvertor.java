package nl.tudelft.oopp.demo.helperclasses;



import javafx.util.StringConverter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

//@FacesConverter(forClass=Building.class, value="building")
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
