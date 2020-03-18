package nl.tudelft.oopp.demo.controller.helperclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.oopp.demo.helperclasses.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DishTest {

    private Dish d1;
    private Dish d2;
    private Dish d3;

    /**
     * Save dishes so they can be used in the tests.
     */
    @BeforeEach
    public void save() {
        d1 = new Dish("name1", "desc1", "type1", 1);
        d2 = new Dish("name2", "desc2", "type2", 2);
        d3 = new Dish("name3", "desc3", "type3", 3);
    }

    /**
     * Test the constructor to see if it is not null and if the getters return the correct values.
     */
    @Test
    public void testConstructor() {
        assertNotNull(d1);
        assertEquals("name1", d1.getName());
        assertEquals("desc2", d2.getDescription());
        assertTrue(3 == d3.getPrice());
    }

    /**
     * Test the equals method.
     */
    @Test
    public void testEquals() {
        Dish d1Equals = new Dish("name1", "desc1", "type1", 1);
        assertEquals(d1Equals, d1);
    }
}
