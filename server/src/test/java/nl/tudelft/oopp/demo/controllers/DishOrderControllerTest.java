package nl.tudelft.oopp.demo.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.DishOrder;
import nl.tudelft.oopp.demo.entities.Order;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.RestaurantDish;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.DishOrderRepository;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import nl.tudelft.oopp.demo.repositories.OrderRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantDishRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.RoomReservationRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DishOrderControllerTest {

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

    @InjectMocks
    private DishOrderController dishOrderController;

    /**
     * Creates all rooms, buildings, users, room reservations, orders and dish orders
     * and is done before each test.
     */


    @BeforeEach
    public void save() {
        b1 = new Building("b1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s1", "sNo1", "z1", "c1");
        r1 = new Room("room1", 1, b1);

        u1 = new User("email1", "student", "fn1", "ln1", "user");


        rr1 = new RoomReservation(LocalDate.parse("2018-01-01"), r1, LocalTime.parse("10:00"), LocalTime.parse("15:00"), u1);
        rr2 = new RoomReservation(LocalDate.parse("2018-01-02"), r1, LocalTime.parse("20:00"), LocalTime.parse("20:25"), u1);


        o1 = new Order(rr1);
        o2 = new Order(rr1);

        d1 = new Dish("french fries", "potatoes", "vegan", 3);
        d2 = new Dish("soup", "chicken, vegetables", "non-vegetarian", 4);


        res1 = new Restaurant("res1", b1, LocalTime.parse("13:00"), LocalTime.parse("14:00"));


        rd1 = new RestaurantDish(res1, d1);
        rd2 = new RestaurantDish(res1, d2);


        do1 = new DishOrder(1, o1, rd1);
        do2 = new DishOrder(2, o2, rd2);


    }


    /**
     * Test if the controller loads correctly and isn't null.
     *
     * @throws Exception exception
     */


    @Test
    public void controllerLoads() throws Exception {
        assertThat(dishOrderController).isNotNull();
    }


    /**
     * Checks whether getAllDishOrders returns the whole list.
     */
    //
    //    @Test
    //    void getAllDishOrders() {
    //        List<DishOrder> expected = new ArrayList<>(List.of(do1, do2));
    //
    ////         Specify what the repository should return when you call the
    ////                // findAll() method which is done in the DishOrderController for the method we are testing
    //        when(dishOrderRepository.findAll()).thenReturn(expected);
    //
    //        // now call that method in the DishOrderController and put it into the actual list
    //        List<DishOrder> actual = dishOrderController.getAllDishOrders();
    //
    //        assertEquals(expected, actual);
    //    }
    @Test
    void getAllDishOrdersByOrder() {
    }

    @Test
    void getDishOrderById() {
    }

    @Test
    void addDishOrder() {
    }

    @Test
    void updateDishOrder() {
    }

    @Test
    void deleteDishOrder() {
    }
}