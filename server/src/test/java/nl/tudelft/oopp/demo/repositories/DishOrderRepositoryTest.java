package nl.tudelft.oopp.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.DishOrder;
import nl.tudelft.oopp.demo.entities.Order;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.RestaurantDish;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//import static org.mockito.Mockito.when;

@DataJpaTest
class DishOrderRepositoryTest {
    private Room r1;

    private Building b1;

    private User u1;

    private Dish d1;
    private Dish d2;

    private RoomReservation rr1;
    private RoomReservation rr2;

    private Order o1;
    private Order o2;

    private DishOrder do1;
    private DishOrder do2;

    private RestaurantDish rd1;
    private RestaurantDish rd2;

    private Restaurant res1;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DishOrderRepository dishOrderRepository;

    @Autowired
    private RestaurantDishRepository restaurantDishRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;

    /**
     * Creates all rooms, buildings, users, room reservations, orders and dish orders
     * and is done before each test.
     */


    @BeforeEach
    public void save() {
        b1 = new Building("b1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s1", "sNo1", "z1", "c1");
        r1 = new Room("room1", 1, b1);
        buildingRepository.save(b1);
        roomRepository.save(r1);

        u1 = new User("email1", "student", "fn1", "ln1", new Date(1000), "user");
        userRepository.save(u1);


        rr1 = new RoomReservation(new Date(1000), r1, new Time(1000), new Time(1500), u1);
        rr2 = new RoomReservation(new Date(2000), r1, new Time(2000), new Time(2500), u1);
        roomReservationRepository.save(rr1);
        roomReservationRepository.save(rr2);

        o1 = new Order(rr1);
        o2 = new Order(rr1);
        orderRepository.save(o1);
        orderRepository.save(o2);

        d1 = new Dish("french fries", "potatoes", "vegan", 3);
        d2 = new Dish("soup", "chicken, vegetables", "non-vegetarian", 4);
        dishRepository.save(d1);
        dishRepository.save(d2);

        res1 = new Restaurant("res1", b1, new Time(1), new Time(2));
        restaurantRepository.save(res1);

        rd1 = new RestaurantDish(res1, d1);
        rd2 = new RestaurantDish(res1, d2);
        restaurantDishRepository.save(rd1);
        restaurantDishRepository.save(rd2);

        do1 = new DishOrder(1, o1, rd1);
        do2 = new DishOrder(2, o2, rd2);
        dishOrderRepository.save(do1);
        dishOrderRepository.save(do2);

    }

    @Test
    public void testLoadRepository() {
        assertThat(dishOrderRepository).isNotNull();
    }


    @Test
    void findAllByOrderId() {
        List<DishOrder> list = new ArrayList<DishOrder>(List.of(do1));
        assertEquals(list, dishOrderRepository.findAllByOrderId(o1.getId()));
    }
}