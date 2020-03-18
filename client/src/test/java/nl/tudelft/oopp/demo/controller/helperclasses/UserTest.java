package nl.tudelft.oopp.demo.controller.helperclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import nl.tudelft.oopp.demo.helperclasses.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User u1;
    private User u2;
    private User u3;

    /**
     * Save restaurants so they can be used in the tests.
     */
    @BeforeEach
    public void save() {
        u1 = new User("email1", "student", "firstName1", "lastName1");
        u2 = new User("email2", "student", "firstName2", "lastName2");
        u3 = new User("email3", "employee", "firstName3", "lastName3");
    }

    /**
     * Test the constructor to see if it is not null and if the getters return the correct values.
     */
    @Test
    public void testConstructor() {
        assertNotNull(u1);
        assertEquals("email1", u1.getEmail());
        assertEquals("student", u2.getRole());
        assertEquals("employee", u3.getRole());
    }
}