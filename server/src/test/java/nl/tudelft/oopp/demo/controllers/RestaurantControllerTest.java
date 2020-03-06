package nl.tudelft.oopp.demo.controllers;

import java.sql.Time;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
class RestaurantControllerTest {
    private Restaurant r1;
    private Restaurant r2;
    private Restaurant r3;
    private Restaurant r4;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantController restaurantController;

    /**
     * Creates all restaurants before each test.
     */

    @BeforeEach
    public void save() {
        Building b1 = new Building(1, "b1", "s1", "sNo1", "z1", "c1");
        Building b2 = new Building(2, "b2", "s1", "sNo2", "z2", "c1");
        r1 = new Restaurant(1, "r1", b1, new Time(1), new Time(2)) ;
        r2 = new Restaurant(2, "r2", b2, new Time(1), new Time(3));
        r3 = new Restaurant(3, "r3", b1, new Time(4), new Time(2));
    }

    /**
     * test if the controllers were loaded correctly and if they are not null.
     * Otherwise, @throws Exception
     */

    @Test
    public void controller() {
        assertThat(restaurantController).isNotNull();
    }

    @Test
    void getAllRestaurants() {
        List<Restaurant> expectedList = new ArrayList<Restaurant>(List.of(r1,r2,r3));
        when(restaurantRepository.findAll()).thenReturn(expectedList);

        List<Restaurant> actualList = restaurantController.getAllRestaurants();

        assertEquals(expectedList, actualList);
    }

//    @Test
//    void getRestaurantByBuilding() {
//    }

    @Test
    void getRestaurantById() {
        Optional<Restaurant> optionalRestaurant = Optional.of(r1);
        ResponseEntity<Restaurant> entity = ResponseEntity.of(optionalRestaurant);

        when(restaurantRepository.findById(1L)).thenReturn(optionalRestaurant);
        assertEquals(entity, restaurantController.getRestaurantById(1L));
    }
//
//    @Test
//    void getRestaurantByName() {
//    }

    @Test
    void createNewRestaurant() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building(1, "b1", "s1", "sNo1", "z1", "c1");
        Restaurant restaurant = new Restaurant(1, "r1", b1, new Time(1), new Time(2));
        Optional<Restaurant> optionalRestaurant = Optional.of(restaurant);

        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        assertEquals(restaurant, restaurantController.createNewRestaurant(restaurant, uriComponentsBuilder).getBody());
    }

    @Test
    void updateRestaurant() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building(1, "b1", "s1", "sNo1", "z1", "c1");
        Restaurant restaurant = new Restaurant(1, "r1", b1, new Time(1), new Time(2));
        Optional<Restaurant> optionalRestaurant = Optional.of(r1);

        ResponseEntity<Restaurant> entity = ResponseEntity.of(optionalRestaurant);
        Optional<Restaurant> newR = Optional.of(restaurant);
        ResponseEntity<Restaurant> responseEntity = ResponseEntity.of(newR);

        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        when(restaurantRepository.findById(1L)).thenReturn(optionalRestaurant);

        assertEquals(responseEntity, restaurantController.updateRestaurant(1, restaurant));

    }

    @Test
    void deleteRestaurant() {
        List<Restaurant> actualList = new ArrayList<Restaurant>(List.of(r1,r3));
        List<Restaurant> expectedList = new ArrayList<Restaurant>(List.of(r1,r2,r3));

        Optional<Restaurant> optionalRestaurant = Optional.of(r2);
        ResponseEntity<Restaurant> responseEntity = ResponseEntity.of(optionalRestaurant);

        restaurantController.deleteRestaurant(2L);
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(1);
                return null;
            }
        }).when(restaurantRepository).deleteById(2L);

        when(restaurantRepository.findAll()).thenReturn(expectedList);

        assertEquals(expectedList, restaurantController.getAllRestaurants());


    }
}