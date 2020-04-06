package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.RestaurantDish;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;


class RestaurantDishCommunicationTest {

    private ClientAndServer mockServer;
    private ObjectMapper mapper;

    private RestaurantDish restaurantDish;
    private RestaurantDish restaurantDish2;
    private RestaurantDish restaurantDish3;

    private Restaurant r1;
    private Restaurant r2;
    private Restaurant r3;
    private Restaurant r4;
    private Restaurant r5;

    private Dish d1;
    private Dish d2;
    private Dish d3;
    private Dish d4;

    private List<RestaurantDish> restaurantDishes;

    @BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(8080);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Building b1 = new Building("b1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s1", "sNo1", "z1", "c1");
        r1 = new Restaurant("res1", b1, LocalTime.parse("13:00"), LocalTime.parse("14:00"));    //building 1
        r2 = new Restaurant("res2", b1, LocalTime.parse("14:00"), LocalTime.parse("15:00"));    //building 1
        r3 = new Restaurant("res3", b1, LocalTime.parse("15:30"), LocalTime.parse("17:00"));    //building 1

        Building b2 = new Building("b2", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s2", "sNo2", "z2", "c2");
        r4 = new Restaurant("res4", b2, LocalTime.parse("16:00"), LocalTime.parse("20:00"));    //building 2
        r5 = new Restaurant("res5", b2, LocalTime.parse("17:00"), LocalTime.parse("21:00"));    //building 2

        d1 = new Dish("french fries", "potatoes", "vegan", 3);
        d2 = new Dish("soup", "chicken, vegetables", "non-vegetarian", 4);

        restaurantDish = new RestaurantDish(r1, d1);
        restaurantDish2 = new RestaurantDish(r1, d2);
        restaurantDish3 = new RestaurantDish(r2, d3);

        restaurantDishes = new ArrayList<>(List.of(restaurantDish, restaurantDish2));

    }

    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }


    @Test
    void getAllRestaurantDishesByRestaurant() throws JsonProcessingException {
        String body = mapper.writeValueAsString(new ArrayList<RestaurantDish>(List.of(restaurantDish, restaurantDish2)));
        mockServer
                .when( request()  .withPath("/restaurant_dish/restaurant/1") )
                .respond(
                        response()
                                .withHeaders(

                                )
                                .withBody(body)
                );

        List<RestaurantDish> rd = RestaurantDishCommunication.getAllRestaurantDishesByRestaurant(1);
        assertEquals(rd, restaurantDishes);
    }
}
