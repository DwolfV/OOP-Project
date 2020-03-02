package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Room;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class BuildingCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a list of buildings from the server.
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static List<Building> getBuildings() {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/building")).build();
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
        List<Building> buildings = null;
        // TODO handle exception
        try {
            buildings = mapper.readValue(response.body(), new TypeReference<List<Building>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buildings;
    }

    /**
     * Retrieves a list of buildings from the server.
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static Building getBuildingById(long id) {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/building/%s", id))).build();
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
        Building building = null;
        // TODO handle exception
        try {
            building = mapper.readValue(response.body(), new TypeReference<Building>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return building;
    }

    /**
     * Adds a building.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void addBuilding(long id, String name, String streetName, String streetNumber, String zipCode, String city) {
        ObjectMapper mapper = new ObjectMapper();
        Building newBuilding = new Building(id, name, streetName, streetNumber, zipCode, city);
        String JSONBuilding = "";
        try {
            JSONBuilding = mapper.writeValueAsString(newBuilding);
            System.out.println(JSONBuilding);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(JSONBuilding)).uri(URI.create("http://localhost:8080/building")).build();
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
     * Updates a Building.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void updateBuilding(long id, String name, String streetName, String streetNumber, String zipCode, String city) {
        ObjectMapper mapper = new ObjectMapper();
        Building newBuilding = new Building(id, name, streetName, streetNumber, zipCode, city);
        String JSONBuilding = "";
        try {
            JSONBuilding = mapper.writeValueAsString(newBuilding);
            System.out.println(JSONBuilding);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(JSONBuilding)).uri(URI.create(String.format("http://localhost:8080/building/%s", id))).build();
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
     * Removes a building.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void removeBuilding(long id) {
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(String.format("http://localhost:8080/building/%s", id))).build();
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

