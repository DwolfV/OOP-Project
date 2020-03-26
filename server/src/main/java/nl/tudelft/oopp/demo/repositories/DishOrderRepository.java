package nl.tudelft.oopp.demo.repositories;

import java.util.List;

import nl.tudelft.oopp.demo.entities.DishOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DishOrderRepository extends JpaRepository<DishOrder, Long> {
    List<DishOrder> findAllByOrderId(long id);
}
