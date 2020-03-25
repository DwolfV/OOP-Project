package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import javax.validation.Valid;
import nl.tudelft.oopp.demo.entities.Occasion;
import nl.tudelft.oopp.demo.repositories.OccasionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;



@RestController
@RequestMapping(path = "/occasion")
public class OccasionController {

    @Autowired
    OccasionRepository occasionRepository;

    /**
     * GET endpoint to retrieve a list of all occasions.
     *
     * @return a list of all occasions
     */
    @GetMapping("/all")
    public List<Occasion> getAllOccasions() {
        return occasionRepository.findAll();
    }

    /**
     * GET endpoint to retrieve a list of all occasions for a building.
     *
     * @return a list of all occasions for a building
     */
    @GetMapping("/building/{building_id}")
    public List<Occasion> getOccasionsByBuilding(@PathVariable(value = "building_id") long id) {
        return occasionRepository.findByBuildingId(id);
    }

    /**
     * GET endpoint to retrieve an occasion.
     *
     * @return a response entity
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<Occasion> getOccasionById(@PathVariable long id) {
        return occasionRepository.findById(id).map(occasion -> new ResponseEntity<>(occasion, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * POST endpoint to add a new occasion.
     *
     * @param occasion - the occasion that is to be added
     * @param o        - a uri component
     * @return         - the added occasion
     */
    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<Occasion> addOccasion(@Valid @RequestBody Occasion occasion, UriComponentsBuilder o) {
        occasionRepository.save(occasion);
        UriComponents uri = o.path("/occasion/id/{id}").buildAndExpand(occasion.getId());
        return ResponseEntity.created(uri.toUri()).body(occasion);
    }

    /**
     * PUT endpoint to update an existing Occasion.
     *
     * @param id            - the id of the occasion that needs to be updated
     * @param newOccasion   - the occasion that has the already updated data
     * @return              - a response entity with the updated occasion
     */
    @PutMapping("/{id}")
    public ResponseEntity<Occasion> updateOccasion(@PathVariable long id,
                                                   @RequestBody Occasion newOccasion) {
        return occasionRepository.findById(id).map(occasion -> {
            occasion.setDate(newOccasion.getDate());
            occasion.setOpenTime(newOccasion.getOpenTime());
            occasion.setCloseTime(newOccasion.getCloseTime());
            occasion.setBuilding(newOccasion.getBuilding());

            return new ResponseEntity<>(occasionRepository.save(occasion), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE endpoint.
     *
     * @param id - the id of the occasion that needs to be deleted
     * @return  200 status code if the occasion was deleted successfully, 404 if it was not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteOccasion(@PathVariable long id) {
        return occasionRepository.findById(id).map(occasion -> {
            occasionRepository.delete(occasion);
            return new ResponseEntity("The occasion has been deleted successfully", HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }
}
