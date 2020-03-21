package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByUserId(long id);
}
