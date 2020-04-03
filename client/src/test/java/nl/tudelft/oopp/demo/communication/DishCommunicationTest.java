package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.tudelft.oopp.demo.entities.Dish;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;


public class DishCommunicationTest {

    private ClientAndServer mockServer;
    private ObjectMapper mapper;

    private Dish dish;
    private Dish dish2;

    private List<Dish> dishes;



    @BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(8080);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        dish = new Dish("soup", "carrots", "vegan", 4);
        dish2 = new Dish("sandwich", "salami, cheese", "with meat", 7);
        dishes = new ArrayList<>(List.of(dish, dish2));
    }

    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }

        @Test
    void getDishes() throws Exception {
        String body = mapper.writeValueAsString(new ArrayList<Dish>(List.of(dish, dish2)));
        mockServer.when(request().withPath("/dish")).respond(
                response()
                        .withHeaders(
                        )
                        .withBody(body));

        List<Dish> dishList = DishCommunication.getDishes();
        assertEquals(dishes, dishList);
        }

    @Test
    void getDishById() throws Exception {
        String body = mapper.writeValueAsString(dish);
        mockServer.when(request().withPath("/dish/1")).respond(
                response()
                        .withBody(body));

        Dish dish1 = DishCommunication.getDishById(1);
        assertEquals(dish, dish1);
    }
}