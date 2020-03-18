package nl.tudelft.oopp.demo.controller.communication;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.helperclasses.Building;
import org.junit.jupiter.api.Test;

public class BuildingCommunicationTest {

    private static HttpClient client = HttpClient.newBuilder().build();

    @Test
    public void testGetByBuilding() {
        List<Building> buildingList = new ArrayList<>();
    }

}
