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
import nl.tudelft.oopp.demo.entities.Restaurant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;


public class RestaurantCommunicationTest {

    private ClientAndServer mockServer;
    private ObjectMapper mapper;

    private Restaurant restaurant;

    private Building building;

    private List<Restaurant> restaurants;

    /**
     * Start mock server before each test.
     */
    @BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(8080);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        building = new Building("b1", LocalTime.parse("10:00"), LocalTime.parse("17:00"), "asdf", "asd", "asdf", "Delft");

        restaurant = new Restaurant("r1", building, LocalTime.parse("10:00"), LocalTime.parse("17:00"));
        restaurants = new ArrayList<>(List.of(restaurant));
    }

    /**
<<<<<<< HEAD
     * Stop mock server before each test.
=======
     * Stop mock server after each test.
>>>>>>> development
     */
    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }

    /**
     * Get all restaurants.
     * @throws JsonProcessingException e
     */
    @Test
    void getRestaurants() throws JsonProcessingException {
        String body = mapper.writeValueAsString(restaurants);
        mockServer
            .when(
                request()
                .withPath("/restaurant")
            )
            .respond(
                response().withHeaders()
                    .withBody(body)
            );
        List<Restaurant> restaurantList = RestaurantCommunication.getRestaurants();
        assertEquals(restaurantList, restaurants);
    }

    /**
     * Get restaurants by ID.
     * @throws JsonProcessingException e
     */
    @Test
    void getRestaurantById() throws JsonProcessingException {
        String body = mapper.writeValueAsString(restaurant);
        mockServer
            .when(
                request()
                    .withPath("/restaurant/id/1")
            )
            .respond(
                response()
                    .withBody(body)
            );
        Restaurant restaurant2 = RestaurantCommunication.getRestaurantById(1);
        assertEquals(restaurant, restaurant2);
    }
}
