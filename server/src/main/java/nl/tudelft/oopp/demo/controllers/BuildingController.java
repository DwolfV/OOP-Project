package nl.tudelft.oopp.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Equipment;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class BuildingController {
    @Autowired
    BuildingRepository rep;

    /**
     * Find all buildings
     *
     * @return message
     */
    @GetMapping("/building")
    @ResponseBody
    public List<Building> getAllBuildings() {
        return rep.findAll();
    }

    /**
     * Find building by id
     *
     * @param id - The id of the building that is to be found
     * @return the building and 200 status code if the building is found, 404 status code otherwise
     */
    @GetMapping("/building/{id}")
    public ResponseEntity<Building> getBuildingById(@PathVariable long id) {
        return rep.findById(id).map(building -> ResponseEntity.ok(building)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Find building by name
     *
     * @param name - The name of the building that is to be found
     * @return the building and 200 status code if the building is found, 404 status code otherwise
     */
    @GetMapping("/building/name/{name}")
    public List<Building> getBuildingByName(@PathVariable String name) {
        return rep.findByName(name);
    }

    /**
     * Filter a list of building with some given parameters
     * @param capacity - the capacity of the room
     * @param e1 - name of a piece of equipment
     * @param e2 - name of a piece of equipment
     * @param e3 - name of a piece of equipment
     * @param e4 - name of a piece of equipment
     * @return a list of filtered buildings by their room capacity and other equipment pieces
     */
    @GetMapping("/building/filter")
    public List<Building> getFilteredBuildings(@RequestParam (name = "capacity", required = false, defaultValue = "0") Integer capacity,
                                               @RequestParam (name = "e1", required = false) String e1,
                                               @RequestParam (name = "e2", required = false) String e2,
                                               @RequestParam (name = "e3", required = false) String e3,
                                               @RequestParam (name = "e4", required = false) String e4) {
        List<Building> result = new ArrayList<>();
        List<String> filters = new ArrayList<>();
        List<Building> buildings = rep.filterBuilding(capacity);

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
        int expected = 0;
        //count the filters;
        for (String s : filters) {
            expected++;
        }

        if (expected == 0) {
            return buildings;
        }

        for (Building building : buildings) {
            List<Room> rooms = building.getRooms();
            for (Room room : rooms) {
                List<Equipment> equipmentList = room.getEquipment();
                int count = 0; //to count how many filters the room satisfies
                for (Equipment equipment : equipmentList) {
                    if (filters.contains(equipment.getItem().getName())) {
                        count++; //increment the filter counter
                    }
                    if (count == expected) { //the rooms has reached the expected amount of filters, thus
                        result.add(building); //add the building
                        break; //break the loop
                    }
                }
                if (result.contains(building)) {
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Create a new building
     *
     * @return message
     */
    @PostMapping(value = "/building", consumes = {"application/json"})
    public ResponseEntity<Building> newBuilding(@Valid @RequestBody Building building, UriComponentsBuilder uri) {
        rep.save(building);
        UriComponents uriComponents = uri.path("/building/{id}").buildAndExpand(building.getId());
        return ResponseEntity.created(uriComponents.toUri()).body(building);
    }

    /**
     * Update a building
     *
     * @param id          -The id of the building that is to be updated
     * @param newBuilding - The building instance that has the modified parameters
     * @return a response: the updated building and the status 200 if the update was successful, 404 if the building was not found
     */
    @PutMapping("/building/{id}")
    public ResponseEntity<Building> updateBuilding(@PathVariable long id,
                                                   @RequestBody Building newBuilding) {
        return rep.findById(id).map(building -> {
            building.setCity(newBuilding.getCity());
            building.setName(newBuilding.getName());
            building.setOpenTime(newBuilding.getOpenTime());
            building.setCloseTime(newBuilding.getCloseTime());
            building.setStreetName(newBuilding.getStreetName());
            building.setStreetNumber(newBuilding.getStreetNumber());

            return new ResponseEntity<>(rep.save(building), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Delete a building by it's id
     *
     * @param id - The id of the building that is to be deleted
     * @return a response status: 200 if the building has been deleted successfully, 404 if the building was not found
     */
    @DeleteMapping("/building/{id}")
    public ResponseEntity deleteBuilding(@PathVariable long id) {
        return rep.findById(id).map(building -> {
            rep.delete(building);
            return new ResponseEntity("The building has been deleted successfully", HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }
}