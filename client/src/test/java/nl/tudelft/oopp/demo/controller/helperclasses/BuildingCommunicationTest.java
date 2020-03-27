package nl.tudelft.oopp.demo.controller.helperclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpStatusCode;

public class BuildingCommunicationTest {

    private ClientAndServer mockServer;
    private ObjectMapper mapper;

    private Building b1;
    private Building b2;

    private List<Building> buildings;

    @BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(8080);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        b1 = new Building("b1", LocalTime.parse("10:00"), LocalTime.parse("17:00"), "asdf", "asd", "asdf", "Delft");
        b2 = new Building("b2", LocalTime.parse("10:00"), LocalTime.parse("17:00"), "asd", "as", "asd", "Delft");
        buildings = new ArrayList<>(List.of(b1, b2));
    }

    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }

    @Test
    public void shouldLoadListOfBooks() throws Exception {

        String body = mapper.writeValueAsString(new ArrayList<Building>(List.of(b1,b2)));
        mockServer
                .when(
                        request()
                                .withPath("/building")
                )
                .respond(
                        response()
                                .withHeaders(
                                        //new Header(CONTENT_TYPE.toString(), MediaType.APPLICATION_JSON_VALUE)
                                )
                                .withBody(body)
                );

        List<Building> b = BuildingCommunication.getBuildings();
        assertEquals(buildings, b);
    }

    @Test
    public void shouldGetById() throws Exception {

        String body = mapper.writeValueAsString(b1);
        mockServer
                .when(
                        request()
                                .withPath("/building/1")
                )
                .respond(
                        response()
                                .withBody(body)
                );

        Building b = BuildingCommunication.getBuildingById(1);
        assertEquals(b1, b);
    }


}
