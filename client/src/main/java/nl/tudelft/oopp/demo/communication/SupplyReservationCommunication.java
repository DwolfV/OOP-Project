package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Supply;
import nl.tudelft.oopp.demo.entities.SupplyReservation;
import nl.tudelft.oopp.demo.entities.User;

/**
 * Note before:
 * The application needs to show the user a certain reservation of a supply.
 * The important features should be:
 * - get a supply reservation by user id
 * - delete a supply reservation by user id or by its id
 */

public class SupplyReservationCommunication {
    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a list of supply reservations made by a user from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */

    public static List<SupplyReservation> getSupplyReservationByUserId(long id) {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/supply_reservations/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<SupplyReservation> supplyReservations = null;

        // TODO handle exception
        try {
            supplyReservations = mapper.readValue(response.body(), new TypeReference<List<SupplyReservation>>() {
            });
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return supplyReservations;
    }


    /**
     * Adds a new supply reservation.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */

    public static String addSupplyReservation(LocalDate date,
                                            Integer amount,
                                            long supplyId) {
        if (amount == 0) { // user cannot get 0 items
            return "The amount you want to reserve should be greater than 0.";
        }
        if (amount == null) { //the default amount is 1
            amount = 1;
        }
        Supply supply = SupplyCommunication.getSupplyById(supplyId); // get the supply
        int stock = supply.getStock(); //get the stock
        if (stock < amount) { //check if there are enough items in stock
            return "The requested amount is larger than the stock";
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        //get the user
        User user = new User();
        user.setId(Authenticator.ID);
        user.setUsername(Authenticator.USERNAME);


        SupplyReservation supplyReservation = new SupplyReservation(date, amount, supply, user);
        String jsonSupplyReservation = "";

        try {
            jsonSupplyReservation = mapper.writeValueAsString(supplyReservation);
            System.out.println(jsonSupplyReservation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonSupplyReservation)).uri(URI.create("http://localhost:8080/supply_reservations/add")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        } else {
            //update the stock for the supply
            SupplyCommunication.updateSupply(supply.getId(), supply.getBuilding().getId(), supply.getName(), stock - amount);
        }
        return "The item has been saved successfully";
    }

    /**
     * Updates a supply reservation.
     *
     *  @throws Exception if communication with the server fails or if the response is not proper json.
     */

    public static void updateSupplyReservation(LocalDate date,
                                               int amount,
                                               long supplyId) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        User user = new User();
        user.setId(Authenticator.ID);
        user.setUsername(Authenticator.USERNAME);

        Supply supply = null;
        for (Supply s : SupplyCommunication.getSupplies()) {
            if (s.getId() == supplyId) {
                supply = s;
                break;
            }
        }

        SupplyReservation supplyReservation = new SupplyReservation(date, amount, supply, user);
        String jsonSupplyReservation = "";

        try {
            jsonSupplyReservation = mapper.writeValueAsString(supplyReservation);
            System.out.println(jsonSupplyReservation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(jsonSupplyReservation)).uri(URI.create(String.format("http://localhost:8080/supply_reservations/%s", supplyId))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());

        }
    }

    /**
     * Removes a supply reservation.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */

    public static void removeSupplyReservation(long id) {
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(String.format("http://localhost:8080/supply_reservations/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
    }

}
