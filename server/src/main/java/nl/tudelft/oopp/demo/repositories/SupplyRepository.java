package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
    List<Supply> findByUserId(long userId);
    Optional<Supply> findByBuildingIdAndName(long buildingId, String name);

    List<Supply> findByBuildingId(long buildingId);
}
