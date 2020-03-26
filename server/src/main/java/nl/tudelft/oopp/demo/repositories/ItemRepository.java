package nl.tudelft.oopp.demo.repositories;

import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByName(String name);
}
