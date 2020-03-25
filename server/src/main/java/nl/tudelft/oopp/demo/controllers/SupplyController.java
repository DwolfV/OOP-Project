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
     * Find supply by building and name.
     *
     * @param id - The building id of which the supply is part of
     * @return the supply and 200 status code if the supply is found, 404 status code otherwise
     */
    @GetMapping("/supply/building/{building_id}")
    public ResponseEntity<List<Supply>> getSupplyByBuildingId(@PathVariable(value = "building_id") long id) {
        return rep.findByBuildingId(id).isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(rep.findByBuildingId(id), HttpStatus.OK);
    }

    /**
     * Find supply by id.
     *
     * @param id - The id of the supply that is to be found
     * @return the supply and 200 status code if the supply is found, 404 status code otherwise
     */
    @GetMapping("/supply/{id}")
    public ResponseEntity<Supply> getSupplyById(@PathVariable long id) {
        return rep.findById(id).map(supply -> ResponseEntity.ok(supply)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Create a new supply.
     * @return message
     */

    @PostMapping(value = "/supply", consumes = {"application/json"})
    public ResponseEntity<Supply> newSupply(@Valid @RequestBody Supply newSupply, UriComponentsBuilder b) {
        rep.save(newSupply);
        UriComponents uri = b.path("/supply/{id}").buildAndExpand(newSupply.getId());
        return ResponseEntity.created(uri.toUri()).body(newSupply);
    }
    /**
     * Update a supply.
     *
     * @param id          -The id of the supply that is to be updated
     * @param newSupply - The supply instance that has the modified parameters
     * @return a response: the updated supply and the status 200 if the update was successful, 404 if the building was not found
     */
    @PutMapping("/supply/{id}")
    public ResponseEntity<Supply> updateSupply(@PathVariable long id,
                                                   @RequestBody Supply newSupply) {
        return rep.findById(id).map(supply -> {
            supply.setStock(newSupply.getStock());
            supply.setName(newSupply.getName());

            return new ResponseEntity<>(rep.save(supply), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Delete a supply by it's id.
     *
     * @param id - The id of the supply that is to be deleted
     * @return a response status: 200 if the supply has been deleted successfully, 404 if the supply was not found
     */
    @DeleteMapping("/supply/{id}")
    public ResponseEntity deleteSupply(@PathVariable long id) {
        return rep.findById(id).map(supply -> {
            rep.delete(supply);
            return new ResponseEntity("The supply has been deleted successfully", HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }
}

