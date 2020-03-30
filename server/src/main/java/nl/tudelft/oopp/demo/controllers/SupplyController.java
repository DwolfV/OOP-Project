package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import javax.validation.Valid;
import nl.tudelft.oopp.demo.entities.Supply;
import nl.tudelft.oopp.demo.repositories.SupplyRepository;
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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class SupplyController {

    @Autowired
    SupplyRepository rep;

    /**
     * Find all the supplies.
     * @return message
     */
    @GetMapping("/supply")
    @ResponseBody
    public List<Supply> getAllSupplies() {
        return rep.findAll();
    }

    /**
     * Find supply by building and name.
     *
     * @param name - The name of the supply that is to be found
     * @param id - The building id of which the supply is part of
     * @return the supply and 200 status code if the supply is found, 404 status code otherwise
     */
    @GetMapping("/supply/building")
    public ResponseEntity<Supply> getSupplyByBuildingIdAndName(@RequestParam String name, @PathVariable(value = "building_id") long id) {
        return rep.findByBuildingIdAndName(id, name).map(supply -> ResponseEntity.ok(supply)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
         * GET Endpoint to retrieve a list of all rooms in a building.
         *
         * @param id Unique identifier of the building.
         * @return a list of the supplies in the building {@link Supply}.
         */

    @GetMapping("/supply/{building_id}")
    public @ResponseBody ResponseEntity<List<Supply>> getSupplyByBuildingId(@PathVariable(value = "building_id") long id) {
        return rep.findByBuildingId(id).isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(rep.findByBuildingId(id), HttpStatus.OK);
    }

    /**
     * Find supply by id.
     *
     * @param id - The id of the supply that is to be found
     * @return the supply and 200 status code if the supply is found, 404 status code otherwise
     */
    @GetMapping("/supply/{supply_id}")
    public ResponseEntity<Supply> getSupplyById(@PathVariable long id) {
        return rep.findById(id).map(supply -> ResponseEntity.ok(supply)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * POST Endpoint to add a new supply.
     *
     * @param newSupply The new supply to add.
     * @return The added supply {@link Supply}.
     */

    @PostMapping(value = "/supply", consumes = {"application/json"})
    public ResponseEntity<Supply> newSupply(@Valid @RequestBody Supply newSupply, UriComponentsBuilder b) {
        rep.save(newSupply);
        UriComponents uri = b.path("/supply/{supply_id}").buildAndExpand(newSupply.getId());
        return ResponseEntity.created(uri.toUri()).body(newSupply);
    }

    /**
     * PUT Endpoint to update the entry of a given supply.
     * @param supplyId Unique identifier of the supply that is to be updated.
     * @param newSupply The updated version of the supply.
     * @return the new room that is updated {@link Supply}.
     */
    
    @PutMapping("/supply/{supply_id}")
    public ResponseEntity<Supply> updateSupply(@RequestBody Supply newSupply, @PathVariable long supplyId,
                                                   UriComponentsBuilder builder) {
        UriComponents uriComponents = builder.path("/supply/{supply_id}").buildAndExpand(supplyId);

        Supply updatedSupply = rep.findById(supplyId).map(supply -> {
            supply.setBuilding(newSupply.getBuilding());
            supply.setName(newSupply.getName());
            supply.setStock(newSupply.getStock());
            return rep.save(supply);
        }).orElseGet(() -> {
            newSupply.setId(supplyId);
            return rep.save(newSupply);
        });

        return ResponseEntity.created(uriComponents.toUri()).body(updatedSupply);
    }

    /**
         * DELETE Endpoint to delete the entry of a given supply.
         *
         * @param supplyId Unique identifier of the supply that is to be deleted. {@link Supply}
         */

    @DeleteMapping("/supply/{supply_id}")
    public ResponseEntity deleteSupply(@PathVariable long supplyId) {
        rep.deleteById(supplyId);

        return ResponseEntity.noContent().build();
    }
}

