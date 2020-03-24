package nl.tudelft.oopp.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.RestaurantDish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RestaurantDishRepositoryTest {

    private Restaurant r1;
    private Restaurant r2;
    private Restaurant r3;
    private Restaurant r4;
    private Restaurant r5;

    private Dish d1;
    private Dish d2;
    private Dish d3;
    private Dish d4;

    private RestaurantDish rd1;
    private RestaurantDish rd2;
    private RestaurantDish rd3;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private RestaurantRepository resRep;

    @Autowired
    private BuildingRepository buildRep;

    @Autowired
    private RestaurantDishRepository restaurantDishRepository;

    /**
     * Saves 5 unique restaurantDish lists into the repository so that we can use
     * them for testing in the @Test methods.
     */
    @BeforeEach
    public void save() {

        Building b1 = new Building("b1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s1", "sNo1", "z1", "c1");
        r1 = new Restaurant("res1", b1, LocalTime.parse("13:00"), LocalTime.parse("14:00"));    //building 1
        r2 = new Restaurant("res2", b1, LocalTime.parse("14:00"), LocalTime.parse("18:00"));    //building 1
        r3 = new Restaurant("res3", b1, LocalTime.parse("15:00"), LocalTime.parse("19:00"));    //building 1

        Building b2 = new Building("b2", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s2", "sNo2", "z2", "c2");
        r4 = new Restaurant("res4", b2, LocalTime.parse("16:00"), LocalTime.parse("20:00"));    //building 2
        r5 = new Restaurant("res5", b2, LocalTime.parse("17:00"), LocalTime.parse("21:00"));    //building 2

        buildRep.save(b1);
        buildRep.save(b2);

        resRep.save(r1);
        resRep.save(r2);
        resRep.save(r3);
        resRep.save(r4);
        resRep.save(r5);

        d1 = new Dish("french fries", "potatoes", "vegan", 3);
        d2 = new Dish("soup", "chicken, vegetables", "non-vegetarian", 4);
        d3 = new Dish("steak", "beef", "non-vegetarian", 7);
        d4 = new Dish("pasta", "pasta, vegetables, cheese", "vegetarian", 6);

        dishRepository.save(d1);
        dishRepository.save(d2);
        dishRepository.save(d3);
        dishRepository.save(d4);

        rd1 = new RestaurantDish(r1, d1);
        rd2 = new RestaurantDish(r1, d2);
        rd3 = new RestaurantDish(r2, d3);

        restaurantDishRepository.save(rd1);
        restaurantDishRepository.save(rd2);
        restaurantDishRepository.save(rd3);
    }

    /**
     * Test if the repositories loads correctly and isn't null.
     *
     * @throws Exception exception
     */

    @Test
    public void testLoadRep() {
        assertThat(buildRep).isNotNull();
        assertThat(resRep).isNotNull();
        assertThat(dishRepository).isNotNull();
        assertThat(restaurantDishRepository).isNotNull();
    }

    /**
     * Test if we can find all the restaurantDish by restaurant by using the restaurant id as input.
     */

    @Test
    void findByRestaurantId() {
        List<RestaurantDish> list = new ArrayList<RestaurantDish>();
        list.add(rd1);
        list.add(rd2);

        assertEquals(list, restaurantDishRepository.findByRestaurantId(r1.getId()));
    }

    /**
     * Test if we can find all the restaurantDish by restaurant id and dish id.
     */

    @Test
    void findByRestaurantIdAndDishId() {
        List<RestaurantDish> list = new ArrayList<RestaurantDish>();
        list.add(rd2);

        assertEquals(list, restaurantDishRepository.findByRestaurantIdAndDishId(r1.getId(), d2.getId()));
    }
}