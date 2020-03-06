package nl.tudelft.oopp.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;


@DataJpaTest
class RoomControllerTest {
    private Room r1;
    private Room r2;
    private Room r3;
    private Room r4;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomController roomController;

    /**
     * Creates all rooms before each test.
     */

    @BeforeEach
    public void save(){
        Building b1 = new Building(1, "b1", "s1", "sNo1", "z1", "c1");
        r1 = new Room(1, "r1", 11, b1);
        r2 = new Room(2, "r2", 111, b1);
        r3 = new Room(3, "r3", 1111, b1);
        r4 = new Room(4, "r3", 3111, b1);
    }

    /**
     * test for correctly loading the controllers.
     * if they are null, throws exception
     */

    @Test
    public void controllerLoads() throws Exception {
        assertThat(roomController).isNotNull();
    }

    @Test
    void getRooms() {
        List<Room> expectedList = new ArrayList<Room>(List.of(r1,r2,r3));
        when(roomRepository.findAll()).thenReturn(expectedList);
        List<Room> actualList = roomController.getRooms();

        assertEquals(expectedList, actualList);
    }

//    @Test
//    void getRoomsInBuilding() {
//    }

    @Test
    void newRoom() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building(1, "b1", "s1", "sNo1", "z1", "c1");
        Room room = new Room(1, "name", 11, b1);
        Optional<Room> roomOptional = Optional.of(room);
        ResponseEntity<Room> responseEntity = ResponseEntity.of(roomOptional);

        when(roomRepository.save(room)).thenReturn(room);

        assertEquals(room, roomController.newRoom(room, uriComponentsBuilder).getBody());
    }

    @Test
    void replaceRoom() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building(1, "b1", "s1", "sNo1", "z1", "c1");
        Room room = new Room(1, "name", 11, b1);

        Optional<Room> roomOptional = Optional.of(r1);
        ResponseEntity<Room> entity = ResponseEntity.of(roomOptional);

        Optional<Room> newR = Optional.of(room);
        ResponseEntity<Room> responseEntity = ResponseEntity.of(newR);

        when(roomRepository.save(room)).thenReturn(room);
        when(roomRepository.findById(1L)).thenReturn(roomOptional);

        assertEquals(responseEntity.getBody(), roomController.replaceRoom(room, 1, uriComponentsBuilder).getBody());
    }

    @Test
    void deleteRoom() {
        List<Room> actualList = new ArrayList<Room>(List.of(r1, r3, r4));
        List<Room> expectedList = new ArrayList<Room>(List.of(r1, r2, r3, r4));

        Optional<Room> optionalRoom = Optional.of(r2);
        ResponseEntity<Room> roomResponseEntity = ResponseEntity.of(optionalRoom);

        roomController.deleteRoom(2L);

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(1);
                return null;
            }
        }).when(roomRepository).deleteById(2L);
        when(roomRepository.findAll()).thenReturn(expectedList);
        assertEquals(expectedList, roomController.getRooms());
    }
}