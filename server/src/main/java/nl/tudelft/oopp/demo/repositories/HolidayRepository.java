package nl.tudelft.oopp.demo.repositories;

import java.util.List;

import nl.tudelft.oopp.demo.entities.Holiday;

import org.springframework.data.jpa.repository.JpaRepository;


public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    List<Holiday> findByName(String name);
}

