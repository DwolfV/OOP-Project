package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalTime;
import nl.tudelft.oopp.demo.helperclasses.Building;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

public class BuildingCommunicationTest {
//    public class TestMockServer {
//        private void createExpectationForInvalidAuth() {
//            new MockServerClient("127.0.0.1", 1080)
//                .when(
//                    request()
//                        .withMethod("POST")
//                        .withPath("/validate")
//                        .withHeader("\"Content-type\", \"application/json\"")
//                        .withBody(exact("{username: 'foo', password: 'bar'}")),
//                    exactly(1))
//                .respond(
//                    response()
//                        .withStatusCode(401)
//                        .withHeaders(
//                            new Header("Content-Type", "application/json; charset=utf-8"),
//                            new Header("Cache-Control", "public, max-age=86400"))
//                        .withBody("{ message: 'incorrect username and password combination' }")
//                        .withDelay(TimeUnit.SECONDS,1)
//                );
//        }
//        // ...
//    }

    private ClientAndServer mockServer;


    @BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(8080);
    }

    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }

    @Test
    public void shouldLoadListOfBooks() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Building ret = new Building("asdf", LocalTime.parse("10:00"), LocalTime.parse("17:00"), "asdf", "asd", "asdf", "Delft");
        String body = mapper.writeValueAsString(ret);
        mockServer
            .when(
                request()
                    .withPath("/building/1")
            )
            .respond(
                response()
                    .withHeaders(
                        //new Header(CONTENT_TYPE.toString(), MediaType.APPLICATION_JSON_VALUE)
                    )
                    .withBody(body)
            );

        Building b = BuildingCommunication.getBuildingById(1);
        assertEquals(ret, b);
    }
}