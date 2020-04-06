package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Dish;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

public class DishCommunicationTest {

    private ClientAndServer mockServer;
    private ObjectMapper mapper;

    private Dish dish;
    private Dish dish2;

    private List<Dish> dishes;

    /**
     * Start mock server before each test.
     */
    @BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(8080);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        dish = new Dish("soup", "carrots", "vegan", 4);
        dish2 = new Dish("sandwich", "salami, cheese", "with meat", 7);
        dishes = new ArrayList<>(List.of(dish, dish2));
    }

    /**
     * Stop mock server after each test.
     */
    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }

    /**
     * Test get all dishese.
     * @throws Exception e
     */
    @Test
    void getDishes() throws Exception {
        String body = mapper.writeValueAsString(dishes);
        mockServer.when(request().withPath("/dish")).respond(
                response()
                        .withHeaders(
                        )
                        .withBody(body));

        List<Dish> dishList = DishCommunication.getDishes();
        for (Dish dish : dishes) {
            System.out.println(dish.toString());
        }
        for (Dish dish : dishList) {
            System.out.println(dish.toString());
        }
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



