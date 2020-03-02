package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BuildingRepositoryTest {

    List<Building> b1;
    List<Building> b2;
    List<Building> b3;
    List<Building> b4;
    List<Building> b5;

    @Autowired
    private BuildingRepository buildRep;

    /**
     * Saves 5 unique buildings lists into the repository so that we can use
     * it for testing in the @Test methods.
     */
    @BeforeEach
    public void save() {
        b1 = new ArrayList<Building>();
        b2 = new ArrayList<Building>();
        b3 = new ArrayList<Building>();
        b4 = new ArrayList<Building>();
        b5 = new ArrayList<Building>();
        b1.add(new Building(1, "name1", "s1", "sNo1", "z1", "c1"));
        b2.add(new Building(2, "name2", "s2", "sNo2", "z2", "c2"));
        b3.add(new Building(3, "name3", "s3", "sNo3", "z3", "c3"));
        b4.add(new Building(4, "name4", "s4", "sNo4", "z4", "c4"));
        b5.add(new Building(5, "name5", "s5", "sNo5", "z5", "c5"));
        buildRep.save(b1.get(0));
        buildRep.save(b2.get(0));
        buildRep.save(b3.get(0));
        buildRep.save(b4.get(0));
        buildRep.save(b5.get(0));
    }

    @Test
    public void getByNameTest() {
        assertEquals(b1, buildRep.findByName("name1"));
        assertEquals(b2, buildRep.findByName("name2"));
        assertEquals(b3, buildRep.findByName("name3"));
        assertEquals(b4, buildRep.findByName("name4"));
        assertEquals(b5, buildRep.findByName("name5"));
    }

    @Test
    public void findAllTest() {
        List<Building> fullList = new ArrayList<Building>();
        fullList.addAll(b1);
        fullList.addAll(b2);
        fullList.addAll(b3);
        fullList.addAll(b4);
        fullList.addAll(b5);
        assertEquals(fullList, buildRep.findAll());
    }
}
