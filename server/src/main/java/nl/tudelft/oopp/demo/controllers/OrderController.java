package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import javax.validation.Valid;
import nl.tudelft.oopp.demo.entities.Order;
import nl.tudelft.oopp.demo.repositories.OrderRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * NB.
 * This entity does not have any uniqueness constraints
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

    @Autowired
    UserRepository users;

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    @GetMapping("/room_reservation/{id}")
    public List<Order> getOrdersByReservationId(@PathVariable long id) {
        return repository.findOrdersByRoomReservationId(id);
    }

    /**
     * Gets all the events linked to a user.
     * @param id - the id of the user by which the orders are retrieved
     * @param authentication - parameter used to check if the current user has the same id as the id of the user that owns the orders
     * @return a List of Orders which are linked to the logged in user
     */
    @GetMapping("/user/{id}")
    public @ResponseBody ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable long id, Authentication authentication) {
        System.out.println(authentication.getName());
        return users.findByUsername(authentication.getName()).map(user -> {
            if (user.getId() == id) {
                return new ResponseEntity<>(repository.findAllByRoomReservation_User_Id(id), HttpStatus.OK);
            }
            return new ResponseEntity<List<Order>>(HttpStatus.UNAUTHORIZED);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * POST Endpoint to add a new order.
     *
     * @param newOrder the new order to be added
     * @return the added order
     */

    @PostMapping(value = "/add", consumes = {"application/json"})
    public ResponseEntity<Order> addOrder(@Valid @RequestBody Order newOrder, UriComponentsBuilder b, Authentication authentication) {

        if (authentication.getName().equals(newOrder.getRoomReservation().getUser().getUsername())
                && !users.findByUsername(authentication.getName()).isEmpty()
                && newOrder.getRoomReservation().getUser().getId() == users.findByUsername(authentication.getName()).get().getId()) {
            Order savedOrder = null;
            try {
                savedOrder = repository.save(newOrder);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            UriComponents uri = b.path("/order/{id}").buildAndExpand(savedOrder.getId());
            return ResponseEntity
                    .created(uri.toUri())
                    .body(savedOrder);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Updates an Order.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable long id, @RequestBody Order newOrder, UriComponentsBuilder b, Authentication authentication) {

        UriComponents uri = b.path("order/{id}").buildAndExpand(id);

        return repository.findById(id).map(order -> {
            if (users.findByUsername(authentication.getName()).isEmpty() || order.getRoomReservation().getUser().getId() != users.findByUsername(authentication.getName()).get().getId()
                    || order.getRoomReservation().getUser().getId() != users.findByUsername(authentication.getName()).get().getId()) {
                return new ResponseEntity<Order>(HttpStatus.UNAUTHORIZED);

            }

            order.setDishOrders(newOrder.getDishOrders());
            order.setRoomReservation(newOrder.getRoomReservation());

            Order orderToReturn;
            try {
                orderToReturn = repository.save(order);
            } catch (Exception e) {
                return new ResponseEntity<Order>(HttpStatus.CONFLICT);
            }

            return ResponseEntity.created(uri.toUri()).body(orderToReturn);
        }).orElseGet(() -> {
            newOrder.setId(id);
            return ResponseEntity.created(uri.toUri()).body(repository.save(newOrder));
        });
    }

    /**
     * DELETE Endpoint to delete the entry of a given order.
     *
     * @param id unique identifier of the user that is to be deleted.
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable long id, Authentication authentication) {

        Order orderToDelete = repository.findById(id).orElseGet(() -> null);

        if (orderToDelete == null) {
            return ResponseEntity.noContent().build();
        }

        if (!orderToDelete.getRoomReservation().getUser().getUsername().equals(authentication.getName())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();

    }
}
