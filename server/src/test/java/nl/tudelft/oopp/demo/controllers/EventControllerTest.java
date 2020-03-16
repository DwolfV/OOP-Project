package nl.tudelft.oopp.demo.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Event;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.EventRepository;
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
public class EventControllerTest {
    private User u1;
    private User u2;

    private Event e1;
    private Event e2;
    private Event e3;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    public void save() {
        u1 = new User("user1@email.com", "student", "fn1", "ln1", new Date(1000), "user1");
        u2 = new User("user2@email.com", "student", "fn2", "ln2", new Date(2000), "user2");

        e1 = new Event("BBQ", "Out with the boys, having a nice steak", new Date(2000), new Time(10), new Time(30), u1);
        e2 = new Event("Beers", "Out with the boys, having a nice beer", new Date(3000), new Time(15), new Time(25), u1);
        e3 = new Event("Match", "Out with the boys, watching a nice match", new Date(4000), new Time(10), new Time(12), u2);
    }

    @Test
    public void testLoadController() throws Exception {
        assertThat(eventController).isNotNull();
    }

    @Test
    public void testGetAllEvents() {
        List<Event> expectedList = new ArrayList<Event>(List.of(e1, e2, e3));
        when(eventRepository.findAll()).thenReturn(expectedList);
        List<Event> actualList = eventController.getAllEvents();

        assertEquals(expectedList, actualList);
    }

    @Test
    public void testGetEventsByUserId() {
        List<Event> expectedList = new ArrayList<Event>(List.of(e1, e2));
        when(eventRepository.findByUserId(u1.getId())).thenReturn(expectedList);
        List<Event> actualList = eventController.getEventsByUserId(u1.getId());

        assertEquals(expectedList, actualList);
    }

    @Test
    public void testGetEventById() {
        Optional<Event> optionalEvent = Optional.of(e1);
        ResponseEntity<Event> entity = ResponseEntity.of(optionalEvent);

        when(eventRepository.findById(e1.getId())).thenReturn(optionalEvent);
        assertEquals(entity, eventController.getEventsById(e1.getId()));
    }

    @Test
    public void testAddNewEvent() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        User u1 = new User("user1@email.com", "student", "fn1", "ln1", new Date(1000), "user1");
        Event event = new Event("BBQ", "Out with the boys, having a nice steak", new Date(2000), new Time(10), new Time(30), u1);
        Optional<Event> optionalEvent = Optional.of(event);
        ResponseEntity<Event> responseEntity = ResponseEntity.of(optionalEvent);

        when(eventRepository.save(event)).thenReturn(event);

        assertEquals(event, eventController.addEvent(event, uriComponentsBuilder).getBody());
    }

    @Test
    public void testUpdateEvent() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        User u1 = new User("user1@email.com", "student", "fn1", "ln1", new Date(1000), "user1");
        Event event = new Event("BBQ", "Out with the boys, having a nice steak", new Date(2000), new Time(10), new Time(30), u1);

        Optional<Event> optionalEvent = Optional.of(e1);
        Optional<Event> newOptionalEvent = Optional.of(event);

        ResponseEntity<Event> entity = ResponseEntity.of(optionalEvent);
        ResponseEntity<Event> newEntity = ResponseEntity.of(newOptionalEvent);

        when(eventRepository.save(event)).thenReturn(event);
        when(eventRepository.findById(e1.getId())).thenReturn(optionalEvent);

        assertEquals(newEntity.getBody(), eventController.updateEvent(e1.getId(), event).getBody());
    }

    @Test
    public void testDeleteEvent() {
        List<Event> actualList = new ArrayList<Event>(List.of(e1, e3));
        List<Event> testList = new ArrayList<Event>(List.of(e1, e2, e3));

        Optional<Event> optionalEvent = Optional.of(e2);
        ResponseEntity<Event> responseEntity = ResponseEntity.of(optionalEvent);

        eventController.deleteEvent(e2.getId());
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                actualList.remove(1);
                return null;
            }
        }).when(eventRepository).deleteById(e2.getId());
        when(eventRepository.findAll()).thenReturn(testList);
        assertEquals(testList, eventController.getAllEvents());
    }
}
