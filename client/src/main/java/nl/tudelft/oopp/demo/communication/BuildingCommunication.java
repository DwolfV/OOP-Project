package nl.tudelft.oopp.demo.communication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import nl.tudelft.oopp.demo.helperclasses.Building;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class BuildingCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a list of buildings from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static List<Building> getBuildings() {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/building")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
            buildings = mapper.readValue(response.body(), new TypeReference<List<Building>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buildings;
    }

    /**
     * Retrieves a list of buildings from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static Building getBuildingById(long id) {
        // TODO what if Authenticator.SESSION_COOKIE is not set?
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/building/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
            building = mapper.readValue(response.body(), new TypeReference<Building>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return building;
    }

    /**
     * Retrieves a list of filtered buildings from the server.
     *
     * @param capacity - the minimum capacity that a room inside a buildings should have
     * @param e1       - the name of a piece of equipment that a room inside a buildings should have
     * @param e2       - the name of a piece of equipment that a room inside a buildings should have
     * @param e3       - the name of a piece of equipment that a room inside a buildings should have
     * @param e4       - the name of a piece of equipment that a room inside a buildings should have
     * @return the body of a get request to the server.
     */
    public static List<Building> getFilteredBuildings(Integer capacity, String e1, String e2, String e3, String e4) {
        String uri = "http://localhost:8080/building/filter";
        if (capacity == null) {
            capacity = 0;
        }
        uri = "?capacity=" + capacity;
        if (e1 != null) {
            uri = "&e1=" + e1;
        }
        if (e2 != null) {
            uri = "&e2=" + e2;
        }
        if (e3 != null) {
            uri = "&e3=" + e3;
        }
        if (e4 != null) {
            uri = "&e4=" + e4;
        }
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
        List<Building> buildings = null;
        // TODO handle exception
        try {
            buildings = mapper.readValue(response.body(), new TypeReference<List<Building>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buildings;
    }

    /**
     * Adds a building.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void addBuilding(String name, String streetName, String streetNumber, String zipCode, String city) {
        ObjectMapper mapper = new ObjectMapper();
        Building newBuilding = new Building(name, streetName, streetNumber, zipCode, city);
        String jsonBuilding = "";
        try {
            jsonBuilding = mapper.writeValueAsString(newBuilding);
            System.out.println(jsonBuilding);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonBuilding)).uri(URI.create("http://localhost:8080/building")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void updateBuilding(long id, String name, String streetName, String streetNumber, String zipCode, String city) {
        ObjectMapper mapper = new ObjectMapper();
        Building newBuilding = new Building(name, streetName, streetNumber, zipCode, city);
        String jsonBuilding = "";
        try {
            jsonBuilding = mapper.writeValueAsString(newBuilding);
            System.out.println(jsonBuilding);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(jsonBuilding)).uri(URI.create(String.format("http://localhost:8080/building/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void removeBuilding(long id) {
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(String.format("http://localhost:8080/building/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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

