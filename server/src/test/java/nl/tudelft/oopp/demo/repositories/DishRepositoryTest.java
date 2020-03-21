package nl.tudelft.oopp.demo.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
public class DishRepositoryTest {
    private Dish d1;
    private Dish d2;
    private Dish d3;
    private Dish d4;

    @Autowired
    private DishRepository dishRepository;

    /**
     * Creates all dishes before every test.
     *  Saves 5 unique buildings lists into the repository so that we can use
     *  it for testing in the @Test methods.
     */


    @BeforeEach
    public void save() {
        d1 = new Dish("french fries", "potatoes", "vegan", 3);
        d2 = new Dish("soup", "chicken, vegetables", "non-vegetarian", 4);
        d3 = new Dish("steak", "beef", "non-vegetarian", 7);
        d4 = new Dish("pasta", "pasta, vegetables, cheese", "vegetarian", 6);

        dishRepository.save(d1);
        dishRepository.save(d2);
        dishRepository.save(d3);
        dishRepository.save(d4);
    }


    /**
     * Test if the repositories loads correctly and isn't null.
     *
     * @throws Exception exception
     */

    @Test
    public void testLoadRepository() {
        assertThat(dishRepository).isNotNull();
    }

    /**
     * checks whether findAll() gets a list of all dishes.
     */

    @Test
    public void testFindAll() {
        List<Dish> fullList = new ArrayList<>(List.of(d1,d2,d3,d4));
        assertEquals(fullList, dishRepository.findAll());
    }

    @Test
    void findById() {
        assertEquals(Optional.of(d1), dishRepository.findById(d1.getId()));
    }
//  @Test
//    public void testFindByName() {
//        assertEquals(Optional.of(i1), itemRepository.findByName("projector"));
//    }
//}

    @Test
    void findByName() {
        assertEquals(List.of(d2), dishRepository.findByName(d2.getName()));
    }
}