package nl.tudelft.oopp.demo.controller.helperclasses;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.oopp.demo.helperclasses.Equipment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EquipmentTest {

    private Equipment e1;
    private Equipment e2;
    private Equipment e3;

    /**
     * Save equipment so they can be used in the tests.
     */
    @BeforeEach
    public void save() {
        e1 = new Equipment(1, 1, 1,1);
        e2 = new Equipment(0, 0, 0,0);
        e3 = new Equipment(0, 1, 0,1);
    }

    /**
     * Test the constructor to see if it is not null and if the getters return the correct values.
     */
    @Test
    public void testConstructor() {
        assertNotNull(e1);
        assertTrue(1 == e1.getPc());
        assertTrue(0 == e2.getPc());
        assertTrue(0 == e3.getPc());
    }
}