package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Order;
import nl.tudelft.oopp.demo.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

/**
 * NB
 * The order should be created automatically when the user clicks the Order button
 * Thus, the important methods here are:
 *  - get the order by a room reservation id
 *  - add a new order
 * The update and delete are not that important
 */

@RestController
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    OrderRepository repository;

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    @GetMapping("/room_reservation/{id}")
    public List<Order> getOrdersByReservation(@PathVariable long id) {
        return repository.findOrdersByRoomReservationId();
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<Order> addOrder(@Valid @RequestBody Order order, UriComponentsBuilder o) {
        repository.save(order);
        UriComponents uri = o.path("/order/{id}").buildAndExpand(order.getId());
        return ResponseEntity.created(uri.toUri()).body(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable long id, @RequestBody Order newOrder) {
        return repository.findById(id).map(order -> {
            order.setDishOrders(newOrder.getDishOrders());
            order.setRoomReservation(newOrder.getRoomReservation());

            return new ResponseEntity<>(repository.save(order), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable long id) {
        return repository.findById(id).map( order -> {
            repository.delete(order);
            return new ResponseEntity("The order has been deleted successfully", HttpStatus.OK);
        }).orElseGet( () -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }
}
