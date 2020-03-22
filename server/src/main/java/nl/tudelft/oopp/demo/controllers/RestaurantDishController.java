package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import javax.validation.Valid;
import nl.tudelft.oopp.demo.entities.RestaurantDish;
import nl.tudelft.oopp.demo.repositories.RestaurantDishRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class RestaurantDishController {

    @Autowired
    RestaurantDishRepository restaurantDishRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    /**
     * GET Endpoint to retrieve a list of all dishes of a restaurant.
     *
     * @return a list of the restaurant's dishes{@link RestaurantDish}
     */
    @GetMapping("restaurant_dish_all")
    public @ResponseBody
    List<RestaurantDish> getAllRestaurantDishes() {
        return restaurantDishRepository.findAll();
    }

    /**
     * GET Endpoint to retrieve all RestaurantDishes for a restaurant.
     *
     * @return a list of the restaurant's dishes for the given restaurant {@link RestaurantDish}
     */

    @GetMapping("restaurant_dish/restaurant/{restaurant_id}")
    public @ResponseBody
    ResponseEntity<List<RestaurantDish>> getRestaurantDishByRestaurantId(@PathVariable(value = "restaurant_id") long
                                                                             id) {

        return restaurantDishRepository.findByRestaurantId(id).isEmpty() ? new
            ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(restaurantDishRepository.findByRestaurantId(id), HttpStatus.OK);
    }

    /**
     * GET Endpoint to retrieve the restaurantDish by ID.
     *
     * @param restaurantDishId - unique identifier of the restaurantDish.
     * @return the requested {@link RestaurantDish}.
     */

    @GetMapping("restaurant_dish/{restaurant_dish_id}")
    public @ResponseBody
    ResponseEntity<RestaurantDish> getRestaurantDishById(@PathVariable(value = "restaurant_dish_id") long restaurantDishId) {
       return restaurantDishRepository.findById(restaurantDishId).map(restaurantDish -> ResponseEntity.ok(restaurantDish))
               .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * POST Endpoint to add a new restaurant dish.
     *
     * @param newRestaurantDish - the new restaurantDish to be added.
     * @return The added restaurant dish {@link RestaurantDish}.
     */


    @PostMapping(value = "restaurant_dish", consumes = {"application/json"})
    public ResponseEntity<RestaurantDish> newRestaurantDish(@Valid @RequestBody RestaurantDish newRestaurantDish, UriComponentsBuilder b) {
        restaurantDishRepository.save(newRestaurantDish);
        UriComponents uri = b.path("restaurant_dish/{restaurant_dish_id}").buildAndExpand(newRestaurantDish.getId());
        return ResponseEntity.created(uri.toUri()).body(newRestaurantDish);
    }

    /**
     * PUT Endpoint to update the entry of the given restaurant dish.
     *
     * @param restaurantDishId - Unique identifier of the restaurantDish that is to be updated
     * @param newRestaurantDish  - The updated version of the restaurantDish
     * @return the new restaurantDish that is updated {@link RestaurantDish}
     */


    @PutMapping("restaurant_dish/{restaurant_dish_id}")
    public ResponseEntity<RestaurantDish> updateRestaurantDish(@RequestBody RestaurantDish newRestaurantDish,
                                                               @PathVariable(value = "restaurant_dish_id") long restaurantDishId,
                                                               UriComponentsBuilder b) {
        UriComponents uri = b.path("restaurant_dish/{restaurant_dish_id}").buildAndExpand(restaurantDishId);

        RestaurantDish restaurantDish = restaurantDishRepository.findById(restaurantDishId).map(restaurantDish1 -> {
            restaurantDish1.setDishOrders(newRestaurantDish.getDishOrders());
            restaurantDish1.setDish(newRestaurantDish.getDish());
            restaurantDish1.setRestaurant(newRestaurantDish.getRestaurant());
            return restaurantDishRepository.save(restaurantDish1);
        }).orElseGet(() -> {
            newRestaurantDish.setId(restaurantDishId);
            return restaurantDishRepository.save(newRestaurantDish);
        });

        return ResponseEntity.created(uri.toUri()).body(restaurantDish);
    }


    /**
     * DELETE Endpoint to delete the entry of a given restaurant's dish.
     *
     * @param restaurantDishId - the unique identifier of the restaurant's dish that is to be deleted. {@link RestaurantDish}
     */

    @DeleteMapping("restaurant_dish/{restaurant_dish_id}")
    public ResponseEntity<?> deleteRestaurantDish(@PathVariable(value = "restaurant_dish_id") long restaurantDishId) {
        restaurantDishRepository.deleteById(restaurantDishId);
        return ResponseEntity.noContent().build();
    }

}
