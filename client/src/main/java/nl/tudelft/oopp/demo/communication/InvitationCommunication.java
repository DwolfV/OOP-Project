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
import nl.tudelft.oopp.demo.helperclasses.Invitation;
import nl.tudelft.oopp.demo.helperclasses.RoomReservation;
import nl.tudelft.oopp.demo.helperclasses.User;

public class InvitationCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Get all guests from a room reservation.
     *
     * @param id - the id of the room reservation
     * @return a list of users
     */
    public static List<User> getGuests(long id) {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/invitation/reservation/%d", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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

        List<User> guests = null;
        // TODO handle exception
        try {
            guests = mapper.readValue(response.body(), new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return guests;
    }

    /**
     * Get a list of room reservations that a user is invited to.
     *
     * @param id - the id of the user
     * @return the list of room reservations
     */
    public static List<RoomReservation> getInvitations(long id) {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/invitation/user/%d", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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

        List<RoomReservation> invitations = null;
        // TODO handle exception
        try {
            invitations = mapper.readValue(response.body(), new TypeReference<List<RoomReservation>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return invitations;
    }

    /**
     * Add a new invitation.
     *
     * @param roomReservation - a room reservation
     * @param guest - the user that is invited to the room reservation
     */
    public static void addInvitation(RoomReservation roomReservation, User guest) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Invitation invitation = new Invitation(guest, roomReservation);
        String jsonInvitation = "";
        try {
            jsonInvitation = mapper.writeValueAsString(invitation);
            System.out.println(jsonInvitation);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonInvitation)).uri(URI.create("http://localhost:8080/invitation/add")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
     * Deletes an invitation.
     *
     * @param roomReservation - a room reservation
     * @param guest - the guest
     */
    public static void removeFriendship(User guest, RoomReservation roomReservation) {
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create("http://localhost:8080/friend/delete?guest=" + guest.getId() + "&room_reservation=" + roomReservation.getId())).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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

