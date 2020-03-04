package nl.tudelft.oopp.demo;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.controllers.BuildingController;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BuildingController.class)
@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
public class BuildingControllerTest {

    @Mock
    BuildingRepository buildingRepository;

    @InjectMocks
    BuildingController buildingController;

    /**
     * Check whether the BuildingController is loaded correctly.
     * @throws Exception exception.
     */

    @Test
    public void contextLoads() throws Exception {
        assertThat(buildingController).isNotNull();
    }

    @Test
    public void getAllTest() {
        List<Building> list=new ArrayList<>();
        Building b1 = new Building(1, "name1", "s1", "sNo1", "z1", "c1");
        Building b2 = new Building(2, "name2", "s2", "sNo2", "z2", "c2");
        Building b3 = new Building(3, "name3", "s3", "sNo3", "z3", "c3");
        Building b4 = new Building(4, "name4", "s4", "sNo4", "z4", "c4");
        Building b5 = new Building(5, "name5", "s5", "sNo5", "z5", "c5");
       list.add(b1);
       list.add(b2);
       list.add(b3);
       list.add(b4);
       list.add(b5);
       when(buildingRepository.findAll()).thenReturn(list);
       List<Building> list1 = buildingController.getAllBuildings();
       assertThat(list1.size()).isEqualTo(5);
    }
}
