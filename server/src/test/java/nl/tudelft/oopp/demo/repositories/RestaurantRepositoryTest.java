package nl.tudelft.oopp.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RestaurantRepositoryTest {

    private Restaurant r1;
    private Restaurant r2;
    private Restaurant r3;
    private Restaurant r4;
    private Restaurant r5;

    @Autowired
    private RestaurantRepository resRep;

    @Autowired
    private BuildingRepository buildRep;

    /**
     * Saves 5 unique restaurant lists into the repository so that we can use
     * it for testing in the @Test methods.
     */
    @BeforeEach
    public void save() {

        Building b1 = new Building("b1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s1", "sNo1", "z1", "c1");
        r1 = new Restaurant("res1", b1, LocalTime.parse("13:00"), LocalTime.parse("14:00"));    //building 1
        r2 = new Restaurant("res2", b1, LocalTime.parse("14:00"), LocalTime.parse("18:00"));    //building 1
        r3 = new Restaurant("res3", b1, LocalTime.parse("15:00"), LocalTime.parse("19:00"));    //building 1
        Building b2 = new Building("b2", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s2", "sNo2", "z2", "c2");
        r4 = new Restaurant("res4", b2, LocalTime.parse("16:00"), LocalTime.parse("20:00"));    //building 2
        r5 = new Restaurant("res5", b2, LocalTime.parse("17:00"), LocalTime.parse("19:00"));    //building 2

        buildRep.save(b1);
        buildRep.save(b2);
        resRep.save(r1);
        resRep.save(r2);
        resRep.save(r3);
        resRep.save(r4);
        resRep.save(r5);
    }

    /**
     * Test if the repositories loads correctly and isn't null.
     *
     * @throws Exception exception
     */
    @Test
    public void testLoadRepository() {
        assertThat(buildRep).isNotNull();
        assertThat(resRep).isNotNull();
    }

    /**
     * Test if we can find all the restaurants by building by using the building name as input.
     */
    @Test
    public void testListByBuilding() {
        List<Restaurant> rl1 = new ArrayList<Restaurant>();
        rl1.add(r1);
        rl1.add(r2);
        rl1.add(r3);
        List<Restaurant> rl2 = new ArrayList<Restaurant>();
        rl2.add(r4);
        rl2.add(r5);

        assertEquals(rl1, resRep.findAllByBuildingName("b1"));
        assertEquals(rl2, resRep.findAllByBuildingName("b2"));
    }

    /**
     * Test if we can find (a) restaurant(s) by the restaurants name.
     */
    @Test
    public void testFindByName() {
        assertEquals(List.of(r1), resRep.findByName("res1"));
        assertEquals(List.of(r2), resRep.findByName("res2"));
        assertEquals(List.of(r3), resRep.findByName("res3"));
        assertEquals(List.of(r4), resRep.findByName("res4"));
        assertEquals(List.of(r5), resRep.findByName("res5"));
    }

    /**
     * Test if we can find the restaurant by restaurant id.
     */
    @Test
    public void testFindById() {
        assertEquals(Optional.of(r1), resRep.findById(r1.getId()));
        assertEquals(Optional.of(r2), resRep.findById(r2.getId()));
        assertEquals(Optional.of(r3), resRep.findById(r3.getId()));
        assertEquals(Optional.of(r4), resRep.findById(r4.getId()));
        assertEquals(Optional.of(r5), resRep.findById(r5.getId()));
    }

    /**
     * Test if we can find all of the restaurants, even from different buildings.
     */
    @Test
    public void testFindAll() {
        List<Restaurant> fullList = new ArrayList<Restaurant>();
        fullList.add(r1);
        fullList.add(r2);
        fullList.add(r3);
        fullList.add(r4);
        fullList.add(r5);
        assertEquals(fullList, resRep.findAll());
    }
}
