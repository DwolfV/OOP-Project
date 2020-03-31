package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.helperclasses.Dish;
import nl.tudelft.oopp.demo.helperclasses.RestaurantDish;

public class RestaurantDishCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Get all the dishes from a restaurant by it's id.
     *
     * @param id - the id of the restaurant
     * @return the list of dishes for the restaurant
     */
    public static List<Dish> getDishesByRestaurant(long id) {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/restaurant_dish/restaurant/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            //return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<RestaurantDish> restaurantDishes = null;
        // TODO handle exception
        try {
            restaurantDishes = mapper.readValue(response.body(), new TypeReference<List<RestaurantDish>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Dish> dishes = new ArrayList<>();
        for (RestaurantDish rd : restaurantDishes) {
            dishes.add(DishCommunication.getDishById(rd.getDish().getId()));
        }
        return dishes;
    }
}
