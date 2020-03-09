package nl.tudelft.oopp.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;
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

    private Building b1;
    private Building b2;
    private Building b3;
    private Building b4;
    private Building b5;

    @Autowired
    private BuildingRepository buildRep;

    /**
     * Saves 5 unique buildings lists into the repository so that we can use
     * it for testing in the @Test methods.
     */
    @BeforeEach
    public void save() {
        b1 = new Building("name1", "s1", "sNo1", "z1", "c1");
        b2 = new Building("name2", "s2", "sNo2", "z2", "c2");
        b3 = new Building("name3", "s3", "sNo3", "z3", "c3");
        b4 = new Building("name4", "s4", "sNo4", "z4", "c4");
        b5 = new Building("name5", "s5", "sNo5", "z5", "c5");
        buildRep.save(b1);
        buildRep.save(b2);
        buildRep.save(b3);
        buildRep.save(b4);
        buildRep.save(b5);
    }

    /**
     * Test if the repositories loads correctly and isn't null.
     *
     * @throws Exception exception
     */
    @Test
    public void testLoadRepository() {
        assertThat(buildRep).isNotNull();
    }

    /**
     * Checks whether findAll() gets a list of all the buildings.
     */
    @Test
    public void findFindAll() {
        List<Building> fullList = new ArrayList<Building>(List.of(b1, b2, b3, b4, b5));
        assertEquals(fullList, buildRep.findAll());
    }

    /**
     * Checks whether findByName() gets a list of all the buildings with that name.
     */
    @Test
    public void testFindByName() {
        assertEquals(List.of(b1), buildRep.findByName("name1"));
        assertEquals(List.of(b2), buildRep.findByName("name2"));
        assertEquals(List.of(b3), buildRep.findByName("name3"));
        assertEquals(List.of(b4), buildRep.findByName("name4"));
        assertEquals(List.of(b5), buildRep.findByName("name5"));
    }
}
