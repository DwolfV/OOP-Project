package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.DishOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishOrderRepository extends JpaRepository<DishOrder, Long> {
    public List<DishOrder> findAllByOrderId(long id);
}
