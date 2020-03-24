package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import javax.validation.Valid;
import nl.tudelft.oopp.demo.entities.DishOrder;
import nl.tudelft.oopp.demo.repositories.DishOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/dish_order")
public class DishOrderController {

    @Autowired
    DishOrderRepository repository;

    /**
     * Retrieve all the dish orders.
     * @return a list of all dis orders
     */
    @GetMapping("/all")
    public List<DishOrder> getAllDishOrders() {
        return repository.findAll();
    }

    /**
     * Find DishOrder by Order id.
     * @return a dish order by the order id
     */
    @GetMapping("/order/{id}")
    public List<DishOrder> getAllDishOrdersByOrder(@PathVariable long id) {
        return repository.findAllByOrderId(id);
    }

    /**
     * Find DishOrder by id.
     * @return a dish order by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<DishOrder> getDishOrderById(@PathVariable long id) {
        return repository.findById(id).map(dishOrder -> ResponseEntity.ok(dishOrder))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Add a new dish order.
     * @param dishOrder - the dish order to add to the database
     * @param d - the uri which helps return the created entity in the request body
     * @return a response entity
     */
    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<DishOrder> addDishOrder(@Valid @RequestBody DishOrder dishOrder, UriComponentsBuilder d) {
        repository.save(dishOrder);
        UriComponents uri = d.path("/dish_order/{id}").buildAndExpand(dishOrder.getId());
        return ResponseEntity.created(uri.toUri()).body(dishOrder);
    }

    /**
     * Update a DishOrder
     * @param id - the id of the dishOrder that is going to be changed
     * @param newDishOrder - the dish that has the newly updated attributes
     * @return a response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<DishOrder> updateDishOrder(@PathVariable long id, @RequestBody DishOrder newDishOrder) {
        return repository.findById(id).map(dishOrder -> {
            dishOrder.setOrder(newDishOrder.getOrder());
            dishOrder.setDish(newDishOrder.getDish());
            dishOrder.setAmount(newDishOrder.getAmount());

            return new ResponseEntity<>(repository.save(dishOrder), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Delete a DishOrder
     * @param id - the id of the DishOrder that needs to be deleted
     * @return a status code
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteDishOrder(@PathVariable long id) {
        return repository.findById(id).map(dishOrder -> {
            repository.delete(dishOrder);
            return new ResponseEntity("The dish order has been deleted successfully", HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }
}
