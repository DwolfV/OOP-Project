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
import nl.tudelft.oopp.demo.entities.Equipment;
import nl.tudelft.oopp.demo.entities.Item;
import nl.tudelft.oopp.demo.entities.Room;

public class EquipmentCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Get a list of equipment for a specific room.
     *
     * @param roomId - the id of the room
     * @return a list of equipment
     */
    public static List<Equipment> getEquipmentByRoom(long roomId) {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/equipment/room/%d", roomId))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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

        List<Equipment> equipment = null;
        // TODO handle exception
        try {
            equipment = mapper.readValue(response.body(), new TypeReference<List<Equipment>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return equipment;
    }

    /**
     * Get a list of equipment by item name.
     *
     * @param itemId - the id of the item
     * @return a list of equipment
     */
    public static List<Equipment> getEquipmentByItem(long itemId) {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/equipment/item_id/%d", itemId))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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

        List<Equipment> equipment = null;
        // TODO handle exception
        try {
            equipment = mapper.readValue(response.body(), new TypeReference<List<Equipment>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return equipment;
    }

    /**
     * Method to add a new Item to a room by creating an Equipment.
     *
     * @param room - the room in which the equipment will be
     * @param item - the item inside the room
     * @param amount - how much of that item
     * @return a string which explains the output of the method
     */
    public static String addEquipmentToRoom(Room room, Item item, int amount) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Equipment equipment = new Equipment(room, item, amount);
        String jsonEquipment = "";
        try {
            jsonEquipment = mapper.writeValueAsString(equipment);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonEquipment)).uri(URI.create("http://localhost:8080/equipment")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            //return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return "This Equipment is already linked to a room";
        }
        return "Successful";
    }

    /**
     * Update the amount for an item inside a room if the quantity will change.
     *
     * @param id - id of the equipment
     * @param room - room where the item is
     * @param item - the item
     * @param amount - the new amount
     * @return a string which explains the output of the method
     */
    public static String updateEquipmentStock(long id, Room room, Item item, int amount) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Equipment newEquipment = new Equipment(room, item, amount);
        String jsonEquipment = "";
        try {
            jsonEquipment = mapper.writeValueAsString(newEquipment);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(jsonEquipment)).uri(URI.create(String.format("http://localhost:8080/equipment/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            //return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return "The room already has this item.";
        }
        return "Successful";
    }

    /**
     * Deletes an equipment.
     *
     * @param id - the id of the equipment
     */
    public static void removeEquipment(long id) {
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(String.format("http://localhost:8080/equipment/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
