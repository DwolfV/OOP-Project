package nl.tudelft.oopp.demo.repositories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.tudelft.oopp.demo.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

//@RepositoryRestResource
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByName(String name);

    List<Room> findByBuildingId(long buildingId);

    @Query(" select distinct r from Room r "
            + " WHERE (r.building.id = ?1) "
            + " AND (?2 is null OR r.capacity >= ?2) "
            + " order by r.capacity asc")
    List<Room> filterRoom(Long id, Integer capacity);
}
