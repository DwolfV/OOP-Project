package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.RestaurantDish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantDishRepository  extends JpaRepository<RestaurantDish, Long> {
    List<RestaurantDish> findByRestaurantId(long restaurantId);
    List<RestaurantDish> findByRestaurantIdAndDishId(long restaurantId, long dishId);
}
