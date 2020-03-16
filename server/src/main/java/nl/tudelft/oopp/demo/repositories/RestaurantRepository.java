package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    /**
     * Find all the restaurants in a specific building.
     *
     * @param buildingName name of building
     * @return List of restaurants
     */
    List<Restaurant> findAllByBuildingName(String buildingName);

    List<Restaurant> findByName(String name);

    Optional<Restaurant> findById(long id);
}
