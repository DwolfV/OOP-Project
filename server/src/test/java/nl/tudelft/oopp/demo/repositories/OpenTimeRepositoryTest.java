package nl.tudelft.oopp.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.OpenTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OpenTimeRepositoryTest {

    private OpenTime ot1;
    private OpenTime ot2;
    private OpenTime ot3;
    private OpenTime ot4;
    private OpenTime ot5;

    private Building b1;
    private Building b2;

    @Autowired
    private OpenTimeRepository openRep;

    @Autowired
    private BuildingRepository buildRep;

    /**
     * Creates all supplies and is done before each test.
     */
    @BeforeEach
    public void save() {
        b1 = new Building("name1", "s1", "sNo1", "z1", "c1");
        b2 = new Building("name2", "s2", "sNo2", "z2", "c2");
        buildRep.save(b1);
        buildRep.save(b2);

        ot1 = new OpenTime("monday", new Time(1000), new Time(1500), b1);
        ot2 = new OpenTime("tuesday", new Time(2000), new Time(2500), b1);
        ot3 = new OpenTime("wednesday", new Time(3000), new Time(3500), b1);
        ot4 = new OpenTime("wednesday", new Time(4000), new Time(4500), b2);
        ot5 = new OpenTime("sunday", new Time(5000), new Time(5500), b2);
        openRep.save(ot1);
        openRep.save(ot2);
        openRep.save(ot3);
        openRep.save(ot4);
        openRep.save(ot5);
    }

    /**
     * Test if the repositories loads correctly and isn't null.
     *
     * @throws Exception exception
     */
    @Test
    public void testLoadRepository() {
        assertThat(buildRep).isNotNull();
        assertThat(openRep).isNotNull();
    }

    /**
     * Checks whether findAll() gets a list of all the openTimes.
     */
    @Test
    public void testFindAll() {
        List<OpenTime> openList = new ArrayList<OpenTime>(List.of(ot1, ot2, ot3, ot4, ot5));
        assertEquals(openList, openRep.findAll());

        List<Building> buildList = new ArrayList<Building>(List.of(b1, b2));
        assertEquals(buildList, buildRep.findAll());
    }

    /**
     * Checks whether findByBuildingId() in the repository
     * finds all the openTimes from a certain building id.
     */
    @Test
    public void testFindByBuildingId() {
        List<OpenTime> b1List = new ArrayList<OpenTime>(List.of(ot1, ot2, ot3));
        assertEquals(b1List, openRep.findByBuildingId(ot1.getBuilding().getId()));

        List<OpenTime> b2List = new ArrayList<OpenTime>(List.of(ot4, ot5));
        assertEquals(b2List, openRep.findByBuildingId(ot4.getBuilding().getId()));

        assertEquals(openRep.findAll().size(),
                openRep.findByBuildingId(ot1.getBuilding().getId()).size()
                + openRep.findByBuildingId(ot4.getBuilding().getId()).size());
    }

    /**
     * Checks whether findByDay() in the repository
     * finds all the openTimes from a certain day.
     */
    @Test
    public void testFindByDay() {
        List<OpenTime> mondayList = new ArrayList<OpenTime>(List.of(ot1));
        assertEquals(mondayList, openRep.findByDay("monday"));

        List<OpenTime> wednesdayList = new ArrayList<OpenTime>(List.of(ot3, ot4));
        assertEquals(wednesdayList, openRep.findByDay("wednesday"));
    }
}
