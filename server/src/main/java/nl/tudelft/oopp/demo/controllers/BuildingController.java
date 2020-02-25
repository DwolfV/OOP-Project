package nl.tudelft.oopp.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BuildingController {

    @Autowired
    BuildingRepository rep;

    /**
     * GET Endpoint to retrieve a random quote.
     *
     * @return randomly selected {@link Building}.
     */
    @GetMapping("building")
    public @ResponseBody List<Building> getBuildingById() {
        Building dw = new Building(
                "35",
                "Drebbelweg",
                "Cornelis Drebbelweg",
                "5",
                "2628 CM",
                "Delft"
        );

        Building ewi = new Building(
                "36",
                "EWI",
                "Mekelweg",
                "4",
                "2628 CD",
                "Delft"
        );

        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(dw);
        buildings.add(ewi);

        return buildings;
    }
}
