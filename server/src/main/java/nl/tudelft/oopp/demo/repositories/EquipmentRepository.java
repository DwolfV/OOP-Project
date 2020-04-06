package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    Optional<Equipment> findById(long id);

    List<Equipment> findByItemName(String equipmentName);

    List<Equipment> findByRoomId(long roomId);

    List<Equipment> findByItemId(long itemId);

}
