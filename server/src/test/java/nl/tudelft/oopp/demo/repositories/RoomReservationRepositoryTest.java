package nl.tudelft.oopp.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RoomReservationRepositoryTest {

    private Room r1;
    private Room r2;

    private Building b1;
    private Building b2;

    private User u1;
    private User u2;

    private RoomReservation rr1;
    private RoomReservation rr2;
    private RoomReservation rr3;
    private RoomReservation rr4;
    private RoomReservation rr5;

    @Autowired
    private RoomRepository roomRep;

    @Autowired
    private BuildingRepository buildRep;

    @Autowired
    private UserRepository userRep;

    @Autowired
    private RoomReservationRepository roomResRep;

    /**
     * Creates all rooms, buildings, users and room reservations
     * and is done before each test.
     */
    @BeforeEach
    public void save() {
        b1 = new Building("b1", "s1", "sNo1", "z1", "c1");
        r1 = new Room("room1", 1, b1);
        b2 = new Building("b2", "s2", "sNo2", "z2", "c2");
        r2 = new Room("room2", 2, b2);
        buildRep.save(b1);
        buildRep.save(b2);
        roomRep.save(r1);
        roomRep.save(r2);

        u1 = new User("email1", "student", "fn1", "ln1", new Date(1000), "user");
        u2 = new User("email2", "employee", "fn2", "ln2", new Date(2000), "user2");
        userRep.save(u1);
        userRep.save(u2);

        rr1 = new RoomReservation(new Date(1000), r1, new Time(1000), new Time(1500), u1);
        rr2 = new RoomReservation(new Date(2000), r1, new Time(2000), new Time(2500), u1);
        rr3 = new RoomReservation(new Date(3000), r1, new Time(3000), new Time(3500), u2);
        rr4 = new RoomReservation(new Date(4000), r2, new Time(4000), new Time(4500), u2);
        rr5 = new RoomReservation(new Date(5000), r2, new Time(5000), new Time(5500), u2);
        roomResRep.save(rr1);
        roomResRep.save(rr2);
        roomResRep.save(rr3);
        roomResRep.save(rr4);
        roomResRep.save(rr5);
    }

    /**
     * Test if the repositories loads correctly and isn't null.
     *
     * @throws Exception exception
     */
    @Test
    public void testLoadRepository() {
        assertThat(buildRep).isNotNull();
        assertThat(roomRep).isNotNull();
        assertThat(userRep).isNotNull();
        assertThat(roomResRep).isNotNull();
    }

    /**
     * Checks whether findAll() gets a list of all the RoomReservations.
     */
    @Test
    public void testFindAll() {
        List<RoomReservation> fullList = new ArrayList<>(List.of(rr1, rr2, rr3, rr4, rr5));
        assertEquals(fullList, roomResRep.findAll());
    }

    /**
     * Checks whether findByUserId() gets a list of all the RoomReservations from that id.
     */
    @Test
    public void testFindByUserId() {
        List<RoomReservation> u1List = new ArrayList<>(List.of(rr1, rr2));
        assertEquals(u1List, roomResRep.findByUserId(rr1.getUser().getId()));

        List<RoomReservation> u2List = new ArrayList<>(List.of(rr3, rr4, rr5));
        assertEquals(u2List, roomResRep.findByUserId(rr3.getUser().getId()));
    }

    /**
     * Checks whether findByUserIdAndRoomId()
     * gets a list of all the RoomReservations from those id's.
     */
    @Test
    public void testFindByUserIdAndRoomId() {
        List<RoomReservation> list1 = new ArrayList<>(List.of(rr1, rr2));
        assertEquals(list1, roomResRep.findByUserIdAndRoomId(
            list1.get(0).getUser().getId(), list1.get(0).getRoom().getId()));

        List<RoomReservation> list2 = new ArrayList<>(List.of(rr3));
        assertEquals(list2, roomResRep.findByUserIdAndRoomId(
            list2.get(0).getUser().getId(), list2.get(0).getRoom().getId()));

        List<RoomReservation> list3 = new ArrayList<>(List.of(rr4, rr5));
        assertEquals(list3, roomResRep.findByUserIdAndRoomId(
            list3.get(0).getUser().getId(), list3.get(0).getRoom().getId()));
    }

}
