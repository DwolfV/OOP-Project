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
        u1 = new User(1, "user1@email.com", "student", "fn1", "ln1", new Date(1000));
        u2 = new User(2, "user2@email.com", "student", "fn2", "ln2", new Date(2000));
        u3 = new User(3, "user3@email.com", "student", "fn3", "ln3", new Date(3000));
        u4 = new User(4, "user4@email.com", "employee", "fn4", "ln4", new Date(4000));
        u5 = new User(5, "user5@email.com", "employee", "fn5", "ln5", new Date(5000));
    }

    /**
     * Test if the controller loads correctly and isn't null.
     *
     * @throws Exception exception
     */
    @Test
    public void controllerLoads() throws Exception {
        assertThat(userCon).isNotNull();
    }

    /**
     * Checks whether getAllUsers returns the whole list.
     */
    @Test
    public void getAllUsersTest() {
        List<User> expectList = new ArrayList<User>(List.of(u1, u2, u3, u4, u5));

        // Specify what the repository should return when you call the
        // findAll() method which is done in the userController for the method we are testing
        when(userRep.findAll()).thenReturn(expectList);

        // now call that method in the userController and put it into the actualList
        List<User> actualList = userCon.getAllUsers();

        assertEquals(expectList, actualList);
    }

    /**
     * Checks whether getUserById returns the correct user.
     */
    @Test
    public void getUserById() {
        // User object to Optional and to ResponseEntity
        Optional<User> optionalU1 = Optional.of(u1);
        ResponseEntity<User> entityU1 = ResponseEntity.of(optionalU1);

        // If findById(1l) method is called from userRep
        // return the optional for u1 (since u1 has the id 1l)
        when(userRep.findById(1L)).thenReturn(optionalU1);

        assertEquals(entityU1, userCon.getUserById(1L));
    }

    /**
     * Checks whether updateUser() updates the user.
     */
    @Test
    public void updateUser() {
        User newU1 = new User(1,
                "newuser1@email.com",
                "student",
                "newFn1",
                "newLn1",
                new Date(1001));

        UriComponentsBuilder b = UriComponentsBuilder.newInstance();

        // User object to Optional and to ResponseEntity
        Optional<User> optionalU1 = Optional.of(u1);
        ResponseEntity<User> entityU1 = ResponseEntity.of(optionalU1);
        // the new User object to Optional and to ResponseEntity
        Optional<User> newOptionalU1 = Optional.of(newU1);
        ResponseEntity<User> newEntityU1 = ResponseEntity.of(newOptionalU1);

        // specify what to return when save() and findById() is used in the userRepository
        when(userRep.save(newU1)).thenReturn(newU1);
        when(userRep.findById(1L)).thenReturn(optionalU1);

        // Use getBody() since you do not care about the status code
        assertEquals(newEntityU1.getBody(), userCon.updateUser(newU1, 1, b).getBody());
    }

    /**
     * Checks whether deleteUser() actually deletes a user.
     */
    @Test
    public void deleteUser() {
        List<User> actualList = new ArrayList<User>(List.of(u1, u3, u4, u5));
        List<User> expectedList = new ArrayList<User>(List.of(u1, u2, u3, u4, u5));

        // User object to Optional and to ResponseEntity
        Optional<User> optionalU2 = Optional.of(u2);
        ResponseEntity<User> entityU2 = ResponseEntity.of(optionalU2);

        userCon.deleteUser(2L);
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(1);
                return null;
            }
        }).when(userRep).deleteById(2L);
        when(userRep.findAll()).thenReturn(expectedList);

        assertEquals(expectedList, userCon.getAllUsers());
    }

    /**
     * Checks whether adding a new user returns the correct user.
     */
    @Test
    public void newUser() {

        UriComponentsBuilder b = UriComponentsBuilder.newInstance();

        // new user and turn User object to Optional and to ResponseEntity
        User u6 = new User(6, "user6@email.com", "student", "fn6", "ln6", new Date(1000));
        Optional<User> optionalU6 = Optional.of(u6);
        ResponseEntity<User> entityU6 = ResponseEntity.of(optionalU6);

        when(userRep.save(u6)).thenReturn(u6);

        assertEquals(u6, userCon.newUser(u6, b).getBody());
    }
}
