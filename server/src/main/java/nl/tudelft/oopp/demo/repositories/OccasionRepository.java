package nl.tudelft.oopp.demo.repositories;

import java.time.LocalDate;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Occasion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccasionRepository extends JpaRepository<Occasion, Long> {
    List<Occasion> findByBuildingId(long id);

    List<Occasion> findByBuildingIdAndDate(long id, LocalDate date);
}
