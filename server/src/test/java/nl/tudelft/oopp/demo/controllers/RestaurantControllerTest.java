package nl.tudelft.oopp.demo.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
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
class RestaurantControllerTest {
    private Restaurant r1;
    private Restaurant r2;
    private Restaurant r3;
    private Restaurant r4;

    private Building b1;
    private Building b2;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantController restaurantController;

    /**
     * Creates all restaurants before each test.
     */

    @BeforeEach
    public void save() {
        b1 = new Building("b1", "s1", "sNo1", "z1", "c1");
        b2 = new Building("b2", "s1", "sNo2", "z2", "c1");
        r1 = new Restaurant("r1", b1, LocalTime.parse("13:00"), LocalTime.parse("14:00"));
        r2 = new Restaurant("r2", b2, LocalTime.parse("13:00"), LocalTime.parse("15:00"));
        r3 = new Restaurant("r3", b1, LocalTime.parse("16:00"), LocalTime.parse("17:30"));
    }

    /**
     * test if the controllers were loaded correctly and if they are not null.
     * Otherwise, @throws Exception
     */

    @Test
    public void testLoadController() {
        assertThat(restaurantController).isNotNull();
    }

    @Test
    void testGetAllRestaurants() {
        List<Restaurant> expectedList = new ArrayList<Restaurant>(List.of(r1, r2, r3));
        when(restaurantRepository.findAll()).thenReturn(expectedList);

        List<Restaurant> actualList = restaurantController.getAllRestaurants();

        assertEquals(expectedList, actualList);
    }

    @Test
    void testGetRestaurantByBuilding() {
        List<Restaurant> list1 = new ArrayList<>(List.of(r1, r3));
        List<Restaurant> list2 = new ArrayList<>(List.of(r2));

        when(restaurantRepository.findAllByBuildingName(b1.getName())).thenReturn(list1);
        assertEquals(list1, restaurantController.getRestaurantByBuilding(b1.getName()));

        when(restaurantRepository.findAllByBuildingName(b2.getName())).thenReturn(list2);
        assertEquals(list2, restaurantController.getRestaurantByBuilding(b2.getName()));
    }

    @Test
    void testGetRestaurantById() {
        Optional<Restaurant> optionalRestaurant = Optional.of(r1);
        ResponseEntity<Restaurant> entity = ResponseEntity.of(optionalRestaurant);

        when(restaurantRepository.findById(r1.getId())).thenReturn(optionalRestaurant);
        assertEquals(entity, restaurantController.getRestaurantById(r1.getId()));
    }

    @Test
    void testGetRestaurantByName() {
        when(restaurantRepository.findByName("r1")).thenReturn(List.of(r1));
        assertEquals(List.of(r1), restaurantController.getRestaurantByName("r1"));
    }

    @Test
    void testCreateNewRestaurant() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building("b1", "s1", "sNo1", "z1", "c1");
        Restaurant restaurant = new Restaurant("r1", b1, LocalTime.parse("09:00"), LocalTime.parse("12:00"));
        Optional<Restaurant> optionalRestaurant = Optional.of(restaurant);

        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        assertEquals(restaurant, restaurantController.createNewRestaurant(
            restaurant, uriComponentsBuilder).getBody());
    }

    @Test
    void testUpdateRestaurant() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building("b1", "s1", "sNo1", "z1", "c1");
        Restaurant restaurant = new Restaurant("r1", b1, LocalTime.parse("13:00"), LocalTime.parse("14:00"));
        Optional<Restaurant> optionalRestaurant = Optional.of(r1);

        ResponseEntity<Restaurant> entity = ResponseEntity.of(optionalRestaurant);
        Optional<Restaurant> newR = Optional.of(restaurant);
        ResponseEntity<Restaurant> responseEntity = ResponseEntity.of(newR);

        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        when(restaurantRepository.findById(r1.getId())).thenReturn(optionalRestaurant);

        assertEquals(responseEntity, restaurantController.updateRestaurant(r1.getId(), restaurant));

    }

    @Test
    void testDeleteRestaurant() {
        List<Restaurant> actualList = new ArrayList<Restaurant>(List.of(r1, r3));
        List<Restaurant> expectedList = new ArrayList<Restaurant>(List.of(r1, r2, r3));

        Optional<Restaurant> optionalRestaurant = Optional.of(r2);
        ResponseEntity<Restaurant> responseEntity = ResponseEntity.of(optionalRestaurant);

        restaurantController.deleteRestaurant(r2.getId());
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(1);
                return null;
            }
        }).when(restaurantRepository).deleteById(r2.getId());

        when(restaurantRepository.findAll()).thenReturn(expectedList);

        assertEquals(expectedList, restaurantController.getAllRestaurants());


    }
}