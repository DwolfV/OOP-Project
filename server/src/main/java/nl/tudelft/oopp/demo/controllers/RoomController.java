package nl.tudelft.oopp.demo.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import nl.tudelft.oopp.demo.entities.Equipment;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.RoomReservationRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
public class RoomController {

    @Autowired
    RoomRepository rooms;

    /**
     * GET Endpoint to retrieve a list of all rooms.
     *
     * @return a list of the rooms {@link Room}.
     */
    @GetMapping("rooms")
    public @ResponseBody
    List<Room> getRooms() {
        return rooms.findAll();
    }

    /**
     * GET Endpoint to retrieve a list of all rooms in a building.
     *
     * @param id Unique identifier of the building.
     * @return a list of the rooms in the building {@link Room}.
     */
    @GetMapping("rooms/{building_id}")
    public @ResponseBody
    ResponseEntity<List<Room>> getRoomsInBuilding(@PathVariable(value = "building_id") long id) {
        return rooms.findByBuildingId(id).isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(rooms.findByBuildingId(id), HttpStatus.OK);
    }

    @Autowired
    RoomReservationRepository reservationRepository;

    /**
     * GET Endpoint to retrieve a list of all rooms in a building with certain filters applied.
     *
     * @param id       - the id of the building from which the rooms are
     * @param capacity - the minimum capacity of the room
     * @param e1       - the name of a piece of equipment that should be present in the room
     * @param e2       - the name of a piece of equipment that should be present in the room
     * @param e3       - the name of a piece of equipment that should be present in the room
     * @param e4       - the name of a piece of equipment that should be present in the room
     * @param e5       - the name of a piece of equipment that should be present in the room
     * @param e6       - the name of a piece of equipment that should be present in the room
     * @return a list of filtered buildings
     */
    @GetMapping("rooms/filter")
    public @ResponseBody
    List<Room> getFilteredRooms(@RequestParam(name = "building_id") Long id,
                                @RequestParam(name = "date") String date,
                                @RequestParam(name = "user_id") Long userId,
                                @RequestParam(name = "capacity", required = false, defaultValue = "0") Integer capacity,
                                @RequestParam(name = "e1", required = false) String e1,
                                @RequestParam(name = "e2", required = false) String e2,
                                @RequestParam(name = "e3", required = false) String e3,
                                @RequestParam(name = "e4", required = false) String e4,
                                @RequestParam(name = "e4", required = false) String e5,
                                @RequestParam(name = "e4", required = false) String e6) {
        List<Room> result = new ArrayList<>();
        List<String> filters = new ArrayList<>();
        List<Room> roomList = rooms.filterRoom(id, capacity);

        LocalDate currentDate = LocalDate.parse(date);
        //use dayOfWeek = date.getDayOfWeek().getValue() to get the day as a number
        int dayOfWeek = currentDate.getDayOfWeek().getValue();
        System.out.println(dayOfWeek);
        // use date.minusDays(dayOfWeek-1) to get to monday
        currentDate = currentDate.minusDays(dayOfWeek-1);
        for (int i = 0; i <= 6; i++) {
            // get the rooms for the current user and date
            List<RoomReservation> reservations = reservationRepository.findByUserIdAndDateAndRoomBuildingId(userId, currentDate, id);

            System.out.println(currentDate);
            // iterate through the reservations for that date
            for (RoomReservation reservation : reservations) {
                System.out.print(reservation);
                System.out.print(reservation.getRoom());
                //if the room has already been reserved this week
                if (roomList.contains(reservation.getRoom())) {
                    // we remove the room from the result
                    roomList.remove(reservation.getRoom());
                }
            }
            //move to the next date to get the next room reservations
            currentDate = currentDate.plusDays(1);
        }

        if (!(e1 == null)) {
            filters.add(e1);
        }
        if (!(e2 == null)) {
            filters.add(e2);
        }
        if (!(e3 == null)) {
            filters.add(e3);
        }
        if (!(e4 == null)) {
            filters.add(e4);
        }
        if (!(e5 == null)) {
            filters.add(e5);
        }
        if (!(e6 == null)) {
            filters.add(e6);
        }
        int expected = 0;
        //count the filters;
        for (String s : filters) {
            expected++;
        }
        System.out.println("test");
        if (expected == 0) {
            return roomList;
        }
        for (Room room : roomList) {
            List<Equipment> equipmentList = room.getEquipment();
            int count = 0; //to count how many filters the room satisfies
            for (Equipment equipment : equipmentList) {
                if (filters.contains(equipment.getItem().getName())) {
                    count++; //increment the filter counter
                }
                if (count == expected) { //the rooms has reached the expected amount of filters, thus
                    result.add(room); //add the room
                    break; //break the loop
                }
            }

        }


        return result;
    }

    /**
     * POST Endpoint to add a new room.
     *
     * @param newRoom The new room to add.
     * @return The added room {@link Room}.
     */
    @PostMapping(value = "/rooms", consumes = {"application/json"})
    public ResponseEntity<Room> newRoom(@Valid @RequestBody Room newRoom, UriComponentsBuilder b) {
        rooms.save(newRoom);
        UriComponents uri = b.path("/rooms/{id}").buildAndExpand(newRoom.getId());
        return ResponseEntity
            .created(uri.toUri())
            .body(newRoom);
    }

    /**
     * PUT Endpoint to update the entry of a given room.
     *
     * @param roomId  Unique identifier of the room that is to be updated.
     * @param newRoom The updated version of the room.
     * @return the new room that is updated {@link Room}.
     */
    @PutMapping("rooms/{room_id}")
    public ResponseEntity<Room> replaceRoom(@RequestBody Room newRoom, @PathVariable(value = "room_id") long roomId, UriComponentsBuilder b) {

        UriComponents uri = b.path("/rooms/{room_id}").buildAndExpand(roomId);

        Room updatedRoom = rooms.findById(roomId)
            .map(room -> {
                room.setName(newRoom.getName());
                room.setBuilding(newRoom.getBuilding());
                room.setCapacity(newRoom.getCapacity());
                return rooms.save(room);
            })
            .orElseGet(() -> {
                newRoom.setId(roomId);
                return rooms.save(newRoom);
            });

        return ResponseEntity.created(uri.toUri()).body(updatedRoom);
    }

    /**
     * DELETE Endpoint to delete the entry of a given room.
     *
     * @param roomId Unique identifier of the room that is to be deleted. {@link Room}
     */
    @DeleteMapping("rooms/{room_id}")
    public ResponseEntity<?> deleteRoom(@PathVariable(value = "room_id") long roomId) {
        rooms.deleteById(roomId);

        return ResponseEntity.noContent().build();
    }

}
