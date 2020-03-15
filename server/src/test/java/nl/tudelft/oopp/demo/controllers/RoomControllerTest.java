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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;


@DataJpaTest
class RoomControllerTest {
    private Room r1;
    private Room r2;
    private Room r3;
    private Room r4;

    private Equipment e1;
    private Equipment e2;
    private Equipment e3;

    private Item i1;
    private Item i2;

    private Building b1;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomController roomController;

    /**
     * Creates all rooms before each test.
     */

    @BeforeEach
    public void save() {
        b1 = new Building("b1", "s1", "sNo1", "z1", "c1");
        r1 = new Room("r1", 11, b1);
        r2 = new Room("r2", 111, b1);
        r3 = new Room("r3", 1111, b1);
        r4 = new Room("r4", 3111, b1);
    }

    /**
     * test for correctly loading the controllers.
     * if they are null, throws exception
     */

    @Test
    public void testLoadController() throws Exception {
        assertThat(roomController).isNotNull();
    }

    @Test
    void testGetAll() {
        List<Room> expectedList = new ArrayList<Room>(List.of(r1,r2,r3));
        when(roomRepository.findAll()).thenReturn(expectedList);
        List<Room> actualList = roomController.getRooms();

        assertEquals(expectedList, actualList);
    }

    @Test
    void testFilterWithoutEquipment() {
        List<Room> expectedList = new ArrayList<Room>(List.of(r2,r3,r4));
        when(roomRepository.filterRoom(b1.getId(),50)).thenReturn(expectedList);
        List<Room> actualList = roomController.getFilteredRooms(b1.getId(),50, null, null, null, null);

        assertEquals(expectedList, actualList);
    }

    @Test
    void testFilterWithEquipment() {
        i1 = new Item("blackboard");
        i2 = new Item("projector");
        itemRepository.save(i1);
        itemRepository.save(i2);

        e1 = new Equipment(r2, i1, 2);
        e2 = new Equipment(r3, i1, 3);
        e3 = new Equipment(r2, i2, 1);
        equipmentRepository.save(e1);
        equipmentRepository.save(e2);
        equipmentRepository.save(e3);

        r2.setEquipment(new ArrayList<>(List.of(e1, e3)));
        r3.setEquipment(new ArrayList<>(List.of(e2)));

        List<Room> expectedList = new ArrayList<Room>(List.of(r2));
        List<Room> repoResult = new ArrayList<Room>(List.of(r2, r3, r4));
        when(roomRepository.filterRoom(b1.getId(),50)).thenReturn(repoResult);
        List<Room> actualList = roomController.getFilteredRooms(b1.getId(),50, i1.getName(), i2.getName(), null, null);

        System.out.println(expectedList + " nice");
        System.out.println(actualList);
        assertEquals(expectedList, actualList);
    }

    @Test
    void testGetRoomsInBuilding() {
        Optional<Room> or1 = Optional.of(r1);
        Optional<Room> or2 = Optional.of(r2);
        Optional<Room> or3 = Optional.of(r3);
        Optional<Room> or4 = Optional.of(r4);
        ResponseEntity<Room> er1 = ResponseEntity.of(or1);
        ResponseEntity<Room> er2 = ResponseEntity.of(or2);
        ResponseEntity<Room> er3 = ResponseEntity.of(or3);
        ResponseEntity<Room> er4 = ResponseEntity.of(or4);

        Mockito.when(roomRepository.findByBuildingId(
                b1.getId())).thenAnswer(invocationOnMock -> List.of(r1, r2, r3, r4));
        assertEquals(List.of(er1.getBody(), er2.getBody(), er3.getBody(), er4.getBody()),
                roomController.getRoomsInBuilding(b1.getId()).getBody());
    }

    @Test
    void testNewRoom() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building("b1", "s1", "sNo1", "z1", "c1");
        Room room = new Room("name", 11, b1);
        Optional<Room> roomOptional = Optional.of(room);
        ResponseEntity<Room> responseEntity = ResponseEntity.of(roomOptional);

        when(roomRepository.save(room)).thenReturn(room);

        assertEquals(room, roomController.newRoom(room, uriComponentsBuilder).getBody());
    }

    @Test
    void testReplaceRoom() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building("b1", "s1", "sNo1", "z1", "c1");
        Room room = new Room("name", 11, b1);

        Optional<Room> roomOptional = Optional.of(r1);
        ResponseEntity<Room> entity = ResponseEntity.of(roomOptional);

        Optional<Room> newR = Optional.of(room);
        ResponseEntity<Room> responseEntity = ResponseEntity.of(newR);

        when(roomRepository.save(room)).thenReturn(room);
        when(roomRepository.findById(r1.getId())).thenReturn(roomOptional);

        assertEquals(responseEntity.getBody(), roomController.replaceRoom(
                room, 1, uriComponentsBuilder).getBody());
    }

    @Test
    void testDeleteRoom() {
        List<Room> actualList = new ArrayList<Room>(List.of(r1, r3, r4));
        List<Room> expectedList = new ArrayList<Room>(List.of(r1, r2, r3, r4));

        Optional<Room> optionalRoom = Optional.of(r2);
        ResponseEntity<Room> roomResponseEntity = ResponseEntity.of(optionalRoom);

        roomController.deleteRoom(r2.getId());

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(1);
                return null;
            }
        }).when(roomRepository).deleteById(r2.getId());
        when(roomRepository.findAll()).thenReturn(expectedList);
        assertEquals(expectedList, roomController.getRooms());
    }
}