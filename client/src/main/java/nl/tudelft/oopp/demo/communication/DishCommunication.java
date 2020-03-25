package nl.tudelft.oopp.demo.communication;

<<<<<<< HEAD:client/src/main/java/nl/tudelft/oopp/demo/communication/OccasionCommunication.java
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
=======
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
>>>>>>> development:client/src/main/java/nl/tudelft/oopp/demo/communication/DishCommunication.java
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
<<<<<<< HEAD:client/src/main/java/nl/tudelft/oopp/demo/communication/OccasionCommunication.java
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nl.tudelft.oopp.demo.helperclasses.Occasion;

public class OccasionCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a list of occasions from the server.
=======
import java.util.List;
import nl.tudelft.oopp.demo.helperclasses.Dish;

public class DishCommunication {
    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a list of dishes from the server.
>>>>>>> development:client/src/main/java/nl/tudelft/oopp/demo/communication/DishCommunication.java
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
<<<<<<< HEAD:client/src/main/java/nl/tudelft/oopp/demo/communication/OccasionCommunication.java
    public static List<Occasion> getOccasions() {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/occasion/all")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
=======

    public static List<Dish> getDishes() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/building")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
>>>>>>> development:client/src/main/java/nl/tudelft/oopp/demo/communication/DishCommunication.java
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
<<<<<<< HEAD:client/src/main/java/nl/tudelft/oopp/demo/communication/OccasionCommunication.java
        mapper.registerModule(new JavaTimeModule());
        List<Occasion> occasions = null;
        // TODO handle exception
        try {
            occasions = mapper.readValue(response.body(), new TypeReference<List<Occasion>>() {
=======
        List<Dish> dishes = null;
        try {
            dishes = mapper.readValue(response.body(), new TypeReference<List<Dish>>() {
>>>>>>> development:client/src/main/java/nl/tudelft/oopp/demo/communication/DishCommunication.java
            });
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

<<<<<<< HEAD:client/src/main/java/nl/tudelft/oopp/demo/communication/OccasionCommunication.java
        return occasions;
    }

    /**
     * Retrieves a list of occasions for a specific building from the server.
=======
        return dishes;
    }

    /**
     * Retrieves a dish by id from the server.
>>>>>>> development:client/src/main/java/nl/tudelft/oopp/demo/communication/DishCommunication.java
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
<<<<<<< HEAD:client/src/main/java/nl/tudelft/oopp/demo/communication/OccasionCommunication.java
    public static List<Occasion> getOccasionsByBuilding(long id) {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/occasion/building/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
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
        List<Occasion> occasions = null;
        // TODO handle exception
        try {
            occasions = mapper.readValue(response.body(), new TypeReference<List<Occasion>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return occasions;
    }

    /**
     * Retrieves an occasion by id from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static Occasion getOccasionById(long id) {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/occasion/id/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
=======

    public static Dish getDishById(long id) {
        // TODO what if Authenticator.SESSION_COOKIE is not set?
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.format("http://localhost:8080/building/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
>>>>>>> development:client/src/main/java/nl/tudelft/oopp/demo/communication/DishCommunication.java
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
<<<<<<< HEAD:client/src/main/java/nl/tudelft/oopp/demo/communication/OccasionCommunication.java
        mapper.registerModule(new JavaTimeModule());
        Occasion occasion = null;
        // TODO handle exception
        try {
            occasion = mapper.readValue(response.body(), new TypeReference<Occasion>() {
=======
        Dish dish = null;
        try {
            dish = mapper.readValue(response.body(), new TypeReference<Dish>() {
>>>>>>> development:client/src/main/java/nl/tudelft/oopp/demo/communication/DishCommunication.java
            });
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

<<<<<<< HEAD:client/src/main/java/nl/tudelft/oopp/demo/communication/OccasionCommunication.java
        return occasion;
    }

    /**
     * Adds an occasion.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void addOccasion(LocalDate date, LocalTime openTime, LocalTime closeTime, long buildingId) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Occasion newOccasion = new Occasion(date, openTime, closeTime, BuildingCommunication.getBuildingById(buildingId));
        String jsonOccasion = "";
        try {
            jsonOccasion = mapper.writeValueAsString(newOccasion);
            System.out.println(jsonOccasion);
=======
        return dish;
    }

    /**
     * Adds a dish.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */

    public static void addDish(String name, String description, String type, float price) {
        ObjectMapper mapper = new ObjectMapper();
        Dish dish = new Dish(name, description, type, price);
        String jsonDish = "";
        try {
            jsonDish = mapper.writeValueAsString(dish);
            System.out.println(jsonDish);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
>>>>>>> development:client/src/main/java/nl/tudelft/oopp/demo/communication/DishCommunication.java
        } catch (IOException e) {
            e.printStackTrace();
        }

<<<<<<< HEAD:client/src/main/java/nl/tudelft/oopp/demo/communication/OccasionCommunication.java
        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonOccasion)).uri(URI.create("http://localhost:8080/occasion/add")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
=======
        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonDish)).uri(URI.create("http://localhost:8080/dish")).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
>>>>>>> development:client/src/main/java/nl/tudelft/oopp/demo/communication/DishCommunication.java
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
<<<<<<< HEAD:client/src/main/java/nl/tudelft/oopp/demo/communication/OccasionCommunication.java
     * Update an occasion.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void updateOccasion(long id, LocalDate date, LocalTime openTime, LocalTime closeTime, long buildingId) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Occasion newOccasion = new Occasion(date, openTime, closeTime, BuildingCommunication.getBuildingById(buildingId));
        String jsonOccasion = "";
        try {
            jsonOccasion = mapper.writeValueAsString(newOccasion);
            System.out.println(jsonOccasion);
=======
     * Updates a Dish.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */

    public static void updateDish(long id, String name, String description, String type, float price) {
        ObjectMapper mapper = new ObjectMapper();
        Dish newDish = new Dish(name, description, type, price);
        String jsonDish = "";
        try {
            jsonDish = mapper.writeValueAsString(newDish);
            System.out.println(jsonDish);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
>>>>>>> development:client/src/main/java/nl/tudelft/oopp/demo/communication/DishCommunication.java
        } catch (IOException e) {
            e.printStackTrace();
        }

<<<<<<< HEAD:client/src/main/java/nl/tudelft/oopp/demo/communication/OccasionCommunication.java
        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(jsonOccasion)).uri(URI.create(String.format("http://localhost:8080/occasion/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
=======
        HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(jsonDish)).uri(URI.create(String.format("http://localhost:8080/dish/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
>>>>>>> development:client/src/main/java/nl/tudelft/oopp/demo/communication/DishCommunication.java
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
<<<<<<< HEAD:client/src/main/java/nl/tudelft/oopp/demo/communication/OccasionCommunication.java
     * Removes an occasion.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */
    public static void removeOccasion(long id) {
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(String.format("http://localhost:8080/occasion/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
=======
     * Removes a dish.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */

    public static void removeDish(long id) {
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(String.format("http://localhost:8080/dish/%s", id))).setHeader("Cookie", Authenticator.SESSION_COOKIE).build();
>>>>>>> development:client/src/main/java/nl/tudelft/oopp/demo/communication/DishCommunication.java
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
