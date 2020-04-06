package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Occasion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

class OccasionCommunicationTest {

    private ClientAndServer mockServer;
    private ObjectMapper mapper;

    private Occasion occasion;
    private Occasion occasion2;
    private Occasion occasion3;
    private Occasion occasion4;

    private List<Occasion> occasions;
    private List<Occasion> occasions2;

    @BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(8080);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Building b1 = new Building("name1", LocalTime.parse("08:00"), LocalTime.parse("20:00"), "s1", "sNo1", "z1", "c1");
        Building b2 = new Building("name2", LocalTime.parse("08:00"), LocalTime.parse("20:00"), "s2", "sNo2", "z2", "c2");

        occasion = new Occasion(LocalDate.parse("2020-03-24"), LocalTime.parse("10:00"), LocalTime.parse("18:00"), b1);
        occasion2 = new Occasion(LocalDate.parse("2020-02-24"), LocalTime.parse("09:00"), LocalTime.parse("13:00"), b1);
        occasion3 = new Occasion(LocalDate.parse("2020-01-12"), LocalTime.parse("12:00"), LocalTime.parse("22:00"), b2);
        occasion4 = new Occasion(LocalDate.parse("2020-07-02"), LocalTime.parse("17:00"), LocalTime.parse("23:00"), b2);

        occasions = new ArrayList<>(List.of(occasion,occasion2,occasion3));
        occasions2 = new ArrayList<>(List.of(occasion,occasion2));
    }

    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }

    @Test
    void getOccasions() throws JsonProcessingException {
        String body = mapper.writeValueAsString(new ArrayList<Occasion>(List.of(occasion,occasion2,occasion3)));
        mockServer.when(request().withPath("/occasion/all")).respond(response().withHeaders().withBody(body));
        List<Occasion> occasionList = OccasionCommunication.getOccasions();
        assertEquals(occasionList, occasions);
    }

    @Test
    void getOccasionsByBuilding() throws JsonProcessingException {

        String body = mapper.writeValueAsString(new ArrayList<Occasion>(List.of(occasion,occasion2)));
        mockServer.when(request().withPath("/occasion/building/1")).respond(response().withBody(body));

        List<Occasion> occasionList = OccasionCommunication.getOccasionsByBuilding(1);
        assertEquals(occasionList,occasions2);
    }

    @Test
    void getOccasionById() throws JsonProcessingException {
        String body = mapper.writeValueAsString(occasion);
        mockServer.when(request().withPath("/occasion/id/1")).respond(response().withBody(body));

        Occasion occ = OccasionCommunication.getOccasionById(1);
        assertEquals(occ, occasion);

    }
}
