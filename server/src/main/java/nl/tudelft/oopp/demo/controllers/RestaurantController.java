package nl.tudelft.oopp.demo.controllers;

import java.util.List;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

    //transient?
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    /**
     * List of all restaurants.
     *
     * @return List of restaurants
     */
    @GetMapping("/api/restaurants")
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    /**
     * Get list of restaurants by building.
     *
     * @param building building
     * @return List of buildings
     */
    @GetMapping("/api/restaurants/{building}")
    public List<Restaurant> getRestaurantByBuilding(@RequestBody Building building) {
        return restaurantRepository.findAllByBuildingName(building.getName());
    }

}
