package nl.tudelft.oopp.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.controllers.BuildingController;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

@DataJpaTest
public class BuildingControllerTest {
    private Building b1, b2, b3, b4, b5;

    @Mock
    private BuildingRepository buildingRepository;

    @InjectMocks
    private BuildingController buildingController;

    /**
     * Creates all buildings and is done before each test.
     */

    @BeforeEach
    public void save(){
        b1 = new Building(1, "name1", "s1", "sNo1", "z1", "c1");
        b2 = new Building(2, "name2", "s2", "sNo2", "z2", "c2");
        b3 = new Building(3, "name3", "s3", "sNo3", "z3", "c3");
        b4 = new Building(4, "name4", "s4", "sNo4", "z4", "c4");
        b5 = new Building(5, "name5", "s5", "sNo5", "z5", "c5");
    }

    /**
     * Test for correctly loading the controllers and if they are not null.
     * Otherwise, @throws Exception exception
     */

    @Test
    public void controllerLoads() throws Exception {
        assertThat(buildingController).isNotNull();
    }

    /**
     * Checking whether getAllBuildings returns indeed the whole list.
     */

    @Test
    public void getAllBuildingsTest() {
        List<Building> expectedList = new ArrayList<Building>(List.of(b1,b2,b3,b4,b5));
        when(buildingRepository.findAll()).thenReturn(expectedList);
        List<Building> actualList = buildingController.getAllBuildings();

        assertEquals(expectedList, actualList);
    }

    @Test
    public void getBuildingByIdTest() {
        Optional<Building> optionalBuilding = Optional.of(b1);
        ResponseEntity<Building> entity = ResponseEntity.of(optionalBuilding);

        when(buildingRepository.findById(1L)).thenReturn(optionalBuilding);
        assertEquals(entity, buildingController.getBuildingById(1L));
    }

    @Test
    public void updateBuilding() {
        Building building = new Building(1, "name1", "s1", "sNo1", "z1", "c1");
        UriComponentsBuilder u1 = UriComponentsBuilder.newInstance();
        Optional<Building> optionalBuilding = Optional.of(b1);
        ResponseEntity<Building> entity = ResponseEntity.of(optionalBuilding);
        Optional<Building> newB = Optional.of(building);
        ResponseEntity<Building> newE = ResponseEntity.of(newB);

        when(buildingRepository.save(building)).thenReturn(building);
        when(buildingRepository.findById(1L)).thenReturn(optionalBuilding);

        assertEquals(newE.getBody(), buildingController.updateBuilding(1, building).getBody());
    }

    @Test
    public void newBuilding(){
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building building = new Building(1, "name1", "s1", "sNo1", "z1", "c1");
        Optional<Building> optionalBuilding = Optional.of(building);
        ResponseEntity<Building> entity = ResponseEntity.of(optionalBuilding);

        when(buildingRepository.save(building)).thenReturn(building);

        assertEquals(building, buildingController.newBuilding(building, uriComponentsBuilder).getBody());

    }
}

