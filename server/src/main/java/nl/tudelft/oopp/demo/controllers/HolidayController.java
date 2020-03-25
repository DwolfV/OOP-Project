package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import javax.validation.Valid;
import nl.tudelft.oopp.demo.entities.Holiday;
import nl.tudelft.oopp.demo.repositories.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;



public class HolidayController {
    @Autowired
    HolidayRepository holidays;

    /**
     * GET Endpoint to retrieve a list of all holidays.
     *
     * @return message
     */

    @GetMapping("holidays")
    @ResponseBody
    public List<Holiday> getAllHolidays() {
        return holidays.findAll();
    }

    /**
     * POST Endpoint to add a new holiday.
     *
     * @param newHoliday The new Holiday to add.
     * @return The added holiday
     */

    @PostMapping(value = "/holidays", consumes = {"application/json"})
    public ResponseEntity<Holiday> newHoliday(@Valid @RequestBody Holiday newHoliday,
                                              UriComponentsBuilder b) {
        holidays.save(newHoliday);
        UriComponents uri = b.path("/holidays/{id}").buildAndExpand(newHoliday.getId());
        return ResponseEntity.created(uri.toUri()).body(newHoliday);
    }

    /**
    * GET Endpoint to retrieve holiday by ID.
     *
     * @param holiday_id Unique identifier of the equipment.
     * @return The requested equipment {@link Holiday}.
     */
    @GetMapping("holidays/{holiday_id}")
    public @ResponseBody ResponseEntity<Holiday> getHolidayById(@PathVariable long holiday_id) {
        Holiday toReturn = holidays.findById(holiday_id).orElseGet(() -> null);
        return (toReturn == null) ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(toReturn, HttpStatus.OK);
    }

    /**
     * PUT Endpoint to update the entry of a given holiday.
     *
     * @param holiday_id Unique identifier of the holiday that is to be updated
     * @param newHoliday the updated version of the holiday
     * @return the new holiday that is updated
     */

    @PutMapping("holidays/{holiday_id}")
    public ResponseEntity<Holiday> replaceHoliday(@RequestBody Holiday newHoliday,
                                               @PathVariable long holiday_id, UriComponentsBuilder b) {
        UriComponents uri = b.path("holidays/{holiday_id}").buildAndExpand(holiday_id);

        Holiday updatedHoliday = holidays.findById(holiday_id).map(holiday -> {
            holiday.setDate(newHoliday.getDate());
            holiday.setName(newHoliday.getName());
            holiday.setT_close(newHoliday.getT_close());
            holiday.setT_open(newHoliday.getT_open());
            return holidays.save(holiday);
        }).orElseGet(() -> {
            newHoliday.setId(holiday_id);
            return holidays.save(newHoliday);
        });
        return ResponseEntity.created(uri.toUri()).body(updatedHoliday);
    }

    /**
     * DELETE Endpoint to delete the entry of a given holiday.
     *
     * @param holiday_id Unique Identifier of the holiday that is to be deleted.
     */

    @DeleteMapping("holidays/{holiday_id}")
    public ResponseEntity<?> deleteHoliday(@PathVariable long holiday_id) {
        holidays.deleteById(holiday_id);

        return ResponseEntity.noContent().build();
    }

}
