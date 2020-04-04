package nl.tudelft.oopp.demo.helperclasses;

import java.util.List;
import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.SupplyCommunication;
import nl.tudelft.oopp.demo.entities.Supply;

public class SupplyToStringConverter  extends StringConverter {

    @Override
    public String toString(Object object) {
        Supply s = (Supply) object;
        return s.getName();
    }

    @Override
    public Object fromString(String string) {
        List<Supply> supplyList = SupplyCommunication.getSupplies();
        for (Supply s : supplyList) {
            if (s.getName().equals(string)) {
                return s;
            }
        }
        // TODO what happens when the admin tries to change the building name to a name that does not exist?
        return null;
    }
}