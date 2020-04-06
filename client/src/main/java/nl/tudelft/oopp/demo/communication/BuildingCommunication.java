package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;

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
        mapper.registerModule(new JavaTimeModule());

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
     * Retrieves a building from the server.
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
        mapper.registerModule(new JavaTimeModule());
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
     * Retrieves the open and close times for a specific building.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static List<LocalTime> getTimebyBuildingId(long id) {
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
        mapper.registerModule(new JavaTimeModule());
        Building building = null;
        // TODO handle exception
        try {
            building = mapper.readValue(response.body(), new TypeReference<Building>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<LocalTime> times = new ArrayList<>(List.of(building.getOpenTime(), building.getCloseTime()));

        return times;
    }

    /**
     * Retrieves a list of filtered buildings from the server.
     *
     * @param capacity - the minimum capacity that a room inside a buildings should have
     * @param equipment - a list of Strings defining the filter parameters
     * @return the body of a get request to the server.
     */
    public static List<Building> getFilteredBuildings(Integer capacity, List<String> equipment) {
        String uri = "http://localhost:8080/building/filter";
        if (capacity == null) {
            capacity = 0;
        }
        uri = uri + "?capacity=" + capacity;

        //the number of equipment items
        int count = 0;
        String baseString = "&e%n"; //base string used for formatting
        for (String s : equipment) {
            if (count == 6) { //we don't want to filter more than 6 items
                break;
            }
            count++;//increase the amount of added items
            uri = uri + String.format(baseString, count) + s; //add the filter to the uri
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
        mapper.registerModule(new JavaTimeModule());
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
    public static String addBuilding(String name, LocalTime openTime, LocalTime closeTime, String streetName, String streetNumber, String zipCode, String city) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Building newBuilding = new Building(name, openTime, closeTime, streetName, streetNumber, zipCode, city);
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
        if (response.statusCode() != 201) {
            System.out.println("Status: " + response.statusCode());
            return "The name or address of the building already exists.";
        }
        return "Successful";
    }

    /**
     * Updates a Building.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static String updateBuilding(long id, String name, LocalTime openTime, LocalTime closeTime, String streetName, String streetNumber, String zipCode, String city) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Building newBuilding = new Building(name, openTime, closeTime, streetName, streetNumber, zipCode, city);
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
            return "The name or address of the building already exists.";
        }
        return "Successful";
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

