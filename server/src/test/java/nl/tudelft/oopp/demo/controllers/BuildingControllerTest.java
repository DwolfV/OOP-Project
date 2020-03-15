package nl.tudelft.oopp.demo.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Equipment;
import nl.tudelft.oopp.demo.entities.Item;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.EquipmentRepository;
import nl.tudelft.oopp.demo.repositories.ItemRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
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
public class BuildingControllerTest {
    private Building b1;
    private Building b2;
    private Building b3;
    private Building b4;
    private Building b5;

    private Room r1;
    private Room r2;

    private Item i1;
    private Item i2;

    private Equipment e1;
    private Equipment e2;

    @Mock
    private EquipmentRepository equipmentRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private BuildingRepository buildingRepository;

    @InjectMocks
    private BuildingController buildingController;

    @Mock
    private RoomRepository roomRepository;

    public BuildingControllerTest() {
    }

    /**
     * Creates all buildings and is done before each test.
     */
    @BeforeEach
    public void save() {
        b1 = new Building("name1", "s1", "sNo1", "z1", "c1");
        b2 = new Building("name2", "s2", "sNo2", "z2", "c2");
        b3 = new Building("name3", "s3", "sNo3", "z3", "c3");
        b4 = new Building("name4", "s4", "sNo4", "z4", "c4");
        b5 = new Building("name5", "s5", "sNo5", "z5", "c5");
    }

    /**
     * Test for correctly loading the controllers and if they are not null.
     * Otherwise, @throws Exception exception
     */
    @Test
    public void testLoadController() throws Exception {
        assertThat(buildingController).isNotNull();
    }

    /**
     * Checking whether getAllBuildings returns indeed the whole list.
     */
    @Test
    public void testGetAllBuildingsTest() {
        List<Building> expectedList = new ArrayList<Building>(List.of(b1, b2, b3, b4, b5));
        when(buildingRepository.findAll()).thenReturn(expectedList);
        List<Building> actualList = buildingController.getAllBuildings();

        assertEquals(expectedList, actualList);
    }

    @Test
    public void testGetBuildingByIdTest() {
        Optional<Building> optionalBuilding = Optional.of(b1);
        ResponseEntity<Building> entity = ResponseEntity.of(optionalBuilding);

        when(buildingRepository.findById(b1.getId())).thenReturn(optionalBuilding);
        assertEquals(entity, buildingController.getBuildingById(b1.getId()));
    }

    @Test
    public void testGetFilteredBuilding() {
        i1 = new Item("projector");
        i2 = new Item("blackboard");

        r1 = new Room("r1", 50, b1);
        r2 = new Room("r2", 60, b2);

        e1 = new Equipment(r1, i1, 2);
        e2 = new Equipment(r2, i2, 3);

        itemRepository.save(i1);
        itemRepository.save(i2);

        roomRepository.save(r1);
        roomRepository.save(r2);

        equipmentRepository.save(e1);
        equipmentRepository.save(e2);

        r1.setEquipment(new ArrayList<>(List.of(e1)));
        r2.setEquipment(new ArrayList<>(List.of(e2)));

        b1.setRooms(new ArrayList<>(List.of(r1)));
        b2.setRooms(new ArrayList<>(List.of(r2)));

        List<Building> expectedBuildings = new ArrayList<>(List.of(b1));
        List<Building> repoResult = new ArrayList<>(List.of(b1,b2));
        when(buildingRepository.filterBuilding(50)).thenReturn(repoResult);
        List<Building> actualList = buildingController.getFilteredBuildings(50, i1.getName(), null, null, null);

        assertEquals(expectedBuildings, actualList);

    }

    @Test
    public void testUpdateBuilding() {
        Building building = new Building("name1", "s1", "sNo1", "z1", "c1");
        UriComponentsBuilder u1 = UriComponentsBuilder.newInstance();
        Optional<Building> optionalBuilding = Optional.of(b1);
        ResponseEntity<Building> entity = ResponseEntity.of(optionalBuilding);
        Optional<Building> newB = Optional.of(building);
        ResponseEntity<Building> newE = ResponseEntity.of(newB);

        when(buildingRepository.save(building)).thenReturn(building);
        when(buildingRepository.findById(b1.getId())).thenReturn(optionalBuilding);

        assertEquals(newE.getBody(),
                buildingController.updateBuilding(b1.getId(), building).getBody());
    }

    @Test
    public void testNewBuilding() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building building = new Building("name1", "s1", "sNo1", "z1", "c1");
        Optional<Building> optionalBuilding = Optional.of(building);
        ResponseEntity<Building> entity = ResponseEntity.of(optionalBuilding);

        when(buildingRepository.save(building)).thenReturn(building);

        assertEquals(building,
                buildingController.newBuilding(building, uriComponentsBuilder).getBody());

    }

    @Test
    public void testDeleteBuilding() {
        List<Building> actualList = new ArrayList<Building>(List.of(b1, b2, b4, b5));
        List<Building> expectedList = new ArrayList<Building>(List.of(b1, b2, b3, b4, b5));

        Optional<Building> optionalBuilding = Optional.of(b3);
        ResponseEntity<Building> buildingResponseEntity = ResponseEntity.of(optionalBuilding);

        buildingController.deleteBuilding(b3.getId());

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(2);
                return null;
            }
        }).when(buildingRepository).deleteById(b3.getId());

        when(buildingRepository.findAll()).thenReturn(expectedList);
        assertEquals(expectedList, buildingController.getAllBuildings());
    }
}

