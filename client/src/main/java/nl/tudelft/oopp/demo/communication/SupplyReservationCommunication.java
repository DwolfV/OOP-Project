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
import java.util.List;
import nl.tudelft.oopp.demo.helperclasses.SupplyReservation;

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
}
