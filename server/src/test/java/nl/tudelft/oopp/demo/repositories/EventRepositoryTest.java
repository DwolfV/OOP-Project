package nl.tudelft.oopp.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Event;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class EventRepositoryTest {
    private User u1;
    private User u2;

    private Event e1;
    private Event e2;
    private Event e3;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    /**
     * Creates all users, events, adds them to their repositories and is done before each test.
     */
    @BeforeEach
    public void save() {
        u1 = new User("user1@email.com", "student", "fn1", "ln1", "user1");
        u2 = new User("user2@email.com", "student", "fn2", "ln2", "user2");

        userRepository.save(u1);
        userRepository.save(u2);

        e1 = new Event("BBQ", "Out with the boys, having a nice steak", LocalDate.parse("2019-01-01"), LocalTime.parse("10:00"), LocalTime.parse("13:00"), u1);
        e2 = new Event("Beers", "Out with the boys, having a nice beer", LocalDate.parse("2019-01-02"), LocalTime.parse("15:00"), LocalTime.parse("17:00"), u1);
        e3 = new Event("Match", "Out with the boys, watching a nice match", LocalDate.parse("2019-01-03"), LocalTime.parse("10:00"), LocalTime.parse("12:00"), u2);

        eventRepository.save(e1);
        eventRepository.save(e2);
        eventRepository.save(e3);
    }

    @Test
    public void testLoadRepository() {
        assertThat(userRepository).isNotNull();
        assertThat(eventRepository).isNotNull();
    }

    @Test
    public void findAllTest() {
        List<Event> eventList = new ArrayList<Event>(List.of(e1, e2, e3));
        assertEquals(eventList, eventRepository.findAll());
    }

    @Test
    public void findAllByUserIdTest() {
        List<Event> eventList = new ArrayList<Event>(List.of(e1, e2));
        assertEquals(eventList, eventRepository.findByUserId(u1.getId()));
    }
}
