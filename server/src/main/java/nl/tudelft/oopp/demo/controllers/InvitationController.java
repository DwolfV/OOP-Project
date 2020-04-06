package nl.tudelft.oopp.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import nl.tudelft.oopp.demo.entities.Invitation;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/invitation")
public class InvitationController {

    @Autowired
    InvitationRepository invitationRepository;

    /**
     * GET endpoint to get users invited to a room reservation.
     *
     * @param id - the id of the room reservation
     * @return a list of users
     */
    @GetMapping("/reservation/{reservation_id}")
    public List<User> getUsersByReservation(@PathVariable(value = "reservation_id") long id) {
        List<Invitation> invitations = invitationRepository.findByRoomReservationId(id);
        List<User> guests = new ArrayList<>();

        for (Invitation i : invitations) {
            guests.add(i.getGuest());
        }

        return guests;
    }

    /**
     * GET endpoint to retrieve the room reservations that a user is invited to.
     *
     * @param username - the username of the user
     * @return the list of room reservations
     */
    @GetMapping("/user")
    public List<RoomReservation> getReservationsByUser(@RequestParam(name = "username") String username) {
        List<Invitation> invitations = invitationRepository.findByGuestUsername(username);
        List<RoomReservation> roomReservations = new ArrayList<>();

        for (Invitation i : invitations) {
            roomReservations.add(i.getRoomReservation());
        }

        return roomReservations;
    }

    /**
     * GET endpoint to retrieve an invitation by it's id.
     *
     * @param id - the id of the invitation
     * @return a response entity containing the invitation
     */
    @GetMapping("/{id}")
    public ResponseEntity<Invitation> getInvitationById(@PathVariable long id) {
        return invitationRepository.findById(id).map(invitation -> new ResponseEntity<>(invitation, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * POST endpoint to add a new invitation to the table.
     *
     * @param invitation - the invitation that needs to be added
     * @param f - a uri component builder
     * @return
     */
    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<Invitation> addInvitation(@Valid @RequestBody Invitation invitation, UriComponentsBuilder f) {
        try {
            invitationRepository.save(invitation);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        UriComponents uri = f.path("/{id}").buildAndExpand(invitation.getId());
        return ResponseEntity.created(uri.toUri()).body(invitation);
    }

    /**
     * DELETE endpoint to delete an invitation by room reservation and user.
     *
     * @param roomReservation - a room reservation
     * @param guest - a user invited to the room reservation
     * @return 200 if the invitation was deleted successfully
     */
    @DeleteMapping("/delete")
    public ResponseEntity deleteInvitation(@RequestParam(name = "room_reservation") RoomReservation roomReservation,
                                           @RequestParam(name = "user") User guest) {
        Invitation invitation = invitationRepository.findByRoomReservationAndGuest(roomReservation, guest);
        if (invitation == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        invitationRepository.delete(invitation);
        return new ResponseEntity("The invitation has been deleted successfully", HttpStatus.OK);
    }
}
