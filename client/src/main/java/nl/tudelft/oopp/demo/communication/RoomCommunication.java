package nl.tudelft.oopp.demo.communication;



import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Room;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a list of rooms.
     * @return A list of all rooms.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static List<Room> getRooms() {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/rooms")).build();
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
        List<Room> room = null;
        // TODO handle exception
        try {
            room = mapper.readValue(response.body(), new TypeReference<List<Room>>(){});
        } catch (IOException e) {
            System.out.println(response.body());
        }

        return room;
    }

    /**
     * Retrieves a list of rooms that are located in a certain building.
     * @return A list of all rooms.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static List<Room> getRoomsByBuildingId(long buildingId) {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/rooms/%s", buildingId))).build();
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
        List<Room> room = null;
        // TODO handle exception
        try {
            room = mapper.readValue(response.body(), new TypeReference<List<Room>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return room;
    }

    /**
     * Adds a room.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void addRoom(String roomName, int capacity, long buildingId) {
        ObjectMapper mapper = new ObjectMapper();
        Room newRoom = new Room(roomName, capacity, BuildingCommunication.getBuildingById(buildingId));
        String JSONRoom = "";
        try {
            JSONRoom = mapper.writeValueAsString(newRoom);
            System.out.println(JSONRoom);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(JSONRoom)).uri(URI.create("http://localhost:8080/rooms")).build();
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
        Room newRoom = new Room(roomName, capacity, BuildingCommunication.getBuildingById(buildingId));
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
