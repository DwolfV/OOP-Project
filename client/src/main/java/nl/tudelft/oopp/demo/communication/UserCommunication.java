package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.List;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.UserInfo;

public class UserCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    // TODO maybe return a flag that will be different if there is an error or if the credentials are wrong

    /**
     * Authenticates a user and sets a session cookie.
     *
     * @return An integer flag: 0 - if the password is wrong or the user does not exist, 1 - if the server has authenticated the user;
     *          2 - if the user has connectivity issues
     */
    public static int authenticate(String username, String password) {
        String encodedCredentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/login")).setHeader("Authorization", "Basic " + encodedCredentials).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return 2;
            //return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return 0;
        }

        // set received cookie
        Authenticator.SESSION_COOKIE = response.headers().allValues(("Set-cookie")).get(0).split("; ")[0];
        ObjectMapper mapper = new ObjectMapper();
        List<String> responseString = null;
        try {
            responseString = mapper.readValue(response.body(),
                new TypeReference<List<String>>() {
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
        // set user's role
        Authenticator.ROLE = responseString.get(0);

        // set the user's id
        Authenticator.ID = Long.parseLong(responseString.get(1));
        Authenticator.USERNAME = username;

        System.out.println(Authenticator.ROLE + "; IS ADMIN - " + Authenticator.isAdmin());
        return 1;
    }

    /**
     * Get a user by a given username.
     *
     * @param username - the username of the user that we are looking for
     * @return the user
     */
    public static User getByUsername(String username) {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/friend/user/%s", username))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println("mile1");
            e.printStackTrace();
            //return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        User user = null;
        // TODO handle exception
        try {
            user = mapper.readValue(response.body(), new TypeReference<User>() {
            });
        } catch (IOException e) {
            System.out.println("mile2");
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Creates a new user and saves their credentials and information in the database.
     *
     * @param user     The information that the user gives on sign up is stored in this object
     * @param username The username that the user gives
     * @param password The password that the user gives
     * @return A boolean - true if everything went OK, false otherwise
     */
    public static boolean createNewUser(User user, String username, String password) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonUserInfo = "";
        UserInfo userInfo = new UserInfo(user.getEmail(), user.getRole(), user.getFirstName(), user.getLastName(), username, password);
        try {
            jsonUserInfo = mapper.writeValueAsString(userInfo);
            System.out.println(userInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonUserInfo)).uri(URI.create("http://localhost:8080/signup")).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
            //return "Communication with server failed";
        }
        if (response.statusCode() != 201) {
            System.out.println("Status: " + response.statusCode());
            return false;
        }

        // if everything has gone fine, the user can authenticate with their newly added credentials
        return authenticate(username, password) == 1;
    }

    /**
     * This method allows admins to change the roles of other users.
     *
     * @param username The username of the account you want to change the role of
     * @param role     The role that you want the user to have. Choose from:
     *                 admin, employee, user
     *                 with user having the least rights, them employees, then admins.
     *                 Note that the letter case does not matter.
     * @return An integer flag:
     *         0 - unauthorized
     *         1 - successful
     *         2 - the role does not exist
     *         3 - if there is a network connection issue
     *         4 - the user does not exist
     *         5 - in any other case
     */
    public static int changeRole(String username, String role) {
        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(role)).uri(URI.create(String.format("http://localhost:8080/change_user_role/%s", username))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return 3;
            //return "Communication with server failed";
        }
        if (response.statusCode() == 404) {
            System.out.println("No such user");
            return 4;
        } else if (response.statusCode() == 204) {
            System.out.println("Successful role changing");
            return 1;
        } else if (response.statusCode() == 401 || response.statusCode() == 403) {
            System.out.println("Unauthorized");
            return 0;
        } else if (response.statusCode() == 409) {
            System.out.println("The role does not exist");
            return 2;
        }
        return 5;
    }

}
