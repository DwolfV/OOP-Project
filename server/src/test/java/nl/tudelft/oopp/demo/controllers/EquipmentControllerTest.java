package nl.tudelft.oopp.demo.controllers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Equipment;
import nl.tudelft.oopp.demo.entities.Item;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.EquipmentRepository;
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
class EquipmentControllerTest {
    private Item i1;
    private Item i2;
    private Item i3;

    private Equipment e1;
    private Equipment e2;
    private Equipment e3;

    private Room r1;
    private Building b1;

    @Mock
    private EquipmentRepository equipmentRepository;

    @InjectMocks
    private EquipmentController equipmentController;

    /**
     * Creates all equipments before each test.
     */
    @BeforeEach
    public void save() {
        //constructor long id, Room room, String name, int amount for building
        //constructor long id, String name, Integer capacity, Building building) for room
        b1 = new Building("b1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s1", "sNo1", "z1", "c1");

        r1 = new Room("name", 11, b1);

        i1 = new Item("e1");
        i2 = new Item("e2");
        i3 = new Item("e3");

        e1 = new Equipment(r1, i1, 15);
        e2 = new Equipment(r1, i2, 22);
        e3 = new Equipment(r1, i3, 45);
    }

    /**
     * test if the controllers were loaded correctly and if they are not null.
     * Otherwise, @throws Exception
     */
    @Test
    public void testLoadController() throws Exception {
        assertThat(equipmentController).isNotNull();
    }

    @Test
    void testGetEquipment() {
        List<Equipment> expectedList = new ArrayList<Equipment>(List.of(e1,e2,e3));
        when(equipmentRepository.findAll()).thenReturn(expectedList);
        List<Equipment> actualList = equipmentController.getEquipment();

        assertEquals(expectedList, actualList);
    }

    @Test
    void testGetEquipmentById() {
        Optional<Equipment> optionalEquipment = Optional.of(e1);
        ResponseEntity<Equipment> entity = ResponseEntity.of(optionalEquipment);

        when(equipmentRepository.findById(e1.getId())).thenReturn(optionalEquipment);
        assertEquals(entity, equipmentController.getEquipmentById(e1.getId()));
    }

    @Test
    void testGetEquipmentByName() {
        Optional<Equipment> optionalEquipment = Optional.of(e1);
        ResponseEntity<Equipment> entity = ResponseEntity.of(optionalEquipment);

        Mockito.when(equipmentRepository.findByItemName(
                "e1")).thenAnswer(invocationOnMock -> List.of(e1));
        assertEquals(List.of(entity.getBody()),
                equipmentController.getEquipmentByName("e1").getBody());
    }

    @Test
    void testGetEquipmentByRoomId() {
        Optional<Equipment> optionalEquipment = Optional.of(e1);
        ResponseEntity<Equipment> entity = ResponseEntity.of(optionalEquipment);

        Mockito.when(equipmentRepository.findByRoomId(
                r1.getId())).thenAnswer(invocationOnMock -> List.of(e1));
        assertEquals(List.of(entity.getBody()),
                equipmentController.getEquipmentByRoomId(r1.getId()).getBody());
    }

    @Test
    void testAddNewEquipment() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building("b1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s1", "sNo1", "z1", "c1");
        Room r1 = new Room("name", 11, b1);
        Equipment equipment = new Equipment(r1, i1, 15);
        Optional<Equipment> optionalEquipment = Optional.of(equipment);
        ResponseEntity<Equipment> responseEntity = ResponseEntity.of(optionalEquipment);

        when(equipmentRepository.save(equipment)).thenReturn(equipment);

        assertEquals(equipment,
                equipmentController.addNewEquipment(equipment, uriComponentsBuilder).getBody());
    }

    @Test
    void testReplaceEquipment() {
        Building b1 = new Building("b1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s1", "sNo1", "z1", "c1");
        Room r1 = new Room("name", 11, b1);
        Equipment equipment = new Equipment(r1, i1, 15);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Optional<Equipment> optionalEquipment = Optional.of(e1);

        ResponseEntity<Equipment> entity = ResponseEntity.of(optionalEquipment);
        Optional<Equipment> newE = Optional.of(equipment);
        ResponseEntity<Equipment> responseEntity = ResponseEntity.of(newE);

        when(equipmentRepository.save(equipment)).thenReturn(equipment);
        when(equipmentRepository.findById(e1.getId())).thenReturn(optionalEquipment);

        assertEquals(responseEntity.getBody(),equipmentController.replaceEquipment(
                equipment, e1.getId()).getBody());
    }

    @Test
    void testDeleteEquipment() {
        List<Equipment> actualList = new ArrayList<Equipment>(List.of(e1,e3));
        List<Equipment> expectedList = new ArrayList<Equipment>(List.of(e1,e2,e3));

        Optional<Equipment> optionalEquipment = Optional.of(e2);
        ResponseEntity<Equipment> equipmentResponseEntity = ResponseEntity.of(optionalEquipment);

        equipmentController.deleteEquipment(e2.getId());
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(1);
                return null;
            }
        }).when(equipmentRepository).deleteById(e2.getId());
        when(equipmentRepository.findAll()).thenReturn(expectedList);
        assertEquals(expectedList, equipmentController.getEquipment());
    }
}