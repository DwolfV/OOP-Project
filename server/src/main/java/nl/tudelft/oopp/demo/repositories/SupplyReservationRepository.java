package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.SupplyReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyReservationRepository extends JpaRepository<SupplyReservation, Long> {
    List<SupplyReservation> findByUserId(long userId);

    List<SupplyReservation> findByUserIdAndSupplyId(long userId, long supplyId);
}
