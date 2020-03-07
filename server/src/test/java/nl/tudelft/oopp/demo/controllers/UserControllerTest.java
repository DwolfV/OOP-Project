package nl.tudelft.oopp.demo.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@DataJpaTest
public class UserControllerTest {

    private User u1;
    private User u2;
    private User u3;
    private User u4;
    private User u5;

    @Mock
    private UserRepository userRep;

    @InjectMocks
    private UserController userCon;

    /**
     * Creates all users and is done before each test.
     */
    @BeforeEach
    public void save() {
        u1 = new User("user1@email.com", "student", "fn1", "ln1", new Date(1000), "user1");
        u2 = new User("user2@email.com", "student", "fn2", "ln2", new Date(2000), "user2");
        u3 = new User("user3@email.com", "student", "fn3", "ln3", new Date(3000), "user3");
        u4 = new User("user4@email.com", "employee", "fn4", "ln4", new Date(4000), "user4");
        u5 = new User("user5@email.com", "employee", "fn5", "ln5", new Date(5000), "user5");
    }

    /**
     * Test if the controller loads correctly and isn't null.
     *
     * @throws Exception exception
     */
    @Test
    public void testLoadController() throws Exception {
        assertThat(userCon).isNotNull();
    }

    /**
     * Checks whether getAllUsers returns the whole list.
     */
    @Test
    public void testGetAllUsersTest() {
        List<User> expectList = new ArrayList<User>(List.of(u1, u2, u3, u4, u5));

        when(userRep.findAll()).thenReturn(expectList);

        List<User> actualList = userCon.getAllUsers();

        assertEquals(expectList, actualList);
    }

    /**
     * Checks whether getUserById returns the correct user.
     */
    @Test
    public void testGetUserById() {
        Optional<User> optionalU1 = Optional.of(u1);
        ResponseEntity<User> entityU1 = ResponseEntity.of(optionalU1);

        when(userRep.findById(u1.getId())).thenReturn(optionalU1);

        assertEquals(entityU1, userCon.getUserById(u1.getId()));
    }

    /**
     * Checks whether updateUser() updates the user.
     */
    @Test
    public void testUpdateUser() {
        User newU1 = new User("newuser1@email.com",
                "student",
                "newFn1",
                "newLn1",
                new Date(1001), "newuser1");

        UriComponentsBuilder b = UriComponentsBuilder.newInstance();

        // User object to Optional and to ResponseEntity
        Optional<User> optionalU1 = Optional.of(u1);
        ResponseEntity<User> entityU1 = ResponseEntity.of(optionalU1);
        // the new User object to Optional and to ResponseEntity
        Optional<User> newOptionalU1 = Optional.of(newU1);
        ResponseEntity<User> newEntityU1 = ResponseEntity.of(newOptionalU1);

        // specify what to return when save() and findById() is used in the userRepository
        when(userRep.save(newU1)).thenReturn(newU1);
        when(userRep.findById(u1.getId())).thenReturn(optionalU1);

        // Use getBody() since you do not care about the status code
        assertEquals(newEntityU1.getBody(), userCon.updateUser(newU1, u1.getId(), b).getBody());
    }

    /**
     * Checks whether deleteUser() actually deletes a user.
     */
    @Test
    public void testDeleteUser() {
        List<User> actualList = new ArrayList<User>(List.of(u1, u3, u4, u5));
        List<User> expectedList = new ArrayList<User>(List.of(u1, u2, u3, u4, u5));

        // User object to Optional and to ResponseEntity
        Optional<User> optionalU2 = Optional.of(u2);
        ResponseEntity<User> entityU2 = ResponseEntity.of(optionalU2);

        userCon.deleteUser(u2.getId());
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(1);
                return null;
            }
        }).when(userRep).deleteById(u2.getId());
        when(userRep.findAll()).thenReturn(expectedList);

        assertEquals(expectedList, userCon.getAllUsers());
    }

    /**
     * Checks whether adding a new user returns the correct user.
     */
    @Test
    public void testNewUser() {

        UriComponentsBuilder b = UriComponentsBuilder.newInstance();

        // new user and turn User object to Optional and to ResponseEntity
        User u6 = new User("user6@email.com", "student", "fn6", "ln6", new Date(1000), "newuser1");
        Optional<User> optionalU6 = Optional.of(u6);
        ResponseEntity<User> entityU6 = ResponseEntity.of(optionalU6);

        when(userRep.save(u6)).thenReturn(u6);

        assertEquals(u6, userCon.newUser(u6, b).getBody());
    }
}
