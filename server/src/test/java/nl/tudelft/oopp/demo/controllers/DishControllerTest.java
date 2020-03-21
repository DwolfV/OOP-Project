package nl.tudelft.oopp.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.repositories.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
class DishControllerTest {

    private Dish d1;
    private Dish d2;
    private Dish d3;
    private Dish d4;

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishController dishController;


    /**
     * Creates all dishes before each test.
     */

    @BeforeEach
    public void save() {
        d1 = new Dish("french fries", "potatoes", "vegan", 3);
        d2 = new Dish("soup", "chicken, vegetables", "non-vegetarian", 4);
        d3 = new Dish("steak", "beef", "non-vegetarian", 7);
        d4 = new Dish("pasta", "pasta, vegetables, cheese", "vegetarian", 6);
    }

    /**
     * test if the controllers were loaded correctly and if they are not null.
     * Otherwise, @throws Exception
     */

    @Test
    public void controllerLoads() throws Exception {
        assertThat(dishController).isNotNull();
    }

    @Test
    void getAllDishes() {
        List<Dish> expectedList = new ArrayList<>(List.of(d1,d2,d3,d4));
        when(dishRepository.findAll()).thenReturn(expectedList);
        List<Dish> actualList = dishController.getAllDishes();

        assertEquals(expectedList, actualList);
    }

    @Test
    void getDishById() {
        Optional<Dish> optionalDish = Optional.of(d1);
        ResponseEntity<Dish> entity = ResponseEntity.of(optionalDish);

        when(dishRepository.findById(1L)).thenReturn(optionalDish);
        assertEquals(entity, dishController.getDishById(1L));
    }

    @Test
    void getAllDishByName() {
        List<Dish> list = new ArrayList<>(List.of(d2));

        when(dishRepository.findByName("soup")).thenReturn(list);
        assertEquals(list, dishController.getAllDishByName("soup"));
    }

    @Test
    void newDish() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Dish dish = new Dish("french fries", "potatoes", "vegan", 3);
        Optional<Dish> optionalDish = Optional.of(dish);
        ResponseEntity<Dish> responseEntity = ResponseEntity.of(optionalDish);

        when(dishRepository.save(dish)).thenReturn(dish);

        assertEquals(dish, dishController.newDish(dish, uriComponentsBuilder).getBody());
    }

    @Test
    void updateDish() {
        Dish dish = new Dish("french fries", "potatoes", "vegan", 3);
        Optional<Dish> optionalDish = Optional.of(d1);
        ResponseEntity<Dish> entity = ResponseEntity.of(optionalDish);

        Optional<Dish> newD = Optional.of(dish);
        ResponseEntity<Dish> responseEntity = ResponseEntity.of(newD);

        when(dishRepository.save(dish)).thenReturn(dish);
        when(dishRepository.findById(d1.getId())).thenReturn(optionalDish);

        assertEquals(responseEntity.getBody(), dishController.updateDish(dish.getId(), dish).getBody());

    }

    @Test
    void deleteDish() {
        List<Dish> actualList = new ArrayList<Dish>(List.of(d1,d4,d3));
        List<Dish> expected = new ArrayList<>(List.of(d1,d2,d3,d4));

        Optional<Dish> optionalDish = Optional.of(d2);
        ResponseEntity<Dish> dishResponseEntity = ResponseEntity.of(optionalDish);

        dishController.deleteDish(d2.getId());
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(1);
                return null;
            }
        }).when(dishRepository).deleteById(d2.getId());

        when(dishRepository.findAll()).thenReturn(expected);
        assertEquals(expected, dishController.getAllDishes());
    }
}