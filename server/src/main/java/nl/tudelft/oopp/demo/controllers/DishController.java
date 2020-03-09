package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import javax.validation.Valid;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.repositories.DishRepository;
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
public class DishController {
    @Autowired
    DishRepository dishRepository;

    /**
     *  Find all dishes.
     * @return message
     */
    @GetMapping("/dish")
    @ResponseBody
    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    /**
     * Find dish by id.
     * @param id - the ID of the dish that is to be found
     * @return the dish and 200 status code if the dish is found, 404 status code otherwise
     */

    @GetMapping("/dish/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable long id) {
        return dishRepository.findById(id).map(dish -> ResponseEntity.ok(dish))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Find dish by name.
     * @param name - the name of the dish that is to be found
     * @return the dish and 200 status code if the dish is found, 404 status code otherwise
     */

    @GetMapping("/dish/name/{name}")
    public List<Dish> getAllDishByName(@PathVariable String name){
        return dishRepository.findByName(name);
    }

    /**
     * Create a new dish.
     * @return message
     */

    @PostMapping(value = "/dish", consumes = {"application/json"})
    public ResponseEntity<Dish> newDish(@Valid @RequestBody Dish dish, UriComponentsBuilder uriComponentsBuilder) {
        dishRepository.save(dish);
        UriComponents uri = uriComponentsBuilder.path("/dish/{id}").buildAndExpand(dish.getId());
        return ResponseEntity.created(uri.toUri()).body(dish);
    }

    /**
     * Update the dish.
     * @param id - the id of the dish that is to be modified
     * @param newDish - the dish instance that has the modified parameters
     * @return a response the updated building and the status 200 if the update was successful, 404 if the dish was not found
     */

    @PutMapping("/dish/{id}")
    public ResponseEntity<Dish> updateDish(@PathVariable long id, @RequestBody Dish newDish) {
        return dishRepository.findById(id).map( dish -> {
            dish.setDescription(newDish.getDescription());
            dish.setName(newDish.getName());
            dish.setPrice(newDish.getPrice());
            dish.setType(newDish.getType());

            return new ResponseEntity<Dish>(dishRepository.save(dish), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Delete a building by it's id.
     *
     * @param id - The id of the dish that is to be deleted
     * @return a response status: 200 if the dish has been deleted successfully, 404 if the dish was not found
     */

    @DeleteMapping("/dish/{id}")
    public ResponseEntity deleteDish(@PathVariable long id) {
        return dishRepository.findById(id).map(dish -> {
            dishRepository.delete(dish);
            return new ResponseEntity("The dish has been deleted successfully", HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

}
