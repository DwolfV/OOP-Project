package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RestaurantTest {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Test
    public void testFindAllRestaurants() {
        Building b1 = new Building(0, "bName1", "bStreet1", "bNum1", "bZip1", "bCity");
        Building b2 = new Building(1, "bName2", "bStreet2", "bNum2", "bZip2", "bCity");
        Restaurant r1 = new Restaurant(0, "name1", b1, new Time(1000), new Time(2000));
        Restaurant r2 = new Restaurant(1, "name2", b1, new Time(2000), new Time(3000));
        Restaurant r3 = new Restaurant(2, "name3", b1, new Time(3000), new Time(4000));
        Restaurant r4 = new Restaurant(3, "name4", b2, new Time(4000), new Time(5000));
        buildingRepository.save(b1);
        buildingRepository.save(b2);
        restaurantRepository.save(r1);
        restaurantRepository.save(r2);
        restaurantRepository.save(r3);
        restaurantRepository.save(r4);
        List<Restaurant> list = new ArrayList<Restaurant>();
        list.add(r1);
        list.add(r2);
        list.add(r3);
        list.add(r4);
        List<Restaurant> tList = restaurantRepository.findAll();
        assertTrue(tList.equals(list));
    }

    @Test
    public void testFindRestaurantsByBuilding() {
        List<Restaurant> list = new ArrayList<Restaurant>();
        Building b1 = new Building(0, "bName1", "bStreet1", "bNum1", "bZip1", "bCity");
        Building b2 = new Building(1, "bName2", "bStreet2", "bNum2", "bZip2", "bCity");
        Restaurant r1 = new Restaurant(0, "name1", b1, new Time(1000), new Time(2000));
        Restaurant r2 = new Restaurant(1, "name2", b1, new Time(2000), new Time(3000));
        Restaurant r3 = new Restaurant(2, "name3", b1, new Time(3000), new Time(4000));
        Restaurant r4 = new Restaurant(3, "name4", b2, new Time(4000), new Time(5000));
        buildingRepository.save(b1);
        buildingRepository.save(b2);
        restaurantRepository.save(r1);
        restaurantRepository.save(r2);
        restaurantRepository.save(r3);
        restaurantRepository.save(r4);
        list.add(r1);
        list.add(r2);
        list.add(r3);
        List<Restaurant> tList = restaurantRepository.findAllByBuildingName("bName1");
        assertTrue(tList.equals(list));
    }
}
