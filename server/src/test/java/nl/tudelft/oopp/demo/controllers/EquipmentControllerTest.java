package nl.tudelft.oopp.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Equipment;
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
    private Equipment e1;
    private Equipment e2;
    private Equipment e3;

    @Mock
    private EquipmentRepository equipmentRepository;

    @InjectMocks
    private EquipmentController equipmentController;

    /**
     * Creates all equipments before each test.
     */

    @BeforeEach
    public void save(){
        //constructor long id, Room room, String name, int amount for building
        //constructor long id, String name, Integer capacity, Building building) for room
        Building b1 = new Building(1, "b1", "s1", "sNo1", "z1", "c1");
        Room r1 = new Room(1, "name", 11, b1);
        e1 = new Equipment(1, r1, "e1", 15);
        e2 = new Equipment(2, r1, "e2", 22);
        e3 = new Equipment(3, r1, "e3", 45);
    }

    /**
     * test if the controllers were loaded correctly and if they are not null.
     * Otherwise, @throws Exception
     */

    @Test
    public void controllerLoads() throws Exception {
        assertThat(equipmentController).isNotNull();
    }

    @Test
    void getEquipment() {
        List<Equipment> expectedList = new ArrayList<Equipment>(List.of(e1,e2,e3));
        when(equipmentRepository.findAll()).thenReturn(expectedList);
        List<Equipment> actualList = equipmentController.getEquipment();

        assertEquals(expectedList, actualList);
    }

    @Test
    void getEquipmentById() {
        Optional<Equipment> optionalEquipment = Optional.of(e1);
        ResponseEntity<Equipment> entity = ResponseEntity.of(optionalEquipment);

        when(equipmentRepository.findById(1L)).thenReturn(optionalEquipment);
        assertEquals(entity, equipmentController.getEquipmentById(1L));
    }

//    @Test
//    void getEquipmentByName() {
//        Optional<Equipment> optionalEquipment = Optional.of(e1);
//        ResponseEntity<Equipment> entity = ResponseEntity.of(optionalEquipment);
//
//       // when(equipmentRepository.findByName("e1")).thenReturn(optionalEquipment);
//        Mockito.when(equipmentRepository.findByName("e1")).thenAnswer(invocationOnMock -> optionalEquipment);
//        assertEquals(entity, equipmentController.getEquipmentByName("e1"));
//    }
//
//    @Test
//    void getEquipmentByRoomId() {
//    }

    @Test
    void addNewEquipment() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building(1, "b1", "s1", "sNo1", "z1", "c1");
        Room r1 = new Room(1, "name", 11, b1);
        Equipment equipment = new Equipment(1, r1, "e1", 15);
        Optional<Equipment> optionalEquipment = Optional.of(equipment);
        ResponseEntity<Equipment> responseEntity = ResponseEntity.of(optionalEquipment);

        when(equipmentRepository.save(equipment)).thenReturn(equipment);

        assertEquals(equipment, equipmentController.addNewEquipment(equipment, uriComponentsBuilder).getBody());
    }

    @Test
    void replaceEquipment() {
        Building b1 = new Building(1, "b1", "s1", "sNo1", "z1", "c1");
        Room r1 = new Room(1, "name", 11, b1);
        Equipment equipment = new Equipment(1, r1, "e1", 15);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Optional<Equipment> optionalEquipment = Optional.of(e1);

        ResponseEntity<Equipment> entity = ResponseEntity.of(optionalEquipment);
        Optional<Equipment> newE = Optional.of(equipment);
        ResponseEntity<Equipment> responseEntity = ResponseEntity.of(newE);

        when(equipmentRepository.save(equipment)).thenReturn(equipment);
        when(equipmentRepository.findById(1L)).thenReturn(optionalEquipment);

        assertEquals(responseEntity.getBody(), equipmentController.replaceEquipment(equipment, 1, uriComponentsBuilder).getBody());
    }

    @Test
    void deleteEquipment() {
        List<Equipment> actualList = new ArrayList<Equipment>(List.of(e1,e3));
        List<Equipment> expectedList = new ArrayList<Equipment>(List.of(e1,e2,e3));

        Optional<Equipment> optionalEquipment = Optional.of(e2);
        ResponseEntity<Equipment> equipmentResponseEntity = ResponseEntity.of(optionalEquipment);

        equipmentController.deleteEquipment(2L);
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(1);
                return null;
            }
        }).when(equipmentRepository).deleteById(2L);
        when(equipmentRepository.findAll()).thenReturn(expectedList);
        assertEquals(expectedList, equipmentController.getEquipment());
    }
}