package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Event;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;


class EventCommunicationTest {

    private ClientAndServer mockServer;
    private ObjectMapper mapper;

    private Event e1;
    private Event e2;
    private Event e3;

    private User u1;
    private User u2;

    private List<Event> events;

    @BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(8080);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        u1 = new User("user1@email.com", "student", "fn1", "ln1");
        u2 = new User("user2@email.com", "student", "fn2", "ln2");

        e1 = new Event("BBQ", "Out with the boys, having a nice steak", LocalDate.parse("2019-11-11"), LocalTime.parse("10:00"), LocalTime.parse("10:30"), u1);
        e2 = new Event("Beers", "Out with the boys, having a nice beer", LocalDate.parse("2019-11-12"), LocalTime.parse("15:00"), LocalTime.parse("15:25"), u1);
        e3 = new Event("Match", "Out with the boys, watching a nice match", LocalDate.parse("2019-11-13"), LocalTime.parse("10:00"), LocalTime.parse("12:00"), u2);

        events = new ArrayList<>(List.of(e1));
    }

    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }

    @Test
    public void getEventByUser() throws JsonProcessingException {
        String body = mapper.writeValueAsString(new ArrayList<Event>(List.of(e1)));
        mockServer.when(request().withPath("/event/user/1")).respond(response().withBody(body));
        List<Event> event = EventCommunication.getEventsByUser(1);
        assertEquals(event, events);
    }
}
