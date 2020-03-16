package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.OpenTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenTimeRepository extends JpaRepository<OpenTime, Long> {
    List<OpenTime> findByDay(String day);

    List<OpenTime> findByBuildingId(long buildingId);
}

