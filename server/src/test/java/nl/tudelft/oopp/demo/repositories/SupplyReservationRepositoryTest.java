package nl.tudelft.oopp.demo.repositories;

import java.sql.Date;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Supply;
import nl.tudelft.oopp.demo.entities.SupplyReservation;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SupplyReservationRepositoryTest {

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
    public SupplyReservationRepository supplyReservationRepository;

    @Autowired
    public SupplyRepository supplyRepository;

    @Autowired
    public BuildingRepository buildingRepository;

    @Autowired
    public UserRepository userRepository;

    /**
     * Creates all supplies and is done before each test.
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

        u1 = new User("email1", "student", "fn1", "ln1", new Date(1000));
        u2 = new User("email2", "employee", "fn2", "ln2", new Date(2000));
        u1 = new User("email1", "student", "fn1", "ln1", new Date(1000));

        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);

        sr1 = new SupplyReservation(1, new java.util.Date(1), 11, s1, u1);
        sr2 = new SupplyReservation(2, new java.util.Date(2), 22, s2, u2);
        sr3 = new SupplyReservation(3, new java.util.Date(3), 33, s3, u3);
    }

        //                             Date date,
        //                             Time startTime,
        //                             Time endTime,
        //                             int amount,
        //                             Supply supply,
        //                             User user
    }

    @Test
    void findByUserId() {
    }

    @Test
    void findByUserIdAndSupplyId() {
    }
}