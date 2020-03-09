package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Equipment;
import nl.tudelft.oopp.demo.repositories.EquipmentRepository;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@Controller
public class EquipmentController {

    @Autowired
    EquipmentRepository equipment;

    /**
     * GET Endpoint to retrieve a list of the equipment.
     *
     * @return a list of the equipment {@link Equipment}.
     */
    @GetMapping("equipment")
    public @ResponseBody
    List<Equipment> getEquipment() {
        return equipment.findAll();
    }

    /**
     * GET Endpoint to retrieve equipment by ID.
     *
     * @param id Unique identifier of the equipment.
     * @return The requested equipment {@link Equipment}.
     */
    @GetMapping("equipment/{id}")
    public @ResponseBody ResponseEntity<Equipment> getEquipmentById(@PathVariable(value="id") long id ) {
        Equipment toReturn = equipment.findById(id).orElseGet(() -> null);
        return (toReturn == null) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(toReturn, HttpStatus.OK);
    }

    /**
     * GET Endpoint to retrieve equipment by name.
     *
     * @param equipmentName Name of the equipment.
     * @return List of the requested equipment {@link Equipment}.
     */
    @GetMapping("equipment/name/{name}")
    public @ResponseBody ResponseEntity<List<Equipment>> getEquipmentByName(@PathVariable(value="name") String equipmentName ) {
        return equipment.findByName(equipmentName).isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(equipment.findByName(equipmentName), HttpStatus.OK);
    }

    /**
     * GET Endpoint to retrieve equipment by room ID.
     *
     * @param roomId Unique identifier of room in which the equipment is located.
     * @return List of the requested equipment {@link Equipment}.
     */
    @GetMapping("equipment/room/{room_id}")
    public @ResponseBody ResponseEntity<List<Equipment>> getEquipmentByRoomId(@PathVariable(value="room_id") long roomId ) {
        return equipment.findByRoomId(roomId).isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(equipment.findByRoomId(roomId), HttpStatus.OK);
    }

    /**
     * POST Endpoint to add a new equipment.
     *
     * @param newEquipment The new equipment to add.
     * @return The added equipment {@link Equipment}.
     */

    @PostMapping(value="/equipment", consumes = {"application/json"})
    public ResponseEntity<Equipment> addNewEquipment(@Valid @RequestBody Equipment newEquipment, UriComponentsBuilder b) {
        equipment.save(newEquipment);
        UriComponents uri = b.path("/equipment/{id}").buildAndExpand(newEquipment.getId());
        return ResponseEntity
                .created(uri.toUri())
                .body(newEquipment);
    }

    /**
     * PUT Endpoint to update the entry of a given equipment.
     *
     * @param id Unique identifier of the equipment that is to be updated.
     * @param newEquipment The updated version of the equipment.
     * @return the new equipment that is updated {@link Equipment}.
     */
    @PutMapping(value="equipment/{id}", consumes = {"application/json"})
    public ResponseEntity<Equipment> replaceEquipment(@Valid @RequestBody Equipment newEquipment, @PathVariable long id, UriComponentsBuilder b) {

        UriComponents uri = b.path("/equipment/{id}").buildAndExpand(id);

        Equipment updatedEquipment = equipment.findById(id)
                .map( equipment -> {
                    equipment.setName(newEquipment.getName());
                    equipment.setRoom(newEquipment.getRoom());
                    equipment.setAmount(newEquipment.getAmount());
                    return this.equipment.save(equipment);
                })
                .orElseGet(() -> { newEquipment.setId(id);
                    return this.equipment.save(newEquipment);
                });

        return ResponseEntity.created(uri.toUri()).body(updatedEquipment);
    }

    /**
     * DELETE Endpoint to delete the entry of an equipment.
     *
     * @param id Unique identifier of the equipment that is to be deleted. {@link Equipment}
     */
    @DeleteMapping("equipment/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable long id) {
        // TODO equipment with that id may not exist
        equipment.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
