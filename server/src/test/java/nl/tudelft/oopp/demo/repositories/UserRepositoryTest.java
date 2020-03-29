package nl.tudelft.oopp.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

    private List<User> u1;
    private List<User> u2;
    private List<User> u3;
    private List<User> u4;
    private List<User> u5;

    @Autowired
    private UserRepository userRep;

    /**
     * Creates all users and is done before each test.
     */
    @BeforeEach
    public void save() {
        u1 = new ArrayList<User>(List.of(
                new User("user1@email.com", "student", "fn1", "ln1", "user1")));
        u2 = new ArrayList<User>(List.of(
                new User("user2@email.com", "student", "fn2", "ln2", "user2")));
        u3 = new ArrayList<User>(List.of(
                new User("user3@email.com", "student", "fn3", "ln3", "user3")));
        u4 = new ArrayList<User>(List.of(
                new User("user4@email.com", "employee", "fn4", "ln4", "user4")));
        u5 = new ArrayList<User>(List.of(
                new User("user5@email.com", "employee", "fn5", "ln5", "user5")));
        userRep.saveAll(u1);
        userRep.saveAll(u2);
        userRep.saveAll(u3);
        userRep.saveAll(u4);
        userRep.saveAll(u5);
    }

    /**
     * Test if the repositories loads correctly and isn't null.
     *
     * @throws Exception exception
     */
    @Test
    public void testLoadController() throws Exception {
        assertThat(userRep).isNotNull();
    }

    /**
     * Checks whether findByEmail() in the repository
     * finds all the users with a certain email.
     */
    @Test
    public void testFindByEmail() {
        assertEquals(u1, userRep.findByEmail("user1@email.com"));
        assertEquals(u2, userRep.findByEmail("user2@email.com"));
        assertEquals(u3, userRep.findByEmail("user3@email.com"));
        assertEquals(u4, userRep.findByEmail("user4@email.com"));
        assertEquals(u5, userRep.findByEmail("user5@email.com"));
        List<User> emptyList = new ArrayList<User>();
        assertEquals(emptyList, userRep.findByEmail("invalid_email"));
        assertNotEquals(null, userRep.findByEmail("invalid_email"));
        assertNotEquals(emptyList, userRep.findByEmail("user1@email.com"));
        assertNotEquals(null, userRep.findByEmail("user1@email.com"));
    }

    /**
     * Checks whether findByRole() in the repository
     * finds all the users with a certain role.
     */
    @Test
    public void testFindByRole() {
        List<User> slist = new ArrayList<User>();
        slist.addAll(u1);
        slist.addAll(u2);
        slist.addAll(u3);
        List<User> elist = new ArrayList<User>();
        elist.addAll(u4);
        elist.addAll(u5);
        assertEquals(slist, userRep.findByRole("student"));
        assertEquals(elist, userRep.findByRole("employee"));
    }
}
