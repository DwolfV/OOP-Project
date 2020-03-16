package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {
    Optional<Dish>  findById(long id);

    List<Dish> findByName(String name);
}
