package nl.tudelft.oopp.demo.controllers;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.RoomReservationRepository;
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
class RoomReservationControllerTest {
    private RoomReservation rr1;
    private RoomReservation rr2;
    private RoomReservation rr3;
    private RoomReservation rr4;

    @Mock
    private RoomReservationRepository roomReservationRepository;

    @InjectMocks
    private RoomReservationController roomReservationController;

    /**
     * Creates all roomReservations before each test.
     */


    @BeforeEach
    public void save(){
        User u1 = new User(1, "user1@email.com", "student", "fn1", "ln1", new Date(1000));
        User u2 = new User(2, "user2@email.com", "student", "fn2", "ln2", new Date(2000));
        User u3 = new User(3, "user3@email.com", "student", "fn3", "ln3", new Date(3000));
        User u4 = new User(4, "user4@email.com", "employee", "fn4", "ln4", new Date(4000));

        Building b1 = new Building(1, "b1", "s1", "sNo1", "z1", "c1");
        Room r1 = new Room(1, "r1", 11, b1);

        Building b2 = new Building(2, "b2", "s2", "sNo2", "z2", "c1");
        Room r2 = new Room(2, "r2", 21, b2);

        Building b3 = new Building(3, "b3", "s1", "sNo3", "z3", "c1");
        Room r3 = new Room(3,  "r3", 31, b3);

        Building b4 = new Building(4, "b4", "s1", "sNo4", "z4", "c1");
        Room r4 = new Room(4, "r4", 41, b4);

        rr1 = new RoomReservation(1, new Date(1), r1, new Time(1), new Time(2), u1);
        rr2 = new RoomReservation(2, new Date(2), r2, new Time(3), new Time(4), u2);
        rr3 = new RoomReservation(3, new Date(3), r3, new Time(1), new Time(4), u3);
        rr4 = new RoomReservation(4, new Date(4), r4, new Time(2), new Time(3), u4);

    }

    /**
     * test if the controllers were loaded correctly and if they are not null.
     * Otherwise, @throws Exception
     */

    @Test
    public void controll() throws Exception {
        assertThat(roomReservationController).isNotNull();
    }

//    @Test
//    void getRoomReservationsByUser() {
//    }
//
//    @Test
//    void getRoomReservationsByUserAndRoom() {
//    }

    @Test
    public void getRoomReservationsTest() {
        List<RoomReservation> expectedList = new ArrayList<RoomReservation>(List.of(rr1,rr2,rr3,rr4));
        when(roomReservationRepository.findAll()).thenReturn(expectedList);
        List<RoomReservation> actualList = roomReservationController.getRoomReservations();

        assertEquals(expectedList, actualList);
    }

    @Test
    void getRoomReservationById() {
        Optional<RoomReservation> optionalRoomReservation = Optional.of(rr1);
        ResponseEntity<RoomReservation> entity = ResponseEntity.of(optionalRoomReservation);

        when(roomReservationRepository.findById(1L)).thenReturn(optionalRoomReservation);
        assertEquals(entity, roomReservationController.getRoomReservationById(1L));
    }

    @Test
    void newRoomReservation() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        User u1 = new User(1, "user1@email.com", "student", "fn1", "ln1", new Date(1000));
        Building b1 = new Building(1, "b1", "s1", "sNo1", "z1", "c1");
        Room r1 = new Room(1, "r1", 11, b1);
        RoomReservation roomReservation = new RoomReservation(1, new Date(1), r1, new Time(1), new Time(2), u1);

        Optional<RoomReservation> optionalRoomReservation = Optional.of(roomReservation);
        ResponseEntity<RoomReservation> responseEntity = ResponseEntity.of(optionalRoomReservation);

        when(roomReservationRepository.save(roomReservation)).thenReturn(roomReservation);

        assertEquals(roomReservation, roomReservationController.newRoomReservation(roomReservation, uriComponentsBuilder).getBody());
    }

    @Test
    void replaceRoomReservation() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        User u1 = new User(1, "user1@email.com", "student", "fn1", "ln1", new Date(1000));
        Building b1 = new Building(1, "b1", "s1", "sNo1", "z1", "c1");
        Room r1 = new Room(1, "r1", 11, b1);
        RoomReservation roomReservation = new RoomReservation(1, new Date(1), r1, new Time(1), new Time(2), u1);

        Optional<RoomReservation> optionalRoomReservation = Optional.of(rr1);

        ResponseEntity<RoomReservation> entity = ResponseEntity.of(optionalRoomReservation);
        Optional<RoomReservation> newR = Optional.of(roomReservation);
        ResponseEntity<RoomReservation> responseEntity = ResponseEntity.of(newR);

        when(roomReservationRepository.save(roomReservation)).thenReturn(roomReservation);
        when(roomReservationRepository.findById(1L)).thenReturn(optionalRoomReservation);

        assertEquals(responseEntity.getBody(), roomReservationController.replaceRoomReservation(roomReservation, 1, uriComponentsBuilder).getBody());
    }
    @Test
    void deleteRoomReservation() {
        List<RoomReservation> actualList = new ArrayList<RoomReservation>(List.of(rr1, rr3, rr4));
        List<RoomReservation> expectedList = new ArrayList<RoomReservation>(List.of(rr1,rr2,rr3,rr4));

        Optional<RoomReservation> optionalRoomReservation = Optional.of(rr2);
        ResponseEntity<RoomReservation> responseEntity = ResponseEntity.of(optionalRoomReservation);

        roomReservationController.deleteRoomReservation(2L);

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(1);
                return null;
            }
        }).when(roomReservationRepository).deleteById(2L);
        when(roomReservationRepository.findAll()).thenReturn(expectedList);

        assertEquals(expectedList, roomReservationController.getRoomReservations());
    }
}