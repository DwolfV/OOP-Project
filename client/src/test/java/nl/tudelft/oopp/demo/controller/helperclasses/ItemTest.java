package nl.tudelft.oopp.demo.controller.helperclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.oopp.demo.helperclasses.Equipment;
import nl.tudelft.oopp.demo.helperclasses.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemTest {

    private Item i1;
    private Item i2;
    private Item i3;

    private ArrayList<Equipment> list1;
    private ArrayList<Equipment> list2;

    /**
     * Save items so they can be used in the tests.
     */
    @BeforeEach
    public void save() {
        Equipment e1 = new Equipment(1, 1, 1, 1);
        Equipment e2 = new Equipment(0, 0, 0, 0);
        Equipment e3 = new Equipment(0, 1, 0, 1);
        list1 = new ArrayList<>(List.of(e1, e2, e3));
        list2 = new ArrayList<>(List.of(e1, e2));
        i1 = new Item("name1", list1);
        i2 = new Item("name2", list1);
        i3 = new Item("name3", list2);
    }

    /**
     * Test the constructor to see if it is not null and if the getters return the correct values.
     */
    @Test
    public void testConstructor() {
        assertNotNull(i1);
        assertEquals("name1", i1.getName());
        assertEquals(list1, i2.getEquipmentList());
        assertEquals(list2, i3.getEquipmentList());
    }
}