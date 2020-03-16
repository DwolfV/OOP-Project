package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Item;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ItemCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a list of items from the server.
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static List<Item> getItems() {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/item/all")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
        List<Item> items = null;
        // TODO handle exception
        try {
            items = mapper.readValue(response.body(), new TypeReference<List<Item>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    /**
     * Adds an item.
     * @param name - the name of the item
     */
    public static void addItem(String name) {
        ObjectMapper mapper = new ObjectMapper();
        Item newItem = new Item(name);
        String JSONItem = "";
        try {
            JSONItem = mapper.writeValueAsString(newItem);
            System.out.println(JSONItem);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(JSONItem)).uri(URI.create("http://localhost:8080/item/add")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
     * Updates a Item.
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void updateItem(long id, String name) {
        ObjectMapper mapper = new ObjectMapper();
        Item newItem = new Item(name);
        String JSONItem = "";
        try {
            JSONItem = mapper.writeValueAsString(newItem);
            System.out.println(JSONItem);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(JSONItem)).uri(URI.create(String.format("http://localhost:8080/item/update/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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