package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.tudelft.oopp.demo.entities.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.util.List;

public class DishOrderCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Get the dish orders by order, used to get dishes for an order
     *
     * @param id - the id of the order
     * @return
     */
    private static List<DishOrder> getDishOrderByOrder(long id) {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/dish_order/order/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
        List<DishOrder> dishes = null;
        // TODO handle exception
        try {
            dishes = mapper.readValue(response.body(), new TypeReference<List<DishOrder>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dishes;
    }

    /**
     * Add a new DishOrder, links a dish to an order
     *
     * @param amount - amount of the product
     * @param restaurantDishId - id of the restaurant
     * @param orderId - the id of the order
     */
    public static void addDishOrder(Integer amount, Long restaurantDishId, Long orderId) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        DishOrder dishOrder = new DishOrder(amount, OrderCommunication.getOrderById(orderId), RestaurantDishCommunication.getRestaurantDishById(restaurantDishId));
        String jsonDishOrder = "";
        try {
            jsonDishOrder = mapper.writeValueAsString(dishOrder);
            System.out.println(jsonDishOrder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonDishOrder)).uri(URI.create("http://localhost:8080/dish_order/add")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
    }
}
