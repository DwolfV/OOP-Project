package nl.tudelft.oopp.demo.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Dish;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.RestaurantDish;
import nl.tudelft.oopp.demo.repositories.RestaurantDishRepository;
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

@DataJpaTest
class RestaurantDishControllerTest {

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

    @Mock
    private RestaurantDishRepository restaurantDishRepository;

    @InjectMocks
    private RestaurantDishController restaurantDishController;

    /**
     * Creates all restaurants, dishes and restaurant dishes
     * and is done before each test.
     */

    @BeforeEach
    public void save() {
        Building b1 = new Building("b1", "s1", "sNo1", "z1", "c1");
        r1 = new Restaurant("res1", b1, LocalTime.parse("13:00"), LocalTime.parse("14:00"));    //building 1
        r2 = new Restaurant("res2", b1, LocalTime.parse("14:00"), LocalTime.parse("15:00"));    //building 1
        r3 = new Restaurant("res3", b1, LocalTime.parse("15:30"), LocalTime.parse("17:00"));    //building 1

        Building b2 = new Building("b2", "s2", "sNo2", "z2", "c2");
        r4 = new Restaurant("res4", b2, LocalTime.parse("16:00"), LocalTime.parse("20:00"));    //building 2
        r5 = new Restaurant("res5", b2, LocalTime.parse("17:00"), LocalTime.parse("21:00"));    //building 2


        d1 = new Dish("french fries", "potatoes", "vegan", 3);
        d2 = new Dish("soup", "chicken, vegetables", "non-vegetarian", 4);

        rd1 = new RestaurantDish(r1, d1);
        rd2 = new RestaurantDish(r1, d2);
        rd3 = new RestaurantDish(r2, d3);
    }

    /**
     * Test if the controller loads correctly and isn't null.
     *
     * @throws Exception exception
     */

    @Test
    public void controllerLoads() throws Exception {
        assertThat(restaurantDishController).isNotNull();
    }


    @Test
    void getAllRestaurantDishes() {
        List<RestaurantDish> expected = new ArrayList<RestaurantDish>(List.of(rd1, rd2, rd3));

        when(restaurantDishRepository.findAll()).thenReturn(expected);

        List<RestaurantDish> actual = restaurantDishController.getAllRestaurantDishes();

        assertEquals(expected, actual);
    }

    @Test
    void testGetRestaurantDishById() {
        Optional<RestaurantDish> optionalRestaurantDish = Optional.of(rd1);
        ResponseEntity<RestaurantDish> entity = ResponseEntity.of(optionalRestaurantDish);

        when(restaurantDishRepository.findById(rd1.getId())).thenReturn(optionalRestaurantDish);
        assertEquals(entity, restaurantDishController.getRestaurantDishById(rd1.getId()));
    }

    @Test
    void getRestaurantDishByRestaurantId() {
        List<RestaurantDish> expected = new ArrayList<RestaurantDish>(List.of(rd1, rd2));
        when(restaurantDishRepository.findByRestaurantId(r1.getId())).thenReturn(expected);

        List<RestaurantDish> actual = restaurantDishController.getRestaurantDishByRestaurantId(r1.getId()).getBody();

        assertEquals(expected, actual);
    }

    @Test
    void newRestaurantDish() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        Building b2 = new Building("b2", "s2", "sNo2", "z2", "c2");
        r4 = new Restaurant("res4", b2, LocalTime.parse("16:00"), LocalTime.parse("20:00"));    //building 2
        d2 = new Dish("soup", "chicken, vegetables", "non-vegetarian", 4);
        RestaurantDish restaurantDish = new RestaurantDish(r4, d2);
        Optional<RestaurantDish> optionalRestaurantDish = Optional.of(restaurantDish);
        ResponseEntity<RestaurantDish> responseEntity = ResponseEntity.of(optionalRestaurantDish);

        when(restaurantDishRepository.save(restaurantDish)).thenReturn(restaurantDish);

        assertEquals(restaurantDish, restaurantDishController.newRestaurantDish(restaurantDish, uriComponentsBuilder).getBody());

    }

    @Test
    void updateRestaurantDish() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        Building b1 = new Building("b1", "s1", "sNo1", "z1", "c1");
        Restaurant r1 = new Restaurant("res1", b1, LocalTime.parse("13:00"), LocalTime.parse("14:00"));    //building 1
        Dish d1 = new Dish("french fries", "potatoes", "vegan", 3);
        RestaurantDish restaurantDish = new RestaurantDish(r1, d1);
        Optional<RestaurantDish> optionalRestaurantDish = Optional.of(rd1);

        ResponseEntity<RestaurantDish> responseEntity = ResponseEntity.of(optionalRestaurantDish);
        Optional<RestaurantDish> newR = Optional.of(restaurantDish);
        ResponseEntity<RestaurantDish> restaurantDishResponseEntity = ResponseEntity.of(newR);

        when(restaurantDishRepository.save(restaurantDish)).thenReturn(restaurantDish);
        when(restaurantDishRepository.findById(rd1.getId())).thenReturn(optionalRestaurantDish);

        assertEquals(restaurantDishResponseEntity.getBody(), restaurantDishController.updateRestaurantDish(restaurantDish, rd1.getId(), uriComponentsBuilder).getBody());
    }

    @Test
    void deleteRestaurantDish() {
        List<RestaurantDish> actual = new ArrayList<RestaurantDish>(List.of(rd1, rd3));
        List<RestaurantDish> expected = new ArrayList<RestaurantDish>(List.of(rd1, rd2, rd3));

        Optional<RestaurantDish> optionalRestaurantDish = Optional.of(rd2);
        ResponseEntity<RestaurantDish> responseEntity = ResponseEntity.of(optionalRestaurantDish);

        restaurantDishController.deleteRestaurantDish(rd2.getId());

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actual.remove(1);
                return null;
            }
        }).when(restaurantDishRepository).deleteById(rd2.getId());

        when(restaurantDishRepository.findAll()).thenReturn(expected);
        assertEquals(expected, restaurantDishController.getAllRestaurantDishes());
    }
}