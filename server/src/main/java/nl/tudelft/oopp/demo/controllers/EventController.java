package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import javax.validation.Valid;
import nl.tudelft.oopp.demo.entities.Event;
import nl.tudelft.oopp.demo.repositories.EventRepository;
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
@RequestMapping(path = "/event")
public class EventController {

    @Autowired
    EventRepository rep;

    @GetMapping("/all")
    public List<Event> getAllEvents() {
        return rep.findAll();
    }

    @GetMapping("/user/{user_id}")
    public List<Event> getEventsByUserId(@PathVariable(value = "user_id") long id) {
        return rep.findByUserId(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable long id) {
        return rep.findById(id).map(event -> new ResponseEntity<>(event, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * POST Endpoint to add a new event.
     *
     * @param event the new event to be added
     * @return the added event
     */

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<Event> addEvent(@Valid @RequestBody Event event, UriComponentsBuilder e) {
        try{
            rep.save(event);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        UriComponents uri = e.path("/event/{id}").buildAndExpand(event.getId());
        return ResponseEntity.created(uri.toUri()).body(event);
    }

    /**
     * Updates an Event.
     *
     * @throws Exception if communication with the server fails or if the response is not proper json.
     */

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable long id,
                                                   @RequestBody Event newEvent) {
        return rep.findById(id).map(event -> {
            event.setName(newEvent.getName());
            event.setDescription(newEvent.getDescription());
            event.setDate(newEvent.getDate());
            event.setEndTime(newEvent.getEndTime());
            event.setStartTime(newEvent.getStartTime());

            Event eventToReturn;
            try {
                eventToReturn = rep.save(event);
            } catch (Exception e) {
                return new ResponseEntity<Event>(HttpStatus.CONFLICT);

            }

            return new ResponseEntity<>(eventToReturn, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE Endpoint to delete the entry of a given event.
     *
     * @param id unique identifier of the user that is to be deleted.
     */

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEvent(@PathVariable long id) {
        return rep.findById(id).map(event -> {
            rep.delete(event);
            return new ResponseEntity("The event has been deleted successfully", HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }
}
