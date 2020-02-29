package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    List<Holiday>  findByName(String name);
}

