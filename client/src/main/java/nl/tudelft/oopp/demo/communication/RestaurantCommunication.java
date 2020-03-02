package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Restaurant;
import nl.tudelft.oopp.demo.helperclasses.Room;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Time;
import java.util.List;

public class RestaurantCommunication {

    // CHANGE ROOM -> BUILDING

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a list of restaurants.
     * @return A list of all restaurants.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static List<Restaurant> getRestaurants() {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/restaurant")).build();
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
            restaurants = mapper.readValue(response.body(), new TypeReference<List<Restaurant>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return restaurants;
    }

    /**
     * Adds a room.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void addRestaurant(String name, long buildingId, Time tClose, Time tOpen) {
        ObjectMapper mapper = new ObjectMapper();
        Restaurant newRestaurant = new Restaurant(name, new Building(buildingId), tClose, tOpen);
        String JSONRoom = "";
        try {
            JSONRoom = mapper.writeValueAsString(newRestaurant);
            System.out.println(JSONRoom);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(JSONRoom)).uri(URI.create("http://localhost:8080/restaurants")).build();
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

    /**
     * Updates a room.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void updateRoom(long id, String roomName, int capacity, long buildingId) {
        ObjectMapper mapper = new ObjectMapper();
        Room newRoom = new Room(roomName, capacity, new Building(buildingId));
        String JSONRoom = "";
        try {
            JSONRoom = mapper.writeValueAsString(newRoom);
            System.out.println(JSONRoom);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(JSONRoom)).uri(URI.create(String.format("http://localhost:8080/rooms/%s", id))).build();
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

    /**
     * Removes a room.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void removeRoom(long id) {
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(String.format("http://localhost:8080/rooms/%s", id))).build();
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
