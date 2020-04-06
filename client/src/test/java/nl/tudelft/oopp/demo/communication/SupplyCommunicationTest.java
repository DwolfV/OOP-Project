package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Supply;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

class SupplyCommunicationTest {

    private ClientAndServer mockServer;
    private ObjectMapper mapper;

    private Building b1;
    private Building b2;
    private Building b3;
    private Building b4;

    private Supply s1;
    private Supply s2;
    private Supply s3;
    private Supply s4;

    private List<Supply> supplies;

    @BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(8080);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        b1 = new Building("name1", LocalTime.parse("08:00"), LocalTime.parse("20:00"), "s1", "sNo1", "z1", "c1");
        b2 = new Building("name2", LocalTime.parse("08:00"), LocalTime.parse("20:00"), "s2", "sNo2", "z2", "c2");
        b3 = new Building("name3", LocalTime.parse("08:00"), LocalTime.parse("20:00"), "s3", "sNo3", "z3", "c3");
        b4 = new Building("name4", LocalTime.parse("08:00"), LocalTime.parse("20:00"), "s4", "sNo4", "z4", "c4");

        s1 = new Supply(b1, "s1", 7);
        s2 = new Supply(b2, "s2", 11);
        s3 = new Supply(b3, "s3", 52);
        s4 = new Supply(b4, "s4", 77);

        supplies = new ArrayList<>(List.of(s1,s2,s3,s4));
    }

    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }

    @Test
    void getSupplies() throws JsonProcessingException {
        String body = mapper.writeValueAsString(supplies);
        mockServer.when(request().withPath("/supply")).respond(response().withHeaders()
                .withBody(body));

        List<Supply> supplyList = SupplyCommunication.getSupplies();
        assertEquals(supplyList, supplies);
    }
}