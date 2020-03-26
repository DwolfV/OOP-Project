package nl.tudelft.oopp.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Order;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OrderRepositoryTest {
    private Room r1;

    private Building b1;

    private User u1;

    private RoomReservation rr1;
    private RoomReservation rr2;

    private Order o1;
    private Order o2;
    private Order o3;

    @Autowired
    private RoomRepository roomRep;

    @Autowired
    private BuildingRepository buildRep;

    @Autowired
    private UserRepository userRep;

    @Autowired
    private RoomReservationRepository roomResRep;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Creates all rooms, buildings, users and room reservations
     * and is done before each test.
     */
    @BeforeEach
    public void save() {
        b1 = new Building("b1", LocalTime.parse("08:00"), LocalTime.parse("20:00"), "s1", "sNo1", "z1", "c1");
        r1 = new Room("room1", 1, b1);
        buildRep.save(b1);
        roomRep.save(r1);

        u1 = new User("email1", "student", "fn1", "ln1", "user");
        userRep.save(u1);


        rr1 = new RoomReservation(LocalDate.parse("2000-01-01"), r1, LocalTime.parse("10:00"), LocalTime.parse("10:30"), u1);
        rr2 = new RoomReservation(LocalDate.parse("2010-01-01"), r1, LocalTime.parse("11:00"), LocalTime.parse("12:30"), u1);
        roomResRep.save(rr1);
        roomResRep.save(rr2);

        o1 = new Order(rr1);
        o2 = new Order(rr1);
        o3 = new Order(rr2);
        orderRepository.save(o1);
        orderRepository.save(o2);
        orderRepository.save(o3);
    }


    @Test
    public void testLoadRepository() {
        assertThat(orderRepository).isNotNull();
    }

    @Test
    public void testFindOrdersByRoomReservationId() {
        List<Order> orderList = new ArrayList<Order>(List.of(o1, o2));
        assertEquals(orderList, orderRepository.findOrdersByRoomReservationId(rr1.getId()));
    }
}
