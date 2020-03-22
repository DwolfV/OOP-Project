package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.RestaurantDish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantDishRepository extends JpaRepository<RestaurantDish, Long> {
    @Override
    Optional<RestaurantDish> findById(Long restaurantDishId);

    List<RestaurantDish> findByRestaurantId(long id);

    List<RestaurantDish> findByRestaurantIdAndDishId(long restaurantId, long dishId);
}
