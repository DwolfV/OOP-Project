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
import nl.tudelft.oopp.demo.helperclasses.Friend;
import nl.tudelft.oopp.demo.helperclasses.User;


public class FriendCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a list of friends for a specific user.
     *
     * @param username - the user for who we look for friends
     * @return
     */
    public static List<User> getFriends(String username) {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/friend/get_friends/%s", username))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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

        List<User> friends = null;
        // TODO handle exception
        try {
            friends = mapper.readValue(response.body(), new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return friends;
    }

    /**
     * Add a new friendship relationship.
     *
     * @param user1 - a user that is part of the friendship system
     * @param user2 - the other user that is part of the friendship system
     */
    public static String addFriendship(User user1, User user2) {
        if ( user1 == null || user2 == null || user1.getUsername() == null || user2.getUsername() == null
            || user1.getUsername() == user2.getUsername()) {
            System.out.println(user1);
            System.out.println(user2);
           return "Invalid friend request.";
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Friend friend = new Friend(user1, user2);
        String jsonFriend = "";
        try {
            jsonFriend = mapper.writeValueAsString(friend);
            System.out.println(jsonFriend);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonFriend)).uri(URI.create("http://localhost:8080/friend/add")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            //return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());

            if (response.statusCode() == 409) {
                return "The user you are trying to add is already your friend";
            }
        }

        return "The user is now your friend";
    }

    /**
     * Deletes a friendship.
     *
     * @param user1 - a user in the friendship
     * @param user2 - a user in the friendship
     */
    public static void removeFriendship(User user1, User user2) {
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create("http://localhost:8080/friend/delete?user1=" + user1.getId() + "&user2=" + user2.getId())).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
