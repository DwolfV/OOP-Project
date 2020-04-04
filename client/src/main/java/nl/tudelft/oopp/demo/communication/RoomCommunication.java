package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Room;

public class RoomCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a list of rooms.
     *
     * @return A list of all rooms.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static List<Room> getRooms() {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/rooms")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
        List<Room> room = null;
        // TODO handle exception
        try {
            room = mapper.readValue(response.body(), new TypeReference<List<Room>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(response.body());
        }

        return room;
    }

    /**
     * Retrieves a list of rooms that are located in a certain building.
     *
     * @return A list of all rooms.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static List<Room> getRoomsByBuildingId(long buildingId) {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/rooms/%s", buildingId))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
        List<Room> room = null;
        // TODO handle exception
        try {
            room = mapper.readValue(response.body(), new TypeReference<List<Room>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return room;
    }

    /**
     * Retrieves a list of rooms that are located in a certain building.
     *
     * @param buildingId - the id of the building where the rooms is supposed to be
     * @param capacity   - the minimum capacity that a room inside a buildings should have
     * @param equipment  - a list of Strings defining the filter parameters
     * @return A list of all rooms.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static List<Room> getFilteredRoomsByBuilding(Long buildingId, Integer capacity, List<String> equipment) {

        String uri = "http://localhost:8080/rooms/filter?building_id=" + buildingId;
        if (capacity == null) {
            capacity = 0;
        }
        uri = uri + "&capacity=" + capacity;

        //the number of equipment items
        int count = 0;
        String baseString = "&e%d"; //base string used for formatting
        for (String s : equipment) {
            if (s == null) {
                break;
            }
            if (count == 6) { //we don't want to filter more than 6 items
                break;
            }
            count++;//increase the amount of added items
            uri = uri + String.format(baseString, count) + "=" + s; //add the filter to the uri
        }
        System.out.println(uri);
        uri = uri.replace(" ", "%20");

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(uri)).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();

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
        List<Room> room = null;
        // TODO handle exception
        try {
            room = mapper.readValue(response.body(), new TypeReference<List<Room>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return room;
    }

    /**
     * Adds a room.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void addRoom(String roomName, int capacity, long buildingId) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Room newRoom = new Room(roomName, capacity, BuildingCommunication.getBuildingById(buildingId));
        String jsonRoom = "";
        try {
            jsonRoom = mapper.writeValueAsString(newRoom);
            System.out.println(jsonRoom);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonRoom)).uri(URI.create("http://localhost:8080/rooms")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void updateRoom(long id, String roomName, int capacity, long buildingId) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Room newRoom = new Room(roomName, capacity, BuildingCommunication.getBuildingById(buildingId));
        String jsonRoom = "";
        try {
            jsonRoom = mapper.writeValueAsString(newRoom);
            System.out.println(jsonRoom);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(jsonRoom)).uri(URI.create(String.format("http://localhost:8080/rooms/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void removeRoom(long id) {
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(String.format("http://localhost:8080/rooms/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
