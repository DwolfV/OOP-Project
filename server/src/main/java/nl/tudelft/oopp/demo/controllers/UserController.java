package nl.tudelft.oopp.demo.controllers;

import javax.validation.Valid;
import java.util.List;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@Controller
public class UserController {
    @Autowired
    UserRepository rep;

    /**
     * GET Endpoint to retrieve a list of all users.
     * @return List of all users
     */

    @GetMapping("users")
    @ResponseBody
    public List<User> getAllUsers() {
        return rep.findAll();
    }

    /**
     * Find user by id.
     *
     * @param user_id The ID of the user that is to be found
     * @return the user and 200 status code if the user is found, 404 status code otherwise
     */

    @GetMapping("users/{user_id}")
    public @ResponseBody
    ResponseEntity<User> getUserById(@PathVariable long user_id) {
        return rep.findById(user_id).map(user -> ResponseEntity.ok(user))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


/**
 * PUT Endpoint to update the entry of a given user.
 * //     *
 * //     * @param user_id Unique identifier of the user that is to be updated.
 * //     * @param newUser The updated version of the user.
 * //     * @return the new user that is updated.
 */


@PutMapping("users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User newUser, @PathVariable long id, UriComponentsBuilder b) {

    UriComponents uri = b.path("users/{id}").buildAndExpand(id);

    User updatedUser = rep.findById(id).map(user -> {
        user.setBirth_date(newUser.getBirth_date());
        user.setEmail(newUser.getEmail());
        user.setRole(newUser.getRole());
        user.setFirst_name(newUser.getFirst_name());
        user.setLast_name(newUser.getLast_name());

        return rep.save(user);
    }).orElseGet(() -> {
        newUser.setId(id);
        return rep.save(newUser);
            });

    return ResponseEntity.created(uri.toUri()).body(updatedUser);
}


    /**
     * DELETE Endpoint to delete the entry of a given user.
     *
     * @param user_id unique identifier of the user that is to be deleted.
     */

    @DeleteMapping("users/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable long user_id) {
        rep.deleteById(user_id);

        return ResponseEntity.noContent().build();
    }

    /**
     * POST Endpoint to add a new user.
     *
     * @param newUser The new user to be added.
     * @return The added user
     */
    @PostMapping(value = "users", consumes = {"application/json"})
    public ResponseEntity<User> newUser(@Valid @RequestBody User newUser, UriComponentsBuilder b) {
        rep.save(newUser);
        UriComponents uri = b.path("users/{id}").buildAndExpand(newUser.getId());
        return ResponseEntity.created(uri.toUri()).body(newUser);
    }
}
