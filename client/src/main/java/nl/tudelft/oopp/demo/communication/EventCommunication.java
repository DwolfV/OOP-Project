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
import nl.tudelft.oopp.demo.helperclasses.Event;
import nl.tudelft.oopp.demo.helperclasses.User;

public class EventCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Get a list of events for a certain user.
     *
     * @param id - the id of the user
     * @return a list of events
     */
    public static List<Event> getEventsByUser(long id) {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/event/%d", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
        List<Event> events = null;
        // TODO handle exception
        try {
            events = mapper.readValue(response.body(), new TypeReference<List<Event>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return events;
    }

    /**
     * Adds a new event in the database.
     *
     * @param name        - the name of the event
     * @param description - the description of the event
     * @param date        - the date of the event
     * @param startTime   - start time of the event
     * @param endTime     - end time fot he new event
     * @param user        - the user that makes the event
     */
    public static void addEvent(String name, String description, LocalDate date, LocalTime startTime, LocalTime endTime, User user) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Event event = new Event(name, description, date, startTime, endTime, user);
        String jsonEvent = "";
        try {
            jsonEvent = mapper.writeValueAsString(event);
            System.out.println(jsonEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonEvent)).uri(URI.create("http://localhost:8080/event/add")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
     * Updates an existing event from the database.
     *
     * @param id          - the id of the event that needs to be updated
     * @param name        - the name of the event
     * @param description - the description of the event
     * @param date        - the date of the event
     * @param startTime   - start time of the event
     * @param endTime     - end time fot he new event
     * @param user        - the user that makes the event
     */
    public static void updateEvent(long id, String name, String description, LocalDate date, LocalTime startTime, LocalTime endTime, User user) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Event newEvent = new Event(name, description, date, startTime, endTime, user);
        String jsonEvent = "";
        try {
            jsonEvent = mapper.writeValueAsString(newEvent);
            System.out.println(jsonEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(jsonEvent)).uri(URI.create(String.format("http://localhost:8080/event/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
     * Removes an event from the database.
     *
     * @param id - the id of the event
     */
    public static void removeEvent(long id) {
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(String.format("http://localhost:8080/event/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
