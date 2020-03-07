package nl.tudelft.oopp.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Supply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SupplyRepositoryTest {

    private Supply s1;
    private Supply s2;
    private Supply s3;
    private Supply s4;
    private Supply s5;

    private Building b1;
    private Building b2;

    @Autowired
    public SupplyRepository supplyRep;

    @Autowired
    public BuildingRepository buildRep;

    /**
     * Creates all supplies and is done before each test.
     */
    @BeforeEach
    public void save() {
        b1 = new Building("build1", "s1", "sNo1", "z1", "c1");
        b2 = new Building("build2", "s2", "sNo2", "z2", "c2");
        buildRep.save(b1);
        buildRep.save(b2);

        s1 = new Supply(b1, "supply1", 1);
        s2 = new Supply(b1, "supply2", 2);
        s3 = new Supply(b1, "supply3", 3);
        s4 = new Supply(b2, "supply4", 4);
        s5 = new Supply(b2, "supply5", 5);
        supplyRep.save(s1);
        supplyRep.save(s2);
        supplyRep.save(s3);
        supplyRep.save(s4);
        supplyRep.save(s5);
    }

    /**
     * Test if the repositories loads correctly and isn't null.
     *
     * @throws Exception exception
     */
    @Test
    public void testLoadController() throws Exception {
        assertThat(supplyRep).isNotNull();
        assertThat(buildRep).isNotNull();
    }

    /**
     * Checks whether findAll() gets a list of all the supplies.
     */
    @Test
    public void testFindAll() {
        List<Supply> fullList = new ArrayList<Supply>(List.of(s1, s2, s3, s4, s5));
        assertEquals(fullList, supplyRep.findAll());
    }

    /**
     * Checks whether findByBuilding() in the repository
     * finds all the supplies from a certain building.
     */
    @Test
    public void testFindByBuilding() {
        List<Supply> expectedList1 = new ArrayList<Supply>(List.of(s1, s2, s3));
        assertEquals(expectedList1, supplyRep.findByBuildingId(b1.getId()));

        List<Supply> expectedList2 = new ArrayList<Supply>(List.of(s4, s5));
        Optional<List<Supply>> expectedOptional2 = Optional.of(expectedList2);
        assertEquals(expectedList2, supplyRep.findByBuildingId(b2.getId()));
    }

    /**
     * Checks whether findByBuildingAndName() in the repository
     * finds all the supplies from a certain building and supplyName.
     */
    @Test
    public void testFindByBuildingAndName() {

        Optional<Supply> optional1 = Optional.of(s1);
        assertEquals(optional1, supplyRep.findByBuildingIdAndName(b1.getId(), "supply1"));

        Optional<Supply> optional2 = Optional.of(s2);
        assertEquals(optional2, supplyRep.findByBuildingIdAndName(b1.getId(), "supply2"));

        Optional<Supply> optional3 = Optional.of(s3);
        assertEquals(optional3, supplyRep.findByBuildingIdAndName(b1.getId(), "supply3"));

        Optional<Supply> optional4 = Optional.of(s4);
        assertEquals(optional4, supplyRep.findByBuildingIdAndName(b2.getId(), "supply4"));
        assertNotEquals(optional4, supplyRep.findByBuildingIdAndName(b1.getId(), "supply4"));
        assertEquals(Optional.empty(), supplyRep.findByBuildingIdAndName(b1.getId(), "supply4"));

        Optional<Supply> optional5 = Optional.of(s5);
        assertEquals(optional5, supplyRep.findByBuildingIdAndName(b2.getId(), "supply5"));

        Supply s5Extra = new Supply(b2, "supply5", 5);
        Optional<Supply> optional5Extra = Optional.of(s5Extra);
        assertEquals(optional5, supplyRep.findByBuildingIdAndName(b2.getId(), "supply5"));
    }
}
