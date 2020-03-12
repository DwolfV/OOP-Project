package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findOrdersByRoomReservationId(long id);

    public List<Order> findAllByRoomReservation_User_Id(long id);
}
