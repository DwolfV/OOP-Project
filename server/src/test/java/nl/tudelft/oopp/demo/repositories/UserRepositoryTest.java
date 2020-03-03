package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
public class UserRepositoryTest {

    List<User> u1;
    List<User> u2;
    List<User> u3;
    List<User> u4;
    List<User> u5;

    @Autowired
    private UserRepository userRep;

    @BeforeEach
    private void save() {
        u1 = new ArrayList<User>(List.of(new User(1, "user1@email.com", "student", "fn2", "ln1", new Date(1000))));
        u2 = new ArrayList<User>(List.of(new User(2, "user2@email.com", "student", "fn3", "ln2", new Date(2000))));
        u3 = new ArrayList<User>(List.of(new User(3, "user3@email.com", "student", "fn4", "ln3", new Date(3000))));
        u4 = new ArrayList<User>(List.of(new User(4, "user4@email.com", "employee", "fn5", "ln4", new Date(4000))));
        u5 = new ArrayList<User>(List.of(new User(5, "user5@email.com", "employee", "fn6", "ln5", new Date(5000))));
        userRep.saveAll(u1);
        userRep.saveAll(u1);
        userRep.saveAll(u1);
        userRep.saveAll(u1);
        userRep.saveAll(u1);
    }

    @Test
    private void getUserByEmail() {
        assertEquals(u1, userRep.findByEmail("user1@email.com"));
        assertEquals(u2, userRep.findByEmail("user2@email.com"));
        assertEquals(u3, userRep.findByEmail("user3@email.com"));
        assertEquals(u4, userRep.findByEmail("user4@email.com"));
        assertEquals(u5, userRep.findByEmail("user5@email.com"));
        assertEquals(null, userRep.findByEmail("invalid_email"));
        assertNotEquals(null, userRep.findByEmail("user1@email.com"));
    }

    @Test
    private void findUserByRole() {
        List<User> sList = new ArrayList<User>();
        sList.addAll(u1);
        sList.addAll(u2);
        sList.addAll(u3);
        List<User> eList = new ArrayList<User>();
        eList.addAll(u4);
        eList.addAll(u5);
        assertEquals(sList, userRep.findByRole("student"));
        assertEquals(eList, userRep.findByRole("employee"));
    }
}
