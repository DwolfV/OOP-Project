package nl.tudelft.oopp.demo.controllers;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.validation.Valid;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.repositories.RoomReservationRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
public class RoomReservationController {

    @Autowired
    RoomReservationRepository reservations;

    @Autowired
    UserRepository users;

    // TODO get mappings for get room reservation times only (per room of course, but no info for users)!
    /**
     * GET Endpoint to retrieve a list of all room reservations.
     *
     * @return a list of the rooms {@link RoomReservation}.
     */
    @GetMapping("room_reservations_all")
    public @ResponseBody
    List<RoomReservation> getRoomReservationsAll() {
        return reservations.findAll();
    }

    @GetMapping("room_reservations")
    public @ResponseBody
    ResponseEntity getRoomReservations() {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    /**
     * GET Endpoint to retrieve a list of all room reservations for a user.
     *
     * @return a list of the room reservations for the given user {@link RoomReservation}.
     */
    @GetMapping("room_reservations/user/{user_id}")
    public @ResponseBody ResponseEntity<List<RoomReservation>>
        getRoomReservationsByUser(@PathVariable(value = "user_id") long userId,
                                  Authentication authentication) {
        System.out.println(authentication.getName());
        return users.findByUsername(authentication.getName()).map(user -> {
            if (user.getId() == userId) {
                return new ResponseEntity<>(reservations.findByUserId(userId), HttpStatus.OK);
            }
            return new ResponseEntity<List<RoomReservation>>(HttpStatus.UNAUTHORIZED);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

     /**
         * GET Endpoint to retrieve the reservation for a room by ID.
         *
         * @param room_reservation_id Unique identifier of the equipment.
         * @return The requested room reservation {@link RoomReservation}.
         */
        @GetMapping("room_reservations/{room_reservation_id}")
        public @ResponseBody ResponseEntity<RoomReservation>
                getRoomReservationById(@PathVariable(value = "room_reservation_id") long room_reservation_id, Authentication authentication) {
            RoomReservation toReturn = reservations.findById(room_reservation_id).orElseGet(() -> null);

            if (toReturn == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            if (!toReturn.getUser().getUsername().equals(authentication.getName())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>(toReturn, HttpStatus.OK);
        }

    /**
     * GET Endpoint to retrieve a list of all rooms reservations of a user in a given room.
     *
     * @param userId Unique identifier of the user.
     * @param roomId Unique identifier of the room.
     * @return a list of the room reservations in the room by the user {@link RoomReservation}.
     */
    @GetMapping("room_reservations/{user_id}/{room_id}")
    public @ResponseBody ResponseEntity<List<RoomReservation>> getRoomReservationsByUserAndRoom(@PathVariable(value="user_id") long userId, @PathVariable(value="room_id") long roomId, Authentication authentication) {
        return users.findByUsername(authentication.getName()).map(user -> {
            if (user.getId() == userId) return reservations.findByUserIdAndRoomId(userId, roomId).isEmpty() ? new ResponseEntity<List<RoomReservation>>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(reservations.findByUserIdAndRoomId(userId, roomId), HttpStatus.OK);
            return new ResponseEntity<List<RoomReservation>>(HttpStatus.UNAUTHORIZED);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public boolean timeIsValid(Time startTime, Time endTime, List<RoomReservation> allRoomReservations) {
        if (startTime.compareTo(endTime) > 0)
            return false;

        for (RoomReservation roomReservation : allRoomReservations) {
            boolean startTimeIsAfterReservedTime = startTime.compareTo(roomReservation.getStartTime()) > 0 &&
                    startTime.compareTo(roomReservation.getEndTime()) > 0;

            boolean startTimeIsBeforeReservedTime = startTime.compareTo(roomReservation.getStartTime()) < 0 &&
                    startTime.compareTo(roomReservation.getEndTime()) < 0;

            boolean endTimeIsAfterReservedTime = endTime.compareTo(roomReservation.getStartTime()) > 0 &&
                    endTime.compareTo(roomReservation.getEndTime()) > 0;

            boolean endTimeIsBeforeReservedTime = endTime.compareTo(roomReservation.getStartTime()) < 0 &&
                    endTime.compareTo(roomReservation.getEndTime()) < 0;

            // if the start time and the end time are not after the reserved time or they are not before it
            if (!((startTimeIsAfterReservedTime && endTimeIsAfterReservedTime) || (startTimeIsBeforeReservedTime && endTimeIsBeforeReservedTime))) {
                return false;
            }
        }

        return true;
    }

    /**
     * POST Endpoint to add a new room reservation.
     *
     * @param newRoomReservation The new room reservation to add.
     * @return The added room reservation {@link RoomReservation}.
     */
    @PostMapping(value="/room_reservations", consumes = {"application/json"})
    public ResponseEntity<RoomReservation> newRoomReservation(@Valid @RequestBody RoomReservation newRoomReservation, UriComponentsBuilder b, Authentication authentication) {

        if (authentication.getName().equals(newRoomReservation.getUser().getUsername())
                && !users.findByUsername(authentication.getName()).isEmpty()
                && newRoomReservation.getUser().getId() == users.findByUsername(authentication.getName()).get().getId()) {

            List<RoomReservation> all_reservations = reservations.findByDateAndRoomId(newRoomReservation.getDate(), newRoomReservation.getRoom().getId());

            if (!timeIsValid(newRoomReservation.getStartTime(), newRoomReservation.getEndTime(), all_reservations)) {
                return new ResponseEntity(HttpStatus.CONFLICT);
            }
            // Save a newly created object, so that the id will be auto generated
            RoomReservation savedRoomReservation = reservations.save(new RoomReservation(newRoomReservation.getDate(),
                    newRoomReservation.getRoom(),
                    newRoomReservation.getStartTime(),
                    newRoomReservation.getEndTime(),
                    newRoomReservation.getUser())
            );
            UriComponents uri = b.path("/room_reservations/{id}").buildAndExpand(savedRoomReservation.getId());
            return ResponseEntity
                    .created(uri.toUri())
                    .body(savedRoomReservation);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * PUT Endpoint to update the entry of a given room reservation.
     *
     * @param roomReservationId Unique identifier of the room reservation that is to be updated.
     * @param newRoomReservation The updated version of the room reservation.
     * @return the new room reservation that is updated {@link RoomReservation}.
     */
    @PutMapping("room_reservations/{room_reservation_id}")
    public ResponseEntity<RoomReservation> replaceRoomReservation(@RequestBody RoomReservation newRoomReservation, @PathVariable(value="room_reservation_id") long roomReservationId, UriComponentsBuilder b, Authentication authentication) {

        UriComponents uri = b.path("room_reservations/{room_reservation_id}").buildAndExpand(roomReservationId);

        return reservations.findById(roomReservationId)
                .map( roomReservation -> {
                    if (users.findByUsername(authentication.getName()).isEmpty() || newRoomReservation.getUser().getId() != users.findByUsername(authentication.getName()).get().getId()
                    || roomReservation.getUser().getId() != users.findByUsername(authentication.getName()).get().getId())
                        return new ResponseEntity<RoomReservation>(HttpStatus.UNAUTHORIZED);

                    List<RoomReservation> all_reservations = reservations.findByDateAndRoomId(newRoomReservation.getDate(), newRoomReservation.getRoom().getId());
                    if (!timeIsValid(newRoomReservation.getStartTime(), newRoomReservation.getEndTime(), all_reservations))
                        return new ResponseEntity<RoomReservation>(HttpStatus.CONFLICT);

                    roomReservation.setUser(newRoomReservation.getUser());
                    roomReservation.setRoom(newRoomReservation.getRoom());
                    roomReservation.setDate(newRoomReservation.getDate());
                    roomReservation.setStartTime(newRoomReservation.getStartTime());
                    roomReservation.setEndTime(newRoomReservation.getEndTime());

                    return ResponseEntity.created(uri.toUri()).body(reservations.save(roomReservation));
                })
                .orElseGet(() -> {
                    newRoomReservation.setId(roomReservationId);
                    return ResponseEntity.created(uri.toUri()).body(reservations.save(newRoomReservation));
                });
    }

    /**
     * DELETE Endpoint to delete the entry of a given room reservation.
     *
     * @param roomReservationId Unique identifier of the room that is to be deleted. {@link RoomReservation}
     */
    @DeleteMapping("room_reservations/{room_reservation_id}")
    public ResponseEntity<?> deleteRoomReservation(@PathVariable long roomReservationId, Authentication authentication) {

        RoomReservation reservationToDelete = reservations.findById(roomReservationId).orElseGet(() -> null);

        if (reservationToDelete == null) {
            return ResponseEntity.noContent().build();
        }

        if (!reservationToDelete.getUser().getUsername().equals(authentication.getName())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        reservations.deleteById(roomReservationId);
        return ResponseEntity.noContent().build();
    }

}
