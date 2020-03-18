package nl.tudelft.oopp.demo.communication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Time;
import java.util.List;

import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Restaurant;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class RestaurantCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a list of restaurants from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */

    public static List<Restaurant> getRestaurants() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/restaurant")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
        List<Restaurant> restaurants = null;
        // TODO handle exception
        try {
            restaurants = mapper.readValue(response.body(), new TypeReference<List<Restaurant>>() {
            });

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return restaurants;
    }

    /**
     * Retrieves a list of restaurants from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */

    public static Restaurant getRestaurantById(long id) {
        // TODO what if Authenticator.SESSION_COOKIE is not set?
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/restaurant/id/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        ObjectMapper mapper = new ObjectMapper();
        Restaurant restaurant = null;

        // TODO handle exception
        try {
            restaurant = mapper.readValue(response.body(), new TypeReference<Restaurant>() {
            });
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return restaurant;
    }

    /**
     * Adds a restaurant.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */

    public static void addRestaurant(String name, Building building, Time timeClose, Time timeOpen) {
        ObjectMapper mapper = new ObjectMapper();
        Restaurant restaurant = new Restaurant(name, building, timeClose, timeOpen);
        String jsonRestaurant = "";
        try {
            jsonRestaurant = mapper.writeValueAsString(restaurant);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonRestaurant)).uri(URI.create("http://localhost:8080/restaurant")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

    }

    /**
     * Updates a Restaurant.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void updateRestaurant(long id, String name, Building building, Time timeClose, Time timeOpen) {
        ObjectMapper mapper = new ObjectMapper();
        Restaurant restaurant = new Restaurant(name, building, timeClose, timeOpen);
        String jsonRestaurant = "";
        try {
            jsonRestaurant = mapper.writeValueAsString(restaurant);
            System.out.println(jsonRestaurant);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(jsonRestaurant)).uri(URI.create(String.format("http://localhost:8080/restaurant/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
    }


    /**
     * Removes a Restaurant.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */

    public static void removeRestaurant(long id) {
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(String.format("http://localhost:8080/restaurant/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
    }

}
