package nl.tudelft.oopp.demo.helperclasses;

import javafx.util.StringConverter;
import nl.tudelft.oopp.demo.communication.ItemCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.entities.Item;
import nl.tudelft.oopp.demo.entities.Room;

import java.util.List;

public class ItemToStringConverter extends StringConverter {

    @Override
    public String toString(Object object) {
        Item i = (Item) object;
        return i.getName();
    }

    @Override
    public Object fromString(String string) {
        List<Item> itemList = ItemCommunication.getItems();
        for (Item i : itemList) {
            if (i.getName().equals(string)) {
                return i;
            }
        }
        return null;
    }
}
