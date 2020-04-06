package nl.tudelft.oopp.demo.communication;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Order;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockserver.integration.ClientAndServer;


class OrderCommunicationTest {

    private RoomReservation rr1;
    private RoomReservation rr2;

    private User u1;
    private User u2;

    private Room r1;
    private Room r2;

    private ClientAndServer mockServer;
    private ObjectMapper mapper;

    private Order order;
    private Order order2;
    private Order order3;

    private List<Order> orders;

    @BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(8080);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        u1 = new User("user1@email.com", "student", "fn1", "ln1");
        u2 = new User("user2@email.com", "student", "fn2", "ln2");

        Building b1 = new Building("b1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s1", "sNo1", "z1", "c1");
        r1 = new Room("r1", 11, b1);

        Building b2 = new Building("b2", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s2", "sNo2", "z2", "c1");
        r2 = new Room("r2", 21, b2);

        rr1 = new RoomReservation(LocalDate.parse("2020-01-01"), LocalTime.parse("13:00"), LocalTime.parse("14:00"), u1, r1);
        rr2 = new RoomReservation(LocalDate.parse("2020-01-02"), LocalTime.parse("15:00"), LocalTime.parse("16:00"), u2, r2);

        order = new Order(rr1);
        order2 = new Order(rr1);
        order3 = new Order(rr2);

        orders = new ArrayList<>(List.of(order, order2, order3));

    }

    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }




}