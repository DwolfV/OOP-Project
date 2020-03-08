package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@RepositoryRestResource
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByName(String name);

    List<Room> findByBuildingId(long buildingId);

}
