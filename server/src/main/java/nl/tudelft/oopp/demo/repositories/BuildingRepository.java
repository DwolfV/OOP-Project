package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    Optional<Building> findById(long id);

    List<Building> findByName(String name);

    @Query(" select distinct b from Building b inner join b.rooms r inner join r.equipment e "
            + " WHERE (?1 is null OR r.capacity >= ?1) "
            + " order by r.capacity asc")
    List<Building> filterBuilding(Integer capacity);

}