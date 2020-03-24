package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import javax.validation.Valid;
import nl.tudelft.oopp.demo.entities.OpenTime;
import nl.tudelft.oopp.demo.repositories.OpenTimeRepository;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class OpenTimeController {
    @Autowired
    OpenTimeRepository openTimes;

    /**
     * GET EndPoint to retrieve a list of all openTimes.
     *
     * @return a list of the openTimes{@link OpenTime}.
     */

    @GetMapping("openTimes")
    public @ResponseBody
    List<OpenTime> getOpenTimes() {

        return openTimes.findAll();
    }

    /**
     * GET EndPoint to retrieve a list of all openTimes for a building.
     *
     * @param id Unique identifier of the building
     * @return a list of the openTimes for the building {@link OpenTime}.
     */

    @GetMapping("openTimes/building/{building_id}")
    public @ResponseBody ResponseEntity<List<OpenTime>>
        getOpenTimesForBuilding(@PathVariable(value = "building_id") long id /*,
                                                   @RequestParam String parameter*/) {
        return openTimes.findByBuildingId(id).isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(openTimes.findByBuildingId(id), HttpStatus.OK);
    }

    /**
     * GET EndPoint to retrieve a list of all openTimes for a building.
     *
     * @param id Unique identifier of the building
     * @return a list of the openTimes for the building {@link OpenTime}.
     */

    @GetMapping("openTimes/day/{building_id}/{day}")
    public @ResponseBody ResponseEntity<OpenTime>
    getOpenTimesForBuildingByDay(@PathVariable(value = "building_id") long id, @PathVariable String day) {
        return openTimes.findByBuildingIdAndDay(id,day).map(openTime -> ResponseEntity.ok(openTime)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * POST Endpoint to add a new OpenTime.
     *
     * @param newOpenTime The new openTime to add.
     * @return The added openTime {@link OpenTime}.
     */

    @PostMapping(value = "openTimes", consumes = {"application/json"})
    public ResponseEntity<OpenTime> newOpenTime(@Valid @RequestBody OpenTime newOpenTime,
                                                UriComponentsBuilder b) {
        openTimes.save(newOpenTime);
        UriComponents uri = b.path("/openTimes/{id}").buildAndExpand(newOpenTime.getId());
        return ResponseEntity.created(uri.toUri()).body(newOpenTime);
    }

    /**
     * PUT Endpoint to update the entry of a given OpenTime.
     *
     * @param openTime_id Unique identifier of the OpenTime that is to be updated.
     * @param newOpenTime The updated version of the OpenTime.
     * @return the new openTime that is updated {@link OpenTime}.
     */

    @PutMapping("openTimes/{openTime_id}")
    public ResponseEntity<OpenTime> replaceOpenTime(@RequestBody OpenTime newOpenTime, @PathVariable long openTime_id, UriComponentsBuilder b) {
        UriComponents uri = b.path("/openTimes/{openTime_id}").buildAndExpand(openTime_id);

        OpenTime updatedOpenTime = openTimes.findById(openTime_id).map(openTime -> {
            openTime.setDay(newOpenTime.getDay());
            openTime.setBuilding(newOpenTime.getBuilding());
            openTime.setCloseTime(newOpenTime.getCloseTime());
            openTime.setOpenTime(newOpenTime.getOpenTime());
            return openTimes.save(openTime);
        })
                .orElseGet(() -> {
                    newOpenTime.setId(openTime_id);
                    return openTimes.save(newOpenTime);
                });
        return ResponseEntity.created(uri.toUri()).body(updatedOpenTime);
    }


    /**
     * DELETE Endpoint to delete the entry of a given openTime.
     *
     * @param openTime_id Unique identifier of the openTime that is to be deleted. {@link OpenTime}
     */

    @DeleteMapping("openTimes/{openTime_id}")
    public ResponseEntity<?> deleteOpenTime(@PathVariable long openTime_id) {
        openTimes.deleteById(openTime_id);
        return ResponseEntity.noContent().build();
    }

}
