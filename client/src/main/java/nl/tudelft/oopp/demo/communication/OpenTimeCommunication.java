package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.OpenTime;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class OpenTimeCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a list of openTimes from the server.
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static List<OpenTime> getOpenTimes() {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/openTimes")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
        List<OpenTime> openTimes = null;
        // TODO handle exception
        try {
            openTimes = mapper.readValue(response.body(), new TypeReference<List<OpenTime>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return openTimes;
    }

    /**
     * Retrieves an openTime by building id from the server.
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static List<OpenTime> getByBuildingId(long id) {
        // TODO what if Authenticator.SESSION_COOKIE is not set?
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/openTimes/building/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
        List<OpenTime> openTime = new ArrayList<>();
        // TODO handle exception
        try {
            openTime = mapper.readValue(response.body(), new TypeReference<List<OpenTime>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return openTime;
    }

    /**
     * Retrieves an openTime by building id and by day from the server.
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static OpenTime getByBuildingIdAndDay(long id, String day) {
        // TODO what if Authenticator.SESSION_COOKIE is not set?
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/openTimes/day/%s/%s", id, day))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
        OpenTime openTime = null;
        // TODO handle exception
        try {
            openTime = mapper.readValue(response.body(), new TypeReference<OpenTime>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return openTime;
    }

    /**
     * Adds an OpenTime.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void addOpenTime(String day, Time openTime, Time closeTime, Long buildingId) {
        ObjectMapper mapper = new ObjectMapper();
        OpenTime newOpenTime = new OpenTime(day, openTime, closeTime, BuildingCommunication.getBuildingById(buildingId));
        String JSONOpenTime = "";
        try {
            JSONOpenTime = mapper.writeValueAsString(newOpenTime);
            System.out.println(JSONOpenTime);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(JSONOpenTime)).uri(URI.create("http://localhost:8080/openTimes")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
     * Updates an OpenTime.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void updateOpenTime(long id, String day, Time openTime, Time closeTime, Long building)  {
        ObjectMapper mapper = new ObjectMapper();
        OpenTime newOpenTime = new OpenTime(day, openTime, closeTime, BuildingCommunication.getBuildingById(building));
        String JSONOpenTime = "";
        try {
            JSONOpenTime = mapper.writeValueAsString(newOpenTime);
            System.out.println(JSONOpenTime);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(JSONOpenTime)).uri(URI.create(String.format("http://localhost:8080/openTimes/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
     * Removes an OpenTime.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void removeOpenTime(long id) {
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(String.format("http://localhost:8080/openTimes/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
