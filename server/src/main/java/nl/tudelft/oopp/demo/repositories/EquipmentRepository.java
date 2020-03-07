package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    Optional<Equipment> findById(long id);

    List<Equipment> findByName(String equipmentName);

    List<Equipment> findByRoomId(long roomId);

}
