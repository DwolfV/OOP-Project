package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nl.tudelft.oopp.demo.helperclasses.Occasion;


public class OccasionCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a list of occasions from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static List<Occasion> getOccasions() {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/occasion/all")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
        List<Occasion> occasions = null;
        // TODO handle exception
        try {
            occasions = mapper.readValue(response.body(), new TypeReference<List<Occasion>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return occasions;
    }

    /**
     * Retrieves a list of occasions for a specific building from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static List<Occasion> getOccasionsByBuilding(long id) {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/occasion/building/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
        List<Occasion> occasions = null;
        // TODO handle exception
        try {
            occasions = mapper.readValue(response.body(), new TypeReference<List<Occasion>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return occasions;
    }

    /**
     * Retrieves an occasion by id from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static Occasion getOccasionById(long id) {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/occasion/id/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
        Occasion occasion = null;
        // TODO handle exception
        try {
            occasion = mapper.readValue(response.body(), new TypeReference<Occasion>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return occasion;
    }

    /**
     * Adds an occasion.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void addOccasion(LocalDate date, LocalTime openTime, LocalTime closeTime, long buildingId) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Occasion newOccasion = new Occasion(date, openTime, closeTime, BuildingCommunication.getBuildingById(buildingId));
        String jsonOccasion = "";
        try {
            jsonOccasion = mapper.writeValueAsString(newOccasion);
            System.out.println(jsonOccasion);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonOccasion)).uri(URI.create("http://localhost:8080/occasion/add")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
     * Update an occasion.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void updateOccasion(long id, LocalDate date, LocalTime openTime, LocalTime closeTime, long buildingId) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Occasion newOccasion = new Occasion(date, openTime, closeTime, BuildingCommunication.getBuildingById(buildingId));
        String jsonOccasion = "";
        try {
            jsonOccasion = mapper.writeValueAsString(newOccasion);
            System.out.println(jsonOccasion);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(jsonOccasion)).uri(URI.create(String.format("http://localhost:8080/occasion/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
     * Removes an occasion.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void removeOccasion(long id) {
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(String.format("http://localhost:8080/occasion/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
