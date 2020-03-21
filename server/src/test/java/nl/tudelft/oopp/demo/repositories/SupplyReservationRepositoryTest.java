package nl.tudelft.oopp.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Supply;
import nl.tudelft.oopp.demo.entities.SupplyReservation;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SupplyReservationRepositoryTest {

    private Supply s1;
    private Supply s2;
    private Supply s3;

    private Building b1;
    private Building b2;
    private Building b3;

    private User u1;
    private User u2;
    private User u3;

    private SupplyReservation sr1;
    private SupplyReservation sr2;
    private SupplyReservation sr3;

    @Autowired
    private SupplyReservationRepository supplyReservationRepository;

    @Autowired
    private SupplyRepository supplyRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates all supplyReservations and is done before each test.
     */
    @BeforeEach
    public void save() {
        b1 = new Building("build1", "s1", "sNo1", "z1", "c1");
        b2 = new Building("build2", "s2", "sNo2", "z2", "c2");
        b3 = new Building("build3", "s3", "sNo3", "z3", "c2");
        buildingRepository.save(b1);
        buildingRepository.save(b2);
        buildingRepository.save(b3);

        s1 = new Supply(b1, "supply1", 1);
        s2 = new Supply(b1, "supply2", 2);
        s3 = new Supply(b1, "supply3", 3);
        supplyRepository.save(s1);
        supplyRepository.save(s2);
        supplyRepository.save(s3);

        u1 = new User("email1", "student", "fn1", "ln1", new Date(1000), "user1");
        u2 = new User("email2", "employee", "fn2", "ln2", new Date(2000), "user2");
        u3 = new User("email1", "student", "fn1", "ln1", new Date(1000), "user3");

        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);

        sr1 = new SupplyReservation(new Date(1), new Time(1), new Time(2), 11, s1, u1);
        sr2 = new SupplyReservation(new Date(2), new Time(1), new Time(2), 22, s1, u1);
        sr3 = new SupplyReservation(new Date(1), new Time(2), new Time(3), 33, s3, u1);

        supplyReservationRepository.save(sr1);
        supplyReservationRepository.save(sr2);
        supplyReservationRepository.save(sr3);
    }

    /**
     * Test if the repositories loads correctly and isn't null.
     *
     * @throws Exception exception
     */

    @Test
    public void testLoadRepository() {
        assertThat(supplyRepository).isNotNull();
        assertThat(supplyReservationRepository).isNotNull();
        assertThat(userRepository).isNotNull();
        assertThat(buildingRepository).isNotNull();
    }

    /**
     * Checks whether findAll() gets a list of all the SupplyReservations.
     */


    @Test
    public void testFindAll() {
        List<SupplyReservation> list = new ArrayList<>(List.of(sr1, sr2, sr3));
        assertEquals(list, supplyReservationRepository.findAll());
    }

    /**
     * Checks whether findByUserId() gets a list of all the SupplyReservations from that id.
     */

    @Test
    void findByUserId() {
        List<SupplyReservation> list1 = new ArrayList<>(List.of(sr1, sr2, sr3));
        assertEquals(list1, supplyReservationRepository.findByUserId(sr1.getUser().getId()));
    }

    /**
     * Checks whether findByUserIdAndRoomId()
     * gets a list of all the RoomReservations from those id's.
     */

    @Test
    void findByUserIdAndSupplyId() {
        List<SupplyReservation> list1 = new ArrayList<>(List.of(sr1, sr2));
        assertEquals(list1, supplyReservationRepository.findByUserIdAndSupplyId(list1.get(0).getUser().getId(), list1.get(0).getSupply().getId()));
    }

}