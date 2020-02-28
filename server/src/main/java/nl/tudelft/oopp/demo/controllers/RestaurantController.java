package nl.tudelft.oopp.demo.controllers;

import java.util.List;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    /**
     * List of all restaurants.
     *
     * @return List of restaurants
     */
    @GetMapping("/restaurant")
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    /**
     * Get list of restaurants by building.
     *
     * @param buildingName name of the building
     * @return List of restaurants in that building
     */
    @GetMapping("/{building}/restaurant")
    public List<Restaurant> getRestaurantByBuilding(@PathVariable String buildingName) {
        return restaurantRepository.findAllByBuildingName(buildingName);
    }

    /**
     * Find building by id
     *
     * @param id - The id of the building that is to be found
     * @return the building and 200 status code if the building is found, 404 status code otherwise
     */
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable long id) {
        return restaurantRepository.findById(id).map(restaurant -> ResponseEntity.ok(restaurant)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Find Restaurant by name
     *
     * @param name - The name of the restaurant that is to be found
     * @return the restaurant and 200 status code if the restaurant is found, 404 status code otherwise
     */
    @GetMapping("/restaurant/{name}")
    public ResponseEntity<Restaurant> getRestaurantByName(@PathVariable String name) {
        return restaurantRepository.findByName(name).map(restaurant -> ResponseEntity.ok(restaurant)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * Create a new restaurant
     *
     * @param restaurant The restaurant you want created
     * @return created restaurant
     */
    @PostMapping(value = "/restaurant", consumes = {"application/json"})
    public Restaurant createNewRestaurant(@Valid @RequestBody Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    /**
     * Update a restaurant
     *
     * @param id          -The id of the restaurant that is to be updated
     * @param newRestaurant - The restaurant instance that has the modified parameters
     * @return a response: the updated restaurant and the status 200 if the update was successful, 404 if the restaurant was not found
     */
    @PutMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable long id,
                                                   @RequestBody Restaurant newRestaurant) {
        return restaurantRepository.findById(id).map(restaurant -> {
            restaurant.setName(newRestaurant.getName());
            restaurant.setBuilding(newRestaurant.getBuilding());
            restaurant.settClose(newRestaurant.gettClose());
            restaurant.settOpen(newRestaurant.gettOpen());

            return new ResponseEntity<>(restaurantRepository.save(restaurant), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Delete a restaurant by it's id
     *
     * @param id - The id of the restaurant that is to be deleted
     * @return a response status: 200 if the restaurant has been deleted successfully, 404 if the restaurant was not found
     */
    @DeleteMapping("/restaurant/{id}")
    public ResponseEntity deleteRestaurant(@PathVariable long id) {
        return restaurantRepository.findById(id).map(restaurant -> {
            restaurantRepository.delete(restaurant);
            return new ResponseEntity("The restaurant has been deleted successfully", HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

}
