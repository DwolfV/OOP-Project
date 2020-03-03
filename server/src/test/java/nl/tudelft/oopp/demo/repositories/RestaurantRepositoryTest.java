package nl.tudelft.oopp.demo.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
//import nl.tudelft.oopp.demo.repositories.BuildingRepository;
//import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository resRep;

    @Autowired
    private BuildingRepository buildRep;

    private List<Restaurant> r1;
    private List<Restaurant> r2;
    private List<Restaurant> r3;
    private List<Restaurant> r4;
    private List<Restaurant> r5;

    /**
     * Saves 5 unique restaurant lists into the repository so that we can use
     * it for testing in the @Test methods.
     */
    @BeforeEach
    public void save() {
        r1 = new ArrayList<Restaurant>();
        r2 = new ArrayList<Restaurant>();
        r3 = new ArrayList<Restaurant>();
        r4 = new ArrayList<Restaurant>();
        r5 = new ArrayList<Restaurant>();
        Building b1 = new Building(1, "b1", "s1", "sNo1", "z1", "c1");
        r1.add(new Restaurant(1, "res1", b1, new Time(1), new Time(2)));    //building 1
        r2.add(new Restaurant(2, "res2", b1, new Time(2), new Time(6)));    //building 1
        r3.add(new Restaurant(3, "res3", b1, new Time(3), new Time(7)));    //building 1
        Building b2 = new Building(2, "b2", "s2", "sNo2", "z2", "c2");
        r4.add(new Restaurant(4, "res4", b2, new Time(4), new Time(8)));    //building 2
        r5.add(new Restaurant(5, "res5", b2, new Time(5), new Time(9)));    //building 2
        buildRep.save(b1);
        buildRep.save(b2);
        resRep.save(r1.get(0));
        resRep.save(r2.get(0));
        resRep.save(r3.get(0));
        resRep.save(r4.get(0));
        resRep.save(r5.get(0));
    }

    /**
     * Test if we can find all the restaurants by building by using the building name as input.
     */
    @Test
    public void listByBuildingTest() {
        List<Restaurant> rl1 = new ArrayList<Restaurant>();
        rl1.addAll(r1);
        rl1.addAll(r2);
        rl1.addAll(r3);
        List<Restaurant> rl2 = new ArrayList<Restaurant>();
        rl2.addAll(r4);
        rl2.addAll(r5);

        assertEquals(rl1, resRep.findAllByBuildingName("b1"));
        assertEquals(rl2, resRep.findAllByBuildingName("b2"));
    }

    /**
     * Test if we can find (a) restaurant(s) by the restaurants name.
     */
    @Test
    public void findByNameTest() {
        assertEquals(r1, resRep.findByName("res1"));
        assertEquals(r2, resRep.findByName("res2"));
        assertEquals(r3, resRep.findByName("res3"));
        assertEquals(r4, resRep.findByName("res4"));
        assertEquals(r5, resRep.findByName("res5"));
    }

    /**
     * Test if we can find the restaurant by restaurant id.
     */
    @Test
    public void findByIdTest() {
        assertEquals(Optional.of(r1.get(0)), resRep.findById(1));
        assertEquals(Optional.of(r2.get(0)), resRep.findById(2));
        assertEquals(Optional.of(r3.get(0)), resRep.findById(3));
        assertEquals(Optional.of(r4.get(0)), resRep.findById(4));
        assertEquals(Optional.of(r5.get(0)), resRep.findById(5));
    }

    /**
     * Test if we can find all of the restaurants, even from different buildings.
     */
    @Test
    public void findAllTest() {
        List<Restaurant> fullList = new ArrayList<Restaurant>();
        fullList.addAll(r1);
        fullList.addAll(r2);
        fullList.addAll(r3);
        fullList.addAll(r4);
        fullList.addAll(r5);
        assertEquals(fullList, resRep.findAll());
    }
}
