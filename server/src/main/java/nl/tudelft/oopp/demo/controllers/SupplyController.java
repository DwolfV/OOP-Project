package nl.tudelft.oopp.demo.controllers;

import java.util.List;

import nl.tudelft.oopp.demo.entities.Supply;
import nl.tudelft.oopp.demo.repositories.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class SupplyController {
    @Autowired
    SupplyRepository rep;

    /**
     * Find all the supplies
     * @return message
     */
    @GetMapping("/supply")
    @ResponseBody
    public List<Supply> getAllSupplies() {
        return rep.findAll();
    }

    /**
     * Find supply by building and name
     *
     * @param name - The name of the supply that is to be found
     * @param building - The building of which the supply is part of
     * @return the supply and 200 status code if the supply is found, 404 status code otherwise
     */
    @GetMapping("/supply/building")
    public ResponseEntity<Supply> getSupplyByBuildingAndName(@RequestParam String name, @RequestParam long building) {
        return rep.findByBuildingAndName(building, name).map(supply -> ResponseEntity.ok(supply)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Find supply by building and name
     *
     * @param building - The building of which the supply is part of
     * @return the supply and 200 status code if the supply is found, 404 status code otherwise
     */
    @GetMapping("/supply/building/{building}")
    public ResponseEntity<List<Supply>> getSupplyByBuilding(@PathVariable long building) {
        return rep.findByBuilding(building).map(supply -> ResponseEntity.ok(supply)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Find supply by id
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
     * Create a new supply
     * @return message
     */
    @PostMapping(value = "/supply", consumes = {"application/json"})
    public Supply createNewActivity(@Valid @RequestBody Supply supply) {
        return rep.save(supply);
    }

    /**
     * Update a supply
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
     * Delete a supply by it's id
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
