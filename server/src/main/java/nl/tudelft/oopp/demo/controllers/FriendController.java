package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Friend;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.FriendRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/friend")
public class FriendController {

    @Autowired
    FriendRepository friendRepository;

    /**
     * GET endpoint to retrieve a friendship by id.
     *
     * @param id - the id of the friendship that we want to get
     * @return response entity containing the friendship
     */
    @GetMapping("/{id}")
    public ResponseEntity<Friend> getFriendById(@PathVariable long id) {
        return friendRepository.findById(id).map(friend -> new ResponseEntity<>(friend, HttpStatus.OK))
                .orElseGet( () -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET endpoint to retrieve a list of friends for a certain user.
     *
     * @param username - the username of the person for which we want to return the list of friendships
     * @return a list of Friend entities
     */
    @GetMapping("/get_by_username")
    public List<Friend> getFriendByUsername(@RequestParam(name = "username") String username) {
        return friendRepository.findByUsername(username);
    }

    /**
     * GET endpoint to retrieve a friendship by two users.
     *
     * @param user1 - user that is part of the friendship
     * @param user2 - user that is part of the friendship
     * @return a list of Friend entities
     */
    @GetMapping("/get_by_users")
    public ResponseEntity<Friend> getFriendByUser1AndUser2(@RequestParam(name = "user1") User user1,
                                                           @RequestParam(name = "user2") User user2) {
        Friend friend = friendRepository.findByUser1AndUser2(user1, user2);
        if(friend == null) {
            friend = friendRepository.findByUser1AndUser2(user2, user1);
        }
        if(friend == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(friend, HttpStatus.OK);
    }

    @Autowired
    UserRepository userRepository;

    /**
     * Retrieve a list of users that are friends with the current user
     *
     * @param username - the username of a user
     * @return a list of users that represent friends of the current user
     */
    @GetMapping("/get_friends/{user}")
    public List<User> getFriends(@PathVariable(value = "user") String username) {
        List<Friend> friendships = friendRepository.findByUsername(username);
        List<User> friends = new ArrayList<>();

        // find the user for which we want the friends
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.get();

        // construct the list with friends of user from the friendship list
        for(Friend friend : friendships) {
            if(friend.getUser1().equals(user)) {
                friends.add(friend.getUser1());
            } else {
                friends.add(friend.getUser2());
            }
        }

        return friends;
    }

    /**
     * Add a new friendship to the database.
     *
     * @param friend - the friendship that is to be added
     * @param f - uri component builder
     * @return the newly created friendship
     */
    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<Friend> addFriend(@Valid @RequestBody Friend friend, UriComponentsBuilder f) {
        friendRepository.save(friend);
        UriComponents uri = f.path("/{id}").buildAndExpand(friend.getId());
        return ResponseEntity.created(uri.toUri()).body(friend);
    }

    /**
     * Delete a friendship between two users
     *
     * @param user1 - user that is part of the friendship
     * @param user2 - the other user that is part of the friendship
     * @return response entity with status 200 if the friendship has been deleted, 404 otherwise
     */
    @DeleteMapping("/delete")
    public ResponseEntity deleteFriend(@RequestParam(name = "user1") User user1,
                                       @RequestParam(name = "user2") User user2) {
        Friend friend = friendRepository.findByUser1AndUser2(user1, user2);
        if(friend == null) {
            friend = friendRepository.findByUser1AndUser2(user2, user1);
        }
        if(friend == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        friendRepository.delete(friend);
        return new ResponseEntity("The friendship has been deleted successfully", HttpStatus.OK);
    }
}
