package nl.tudelft.oopp.demo.communication;

import com.fasterxml.jackson.core.JsonParseException;
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
import nl.tudelft.oopp.demo.helperclasses.Supply;

public class SupplyCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a list of supplies from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */

    public static List<Supply> getSupplies() {
                HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/supply/all")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
                List<Supply> supplies = null;
                // TODO handle exception
                try {
                    supplies = mapper.readValue(response.body(), new TypeReference<List<Supply>>(){});
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return supplies;
    }


    
}
