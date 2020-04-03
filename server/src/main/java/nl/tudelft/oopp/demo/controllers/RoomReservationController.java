package nl.tudelft.oopp.demo.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Occasion;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.OccasionRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
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

    @Autowired
    BuildingRepository buildings;

    @Autowired
    RoomRepository rooms;

    @Autowired
    OccasionRepository occasionRepository;

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
     * GET Endpoint to retrieve a map of all room reservation start and end times.
     *
     * @return a map of the room reservation times {@link RoomReservation}.
     */
    @GetMapping(value = "room_reservations_times/{room_id}", consumes = {"application/json"})
    public @ResponseBody
    ResponseEntity<Map<String, String>> getRoomReservationTimesByRoomAndDay(@PathVariable(value = "room_id") long roomId, @Valid @RequestBody LocalDate date) {
        Map<String, String> unavailableTimes = new HashMap<>();

        for (Object[] times : reservations.findStartAndEndTimesByRoomIdAndDate(roomId, date)) {
            LocalTime startTime = (LocalTime) times[0];
            LocalTime endTime = (LocalTime) times[1];
            unavailableTimes.put(startTime.toString(), endTime.toString());
        }

        return new ResponseEntity<>(unavailableTimes, HttpStatus.OK);
    }

    /**
     * GET Endpoint to retrieve a map of all room reservation start and end times.
     *
     * @return a map of the room reservation times {@link RoomReservation}.
     */
    @GetMapping(value = "room_reservations_times/room/{room_id}")
    public @ResponseBody
    ResponseEntity<List<RoomReservation>> getRoomReservationTimesByRoom(@PathVariable(value = "room_id") long roomId) {
        Map<String, String> unavailableTimes = new HashMap<>();

        List<RoomReservation> reservations = new ArrayList<>();
        for (RoomReservation reservation : this.reservations.findByRoomId(roomId)) {
            reservation.setUser(null);
            reservations.add(reservation);
        }

        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    /**
     * GET Endpoint to retrieve a list of all room reservations for a user.
     *
     * @return a list of the room reservations for the given user {@link RoomReservation}.
     */
    @GetMapping("room_reservations/user/{user_id}")
    public @ResponseBody
    ResponseEntity<List<RoomReservation>> getRoomReservationsByUser(@PathVariable(value = "user_id") long userId,
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
     * @param roomReservationId Unique identifier of the equipment.
     * @return The requested room reservation {@link RoomReservation}.
     */
    @GetMapping("room_reservations/{room_reservation_id}")
    public @ResponseBody
    ResponseEntity<RoomReservation> getRoomReservationById(@PathVariable(value = "room_reservation_id") long roomReservationId,
                                                           Authentication authentication) {
        RoomReservation toReturn = reservations.findById(roomReservationId).orElseGet(() -> null);
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
    public @ResponseBody
    ResponseEntity<List<RoomReservation>> getRoomReservationsByUserAndRoom(@PathVariable(value = "user_id") long userId,
                                                                           @PathVariable(value = "room_id") long roomId,
                                                                           Authentication authentication) {
        return users.findByUsername(authentication.getName()).map(user -> {
            if (user.getId() == userId) {
                return reservations.findByUserIdAndRoomId(userId, roomId).isEmpty()
                    ? new ResponseEntity<List<RoomReservation>>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(reservations.findByUserIdAndRoomId(userId, roomId), HttpStatus.OK);
            }
            return new ResponseEntity<List<RoomReservation>>(HttpStatus.UNAUTHORIZED);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Checks if a given time frame is valid and available.
     *
     * @param newRoomReservation  The new room reservation object whose times need to be checked
     * @param allRoomReservations A list of all room reservations that have all unavailable time periods
     * @return A boolean - true if the time slot is available and valid; false otherwise
     */
    public boolean timeIsValid(RoomReservation newRoomReservation, List<RoomReservation> allRoomReservations) {
        // TODO ROLE_EMPLOYEE?

        LocalTime startTime = newRoomReservation.getStartTime();
        LocalTime endTime = newRoomReservation.getEndTime();

        // get the building from the room
        Building building = buildings.findById(rooms.findById(newRoomReservation.getRoom().getId()).get().getBuilding().getId()).get();

        List<Occasion> occasions = occasionRepository.findByBuildingIdAndDate(building.getId(), newRoomReservation.getDate().plusDays(1));
        LocalTime buildingOpenTime = null;
        LocalTime buildingCloseTime = null;

        // if there is a special occasion on the given day, override the building's opening and closing times
        for (Occasion occasion : occasions) {
            buildingOpenTime = occasion.getOpenTime();
            buildingCloseTime = occasion.getCloseTime();
        }
        if (buildingOpenTime == null || buildingCloseTime == null) {
            buildingOpenTime = building.getOpenTime();
            buildingCloseTime = building.getCloseTime();
        }

        // compare the times of the room reservation to the building's opening and closing times
        if (buildingOpenTime.compareTo(startTime) > 0 || buildingOpenTime.compareTo(endTime) > 0) {
            return false;
        }

        if (buildingCloseTime.compareTo(startTime) < 0 || buildingCloseTime.compareTo(endTime) < 0) {
            return false;
        }

        if (startTime.compareTo(endTime) > 0) {
            return false;
        }

        String startTimeString = startTime.format(DateTimeFormatter.ISO_TIME);
        String endTimeString = endTime.format(DateTimeFormatter.ISO_TIME);
        String startSec = startTimeString.split(":")[2];
        String startMin = startTimeString.split(":")[1];
        String endSec = endTimeString.split(":")[2];
        String endMin = endTimeString.split(":")[1];

        if (!startSec.equals("00") || !endSec.equals("00")) {
            return false;
        }

        if ((!startMin.equals("00") && !startMin.equals("30")) || (!endMin.equals("00") && !endMin.equals("30"))) {
            return false;
        }

        for (RoomReservation roomReservation : allRoomReservations) {
            // don't check if the times of the newRoomReservation overlap with itself
            if (newRoomReservation.getId() == roomReservation.getId()) {
                continue;
            }

            boolean startTimeIsAfterReservedTime = startTime.compareTo(roomReservation.getStartTime()) >= 0
                && startTime.compareTo(roomReservation.getEndTime()) >= 0;

            boolean startTimeIsBeforeReservedTime = startTime.compareTo(roomReservation.getStartTime()) <= 0
                && startTime.compareTo(roomReservation.getEndTime()) <= 0;

            boolean endTimeIsAfterReservedTime = endTime.compareTo(roomReservation.getStartTime()) >= 0
                && endTime.compareTo(roomReservation.getEndTime()) >= 0;

            boolean endTimeIsBeforeReservedTime = endTime.compareTo(roomReservation.getStartTime()) <= 0
                && endTime.compareTo(roomReservation.getEndTime()) <= 0;

            // if the start time and the end time are not after the reserved time or they are not before it
            if (!((startTimeIsAfterReservedTime && endTimeIsAfterReservedTime) || (startTimeIsBeforeReservedTime && endTimeIsBeforeReservedTime))) {
                return false;
            }
        }

        LocalDate dateNow = LocalDate.now();
        // if the date in the room reservation is in the past
        if (newRoomReservation.getDate().compareTo(dateNow) < 0) {
            return false;
        }

        // if the date is more than two weeks from now
        LocalDate dateTwoWeeksFromNow = dateNow.plusWeeks(2);
        if (newRoomReservation.getDate().compareTo(dateTwoWeeksFromNow) > 0) {
            return false;
        }

        // if the room reservation is for today
        if (newRoomReservation.getDate().equals(dateNow)) {
            // if the start time is in the past
            LocalTime timeNow = LocalTime.now();
            return newRoomReservation.getStartTime().compareTo(timeNow) >= 0;
        }
        return true;
    }

    /**
     * POST Endpoint to add a new room reservation.
     *
     * @param newRoomReservation The new room reservation to add.
     * @return The added room reservation {@link RoomReservation}.
     */
    @PostMapping(value = "/room_reservations", consumes = {"application/json"})
    public ResponseEntity<RoomReservation> newRoomReservation(@Valid @RequestBody RoomReservation newRoomReservation, UriComponentsBuilder b, Authentication authentication) {

        if (authentication.getName().equals(newRoomReservation.getUser().getUsername())
            && !users.findByUsername(authentication.getName()).isEmpty()
            && newRoomReservation.getUser().getId() == users.findByUsername(authentication.getName()).get().getId()) {

            List<RoomReservation> allReservations = reservations.findByDateAndRoomId(newRoomReservation.getDate(), newRoomReservation.getRoom().getId());

            if (userHasReservedAlready(newRoomReservation)) {
                return new ResponseEntity(HttpStatus.CONFLICT);
            }

            if (!timeIsValid(newRoomReservation, allReservations)) {
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

    private boolean userHasReservedAlready(RoomReservation newRoomReservation) {
        LocalTime startTime = newRoomReservation.getStartTime();
        LocalTime endTime = newRoomReservation.getEndTime();
        for (RoomReservation roomReservation : reservations.findByUserIdAndDate(newRoomReservation.getUser().getId(), newRoomReservation.getDate())) {
            // don't check if the times of the newRoomReservation overlap with itself
            if (newRoomReservation.getId() == roomReservation.getId()) {
                continue;
            }

            boolean startTimeIsAfterReservedTime = startTime.compareTo(roomReservation.getStartTime()) >= 0
                && startTime.compareTo(roomReservation.getEndTime()) >= 0;

            boolean startTimeIsBeforeReservedTime = startTime.compareTo(roomReservation.getStartTime()) <= 0
                && startTime.compareTo(roomReservation.getEndTime()) <= 0;

            boolean endTimeIsAfterReservedTime = endTime.compareTo(roomReservation.getStartTime()) >= 0
                && endTime.compareTo(roomReservation.getEndTime()) >= 0;

            boolean endTimeIsBeforeReservedTime = endTime.compareTo(roomReservation.getStartTime()) <= 0
                && endTime.compareTo(roomReservation.getEndTime()) <= 0;
            // if the user has already booked a room in that time period
            if (!((startTimeIsAfterReservedTime && endTimeIsAfterReservedTime) || (startTimeIsBeforeReservedTime && endTimeIsBeforeReservedTime))) {
                return true;
            }
        }

        // if the user has reserved this room at some point during the week
        int weekOfNewReservation = newRoomReservation.getDate().get(WeekFields.ISO.weekOfWeekBasedYear());
        for (RoomReservation reservation : reservations.findByUserIdAndRoomId(newRoomReservation.getUser().getId(), newRoomReservation.getRoom().getId())) {
            int weekOfRes = reservation.getDate().plusDays(1).get(WeekFields.ISO.weekOfWeekBasedYear());
            if (weekOfNewReservation == weekOfRes && reservation.getId() != newRoomReservation.getId() && reservation.getDate().getYear() == newRoomReservation.getDate().getYear()) {
                return true;
            }
        }
        return false;
    }

    /**
     * PUT Endpoint to update the entry of a given room reservation.
     *
     * @param roomReservationId  Unique identifier of the room reservation that is to be updated.
     * @param newRoomReservation The updated version of the room reservation.
     * @return the new room reservation that is updated {@link RoomReservation}.
     */
    @PutMapping("room_reservations/{room_reservation_id}")
    public ResponseEntity<RoomReservation> replaceRoomReservation(@RequestBody RoomReservation newRoomReservation,
                                                                  @PathVariable(value = "room_reservation_id") long roomReservationId,
                                                                  UriComponentsBuilder b, Authentication authentication) {

        UriComponents uri = b.path("room_reservations/{room_reservation_id}").buildAndExpand(roomReservationId);

        return reservations.findById(roomReservationId)
            .map(roomReservation -> {
                if (users.findByUsername(authentication.getName()).isEmpty() || newRoomReservation.getUser().getId() != users.findByUsername(authentication.getName()).get().getId()
                    || roomReservation.getUser().getId() != users.findByUsername(authentication.getName()).get().getId()) {
                    return new ResponseEntity<RoomReservation>(HttpStatus.UNAUTHORIZED);
                }

                List<RoomReservation> allReservations = reservations.findByDateAndRoomId(newRoomReservation.getDate(), newRoomReservation.getRoom().getId());

                if (userHasReservedAlready(newRoomReservation)) {
                    return new ResponseEntity<RoomReservation>(HttpStatus.CONFLICT);
                }

                if (!timeIsValid(newRoomReservation, allReservations)) {
                    return new ResponseEntity<RoomReservation>(HttpStatus.CONFLICT);
                }

                // users should not be able to delete room reservations for previous days
                if (newRoomReservation.getDate().compareTo(LocalDate.now()) < 0
                    || (LocalDate.now().equals(newRoomReservation.getDate())
                    && newRoomReservation.getStartTime().compareTo(LocalTime.now()) < 0)) {
                    return new ResponseEntity<RoomReservation>(HttpStatus.CONFLICT);
                }

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
    public ResponseEntity<?> deleteRoomReservation(@PathVariable(value = "room_reservation_id") long roomReservationId, Authentication authentication) {

        RoomReservation reservationToDelete = reservations.findById(roomReservationId).orElseGet(() -> null);

        if (reservationToDelete == null) {
            return ResponseEntity.noContent().build();
        }

        if (!reservationToDelete.getUser().getUsername().equals(authentication.getName())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // users should not be able to delete room reservations for previous days
        if (reservationToDelete.getDate().plusDays(1).compareTo(LocalDate.now()) < 0
            || (LocalDate.now().equals(reservationToDelete.getDate().plusDays(1))
            && reservationToDelete.getStartTime().compareTo(LocalTime.now()) < 0)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        reservations.deleteById(roomReservationId);
        return ResponseEntity.noContent().build();
    }

}
