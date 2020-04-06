package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class ItemCommunicationTest {

    private ClientAndServer mockServer;
    private ObjectMapper mapper;

    private Item item1;
    private Item item2;
    private Item item3;

    private List<Item> items;

    @BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(8080);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        item1 = new Item("projector");
        item2 = new Item("computer");
        item3 = new Item("blackboard");
        items = new ArrayList<>(List.of(item1,item2,item3));
    }

    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }

    @Test
    void getItems() throws JsonProcessingException {
        String body = mapper.writeValueAsString(new ArrayList<Item>(List.of(item1,item2,item3)));
        mockServer.when(request().withPath("/item/all")).respond(response().withHeaders().withBody(body));
        List<Item> i = ItemCommunication.getItems();
        assertEquals(items, i);
    }
}
