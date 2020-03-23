package nl.tudelft.oopp.demo.communication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.tudelft.oopp.demo.helperclasses.Room;
import nl.tudelft.oopp.demo.helperclasses.RoomReservation;
import nl.tudelft.oopp.demo.helperclasses.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;



/**
 * Note before:
 * The application needs to show the user a certain reservation.
 * The important features should be:
 *      - get a room reservation by user id
 *      - delete a room reservation by user id or by its id
 */

public class  RoomReservationCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a list of room reservations made by a user from the server.
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static List<RoomReservation> getRoomReservationsByUserId(long id) {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/room_reservation/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
        List<RoomReservation> roomReservations = null;
        // TODO handle exception
        try {
            roomReservations = mapper.readValue(response.body(),
                               new TypeReference<List<RoomReservation>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return roomReservations;
    }

    /**
     * Adds a new room reservation.
     * @throws Exception if communication with the server fails
     *         or if the response is not proper json.
     */
    public static void addRoomReservation(Date date,
                                          Time startTime,
                                          Time endTime,
                                          long roomId) {
        ObjectMapper mapper = new ObjectMapper();
        User user = new User();
        user.setId(Authenticator.ID);
        user.setUsername(Authenticator.USERNAME);

        // TODO get rooms by id
        Room room = null;
        for (Room r : RoomCommunication.getRooms()) {
            if (r.getId() == roomId) {
                room = r;
                break;
            }
        }

        RoomReservation newRoomReservation = new RoomReservation(date, startTime, endTime, user, room);
        String jsonRoomReservation = "";
        try {
            jsonRoomReservation = mapper.writeValueAsString(newRoomReservation);
            System.out.println(jsonRoomReservation);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonRoomReservation)).uri(URI.create("http://localhost:8080/room_reservations")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
     * Updates a RoomReservation.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void updateRoomReservation(long id, Date date, Time startTime, Time endTime, long roomId) {
        ObjectMapper mapper = new ObjectMapper();
        User user = new User();
        user.setId(Authenticator.ID);
        user.setUsername(Authenticator.USERNAME);

        // TODO get rooms by id
        Room room = null;
        for (Room r : RoomCommunication.getRooms()) {
            if (r.getId() == roomId) {
                room = r;
                break;
            }
        }
        RoomReservation newRoomReservation = new RoomReservation(date, startTime, endTime, user, room);
        String jsonRoomReservation = "";
        try {
            jsonRoomReservation = mapper.writeValueAsString(newRoomReservation);
            System.out.println(jsonRoomReservation);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(jsonRoomReservation)).uri(URI.create(String.format("http://localhost:8080/room_reservations/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
     * Removes a room reservation.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void removeRoomReservation(long id) {
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(String.format("http://localhost:8080/room_reservation/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
     * Returns a hash map of all start and end times of all reservations for a given room on a given day.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static Map<Time, Time> getAllRoomReservationTimesPerRoomAndDate(long roomId, Date date) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonDate = "";
        try {
            jsonDate = mapper.writeValueAsString(date);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }

        HttpRequest request = HttpRequest.newBuilder().method("GET", HttpRequest.BodyPublishers.ofString(jsonDate))
                .uri(URI.create(String.format("http://localhost:8080/room_reservations_times/%s", roomId)))
                .setHeader("Content-type", "application/json")
                .setHeader("Cookie", Authenticator.SESSION_COOKIE)
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return new HashMap<>();
        }

        Map<Time, Time> unavailableTimes = null;
        try {
            unavailableTimes = mapper.readValue(response.body(),
                    new TypeReference<Map<Time, Time>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return unavailableTimes;
    }
}

