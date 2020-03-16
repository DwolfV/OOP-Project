package nl.tudelft.oopp.demo.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Order;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.OrderRepository;
import nl.tudelft.oopp.demo.repositories.RoomReservationRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.util.UriComponentsBuilder;

@DataJpaTest
public class OrderControllerTest {

    private RoomReservation rr1;
    private RoomReservation rr2;

    private User u1;
    private User u2;

    private Room r1;
    private Room r2;

    private Order o1;
    private Order o2;
    private Order o3;

    @Mock
    private RoomReservationRepository roomReservationRepository;

    @Mock
    private UserRepository users;

    @Mock
    private JdbcUserDetailsManager jdbcUserDetailsManager;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderController orderController;

    /**
     * Creates all roomReservations before each test.
     */


    @BeforeEach
    public void save() {
        u1 = new User("user1@email.com", "student", "fn1", "ln1", new Date(1000), "user1");
        u2 = new User("user2@email.com", "student", "fn2", "ln2", new Date(2000), "user2");

        Building b1 = new Building("b1", "s1", "sNo1", "z1", "c1");
        r1 = new Room("r1", 11, b1);

        Building b2 = new Building("b2", "s2", "sNo2", "z2", "c1");
        r2 = new Room("r2", 21, b2);

        rr1 = new RoomReservation(new Date(1), r1, new Time(1), new Time(2), u1);
        rr2 = new RoomReservation(new Date(2), r2, new Time(3), new Time(4), u2);

        o1 = new Order(rr1);
        o2 = new Order(rr1);
        o3 = new Order(rr2);
    }

    @Test
    public void testLoadController() throws Exception {
        assertThat(orderController).isNotNull();
    }

    @Test
    public void testGetAll() {
        List<Order> expectedList = new ArrayList<Order>(List.of(o1, o2, o3));
        when(orderRepository.findAll()).thenReturn(expectedList);
        List<Order> actualList = orderController.getAllOrders();

        assertEquals(expectedList, actualList);
    }

    @Test
    public void testGetOrdersByRoomReservationId() {
        List<Order> expectedList = new ArrayList<Order>(List.of(o1, o2));
        when(orderRepository.findOrdersByRoomReservationId(rr1.getId())).thenReturn(expectedList);
        List<Order> actualList = orderController.getOrdersByReservationId(u1.getId());

        assertEquals(expectedList, actualList);
    }

    @Test
    public void testGetOrdersByUserId() {
        Order o4 = new Order(rr2);
        when(orderRepository.findAllByRoomReservation_User_Id(u2.getId())).thenReturn(List.of(o3, o4));
        when(users.findByUsername(u2.getUsername())).thenReturn(Optional.of(u2));

        org.springframework.security.core.Authentication auth = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public String getName() {
                return "user2";
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }


        };

        assertEquals(List.of(o3, o4),
            orderController.getOrdersByUserId(u2.getId(), auth).getBody());
    }

    @Test
    public void testNewOrder() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        User u1 = new User("user1@email.com", "student", "fn1", "ln1", new Date(1000), "user1");
        Building b1 = new Building("b1", "s1", "sNo1", "z1", "c1");
        Room r1 = new Room("r1", 11, b1);
        RoomReservation rr1 = new RoomReservation(
            new Date(1), r1, new Time(1), new Time(2), u1);

        Order o4 = new Order(rr1);
        Optional<Order> optionalOrder = Optional.of(o4);
        ResponseEntity<Order> responseEntity = ResponseEntity.of(optionalOrder);

        when(orderRepository.save(o4)).thenReturn(o4);
        when(users.findByUsername(u1.getUsername())).thenReturn(Optional.of(u1));

        assertEquals(o4, orderController.addOrder(o4, uriComponentsBuilder, new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public String getName() {
                return "user1";
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }


        }).getBody());
    }


    @Test
    public void testUpdateOrder() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        User u1 = new User("user1@email.com", "student", "fn1", "ln1", new Date(1000), "user1");
        Building b1 = new Building("b1", "s1", "sNo1", "z1", "c1");
        Room r1 = new Room("r1", 11, b1);
        RoomReservation roomReservation = new RoomReservation(
            new Date(1), r1, new Time(1), new Time(2), u1);
        Order newOrder = new Order(roomReservation);

        Optional<Order> newOptionalOrder = Optional.of(newOrder);
        ResponseEntity<Order> newRe = ResponseEntity.of(newOptionalOrder);

        Optional<Order> optionalOrder = Optional.of(o1);
        ResponseEntity<Order> responseEntity = ResponseEntity.of(optionalOrder);

        when(orderRepository.save(newOrder)).thenReturn(newOrder);
        when(orderRepository.findById(o1.getId())).thenReturn(optionalOrder);

        assertEquals(newRe.getBody(), orderController.updateOrder(1,
            newOrder, uriComponentsBuilder, new Authentication() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return null;
                }

                @Override
                public Object getCredentials() {
                    return null;
                }

                @Override
                public Object getDetails() {
                    return null;
                }

                @Override
                public Object getPrincipal() {
                    return null;
                }

                @Override
                public boolean isAuthenticated() {
                    return false;
                }

                @Override
                public String getName() {
                    return "user1";
                }

                @Override
                public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

                }


            }).getBody());
    }

    @Test
    void testDeleteOrder() {
        List<Order> actualList = new ArrayList<Order>(
            List.of(o1, o3));
        List<Order> orderList = new ArrayList<Order>(
            List.of(o1, o2, o3));

        Optional<Order> optionalOrder = Optional.of(o2);
        ResponseEntity<Order> responseEntity = ResponseEntity.of(optionalOrder);

        orderController.deleteOrder(o2.getId(), new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public String getName() {
                return "user1";
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }


        });

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(1);
                return null;
            }
        }).when(orderRepository).deleteById(o2.getId());
        when(orderRepository.findAll()).thenReturn(orderList);

        assertEquals(orderList, orderController.getAllOrders());
    }

}
