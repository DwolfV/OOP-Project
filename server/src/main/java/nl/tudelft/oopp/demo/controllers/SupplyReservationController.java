package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import javax.validation.Valid;

import nl.tudelft.oopp.demo.entities.Supply;
import nl.tudelft.oopp.demo.entities.SupplyReservation;
import nl.tudelft.oopp.demo.repositories.SupplyRepository;
import nl.tudelft.oopp.demo.repositories.SupplyReservationRepository;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/supply_reservations")
public class SupplyReservationController {

    @Autowired
    SupplyReservationRepository supplyReservationRepository;

    /**
     * GET Endpoint to retrieve a list of all supply reservations.
     *
     * @return a list of the supply reservations {@link SupplyReservation}.
     */

    @GetMapping("/all")
    public List<SupplyReservation> getSupplyReservations() {
        return supplyReservationRepository.findAll();
    }

    /**
     * GET Endpoint to retrieve a list of all supply reservations for a user.
     *
     * @return a list of the supply reservation for the given user {@link SupplyReservation}
     */
    @GetMapping("/{user_id}")
    public List<SupplyReservation> getSupplyReservationsByUser(@PathVariable(value = "user_id") long userId) {
        return supplyReservationRepository.findByUserId(userId);
    }

    /**
     * GET Endpoint to retrieve the reservation for a supply by ID.
     *
     * @param supplyReservationId Unique identifier of the equipment.
     * @return The requested equipment {@link SupplyReservation}.
     */
    @GetMapping("/reservation/{supply_reservation_id}")
    public ResponseEntity<SupplyReservation> getSupplyReservationById(@PathVariable(value = "supply_reservation_id") long supplyReservationId) {
        SupplyReservation toReturn = supplyReservationRepository.findById(supplyReservationId).orElseGet(() -> null);
        return (toReturn == null) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(toReturn, HttpStatus.OK);
    }

    /**
     * POST Endpoint to add a new supply reservation.
     *
     * @param newSupplyReservation The new supply reservation to add.
     * @return The added supply reservation {@link SupplyReservation }.
     */

    @PostMapping(value = "/add", consumes = {"application/json"})
    public ResponseEntity<SupplyReservation> newSupplyReservation(@Valid @RequestBody SupplyReservation newSupplyReservation, UriComponentsBuilder b) {
        try {
            supplyReservationRepository.save(newSupplyReservation);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        UriComponents uriComponents = b.path("supply_reservations/reservation/{id}").buildAndExpand(newSupplyReservation.getId());
        return ResponseEntity.created(uriComponents.toUri()).body(newSupplyReservation);
    }

    /**
     * PUT Endpoint to update the entry of a given supply reservation.
     *
     * @param supplyReservationId Unique identifier of the supply reservation that is to be uploaded.
     * @param newSupplyReservation  The updated version of the supply reservation.
     * @return the new supply reservation that is updated {@link SupplyReservation}.
     */

    @PutMapping("/{supply_reservation_id}")
    public ResponseEntity<SupplyReservation> replaceSupplyReservation(@RequestBody SupplyReservation newSupplyReservation,
                                                                      @PathVariable(value = "supply_reservation_id") long supplyReservationId,
                                                                      UriComponentsBuilder b) {
        UriComponents uriComponents = b.path("supply_reservation/{supply_reservation_id}").buildAndExpand(supplyReservationId);
        return supplyReservationRepository.findById(supplyReservationId).map(supplyReservation -> {
            supplyReservation.setAmount(newSupplyReservation.getAmount());
            supplyReservation.setDate(newSupplyReservation.getDate());
            supplyReservation.setSupply(newSupplyReservation.getSupply());
            supplyReservation.setUser(newSupplyReservation.getUser());

            SupplyReservation supplyReservationReturn;
            try {
                supplyReservationReturn = supplyReservationRepository.save(supplyReservation);
            } catch (Exception e) {
                return new ResponseEntity<SupplyReservation>(HttpStatus.CONFLICT);
            }

            return new ResponseEntity<>(supplyReservationReturn, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE Endpoint to delete the entry of a given supply reservation.
     *
     * @param supplyReservationId Unique identifier of the supply reservation that is to be deleted. {@link SupplyReservation}
     */

    @DeleteMapping("/{supply_reservation_id}")
    public ResponseEntity<?> delete(@PathVariable(value = "supply_reservation_id") long supplyReservationId) {
        supplyReservationRepository.deleteById(supplyReservationId);

        return ResponseEntity.noContent().build();
    }
}
