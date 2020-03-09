package nl.tudelft.oopp.demo.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Supply;
import nl.tudelft.oopp.demo.entities.SupplyReservation;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.SupplyReservationRepository;
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
class SupplyReservationControllerTest {
    private SupplyReservation sr1;
    private SupplyReservation sr2;
    private SupplyReservation sr3;

    private User u1;
    private User u2;
    private User u3;

    @Mock
    private SupplyReservationRepository supplyReservationRepository;

    @InjectMocks
    private SupplyReservationController supplyReservationController;

    /**
     * Created all suppliers before each test.
     */

    @BeforeEach
    public void save() {
        u1 = new User("user1@email.com", "student", "fn1", "ln1", new Date(1000) , "user1");
        u2 = new User("user2@email.com", "student", "fn2", "ln2", new Date(2000), "user2");
        u3 = new User("user3@email.com", "student", "fn3", "ln3", new Date(3000), "user3");

        Building b1 = new Building("name1", "s1", "sNo1", "z1", "c1");
        Building b2 = new Building("name2", "s2", "sNo2", "z2", "c2");
        Building b3 = new Building("name3", "s3", "sNo3", "z3", "c3");

        Supply s1 = new Supply(b1, "s1", 7);
        Supply s2 = new Supply(b2, "s2", 11);
        Supply s3 = new Supply(b3, "s3", 52);

        sr1 = new SupplyReservation(new Date(1), new Time(10), new Time(11), 11, s1, u1);
        sr2 = new SupplyReservation(new Date(2), new Time(20), new Time(21), 22, s2, u2);
        sr3 = new SupplyReservation(new Date(3), new Time(30), new Time(31), 33, s3, u3);
    }

    /**
     * test if the controllers were loaded correctly and if they are not null.
     * Otherwise, @throws Exception
     */

    @Test
    public void testLoadController() throws Exception {
        assertThat(supplyReservationController).isNotNull();
    }

    @Test
    void testGetSupplyReservations() {
        List<SupplyReservation> expectedList =
                new ArrayList<SupplyReservation>(List.of(sr1,sr2,sr3));
        when(supplyReservationRepository.findAll()).thenReturn(expectedList);
        List<SupplyReservation> actualList = supplyReservationController.getSupplyReservations();

        assertEquals(expectedList,actualList);
    }

    @Test
    void testGetSupplyReservationsByUser() {
        List<SupplyReservation> expectedList = new ArrayList<SupplyReservation>(List.of(sr1));
        when(supplyReservationRepository.findByUserId(u1.getId())).thenReturn(expectedList);
        List<SupplyReservation> actualList =
                supplyReservationController.getSupplyReservationsByUser(u1.getId());

        assertEquals(expectedList,actualList);
    }

    @Test
    void testGetRoomReservationById() {
        Optional<SupplyReservation> optionalSupplyReservation = Optional.of(sr1);
        ResponseEntity<SupplyReservation> entity = ResponseEntity.of(optionalSupplyReservation);

        when(supplyReservationRepository.findById(
                sr1.getId())).thenReturn(optionalSupplyReservation);
        assertEquals(entity, supplyReservationController.getRoomReservationById(sr1.getId()));
    }

    @Test
    void testNewSupplyReservation() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        User u1 = new User("user1@email.com", "student", "fn1", "ln1", new Date(1000), "user1");
        Building b1 = new Building("name1", "s1", "sNo1", "z1", "c1");
        Supply s1 = new Supply(b1, "s1", 7);

        SupplyReservation supplyReservation = new SupplyReservation(
                new Date(1), new Time(10), new Time(11), 11, s1, u1);
        Optional<SupplyReservation> osr = Optional.of(supplyReservation);
        ResponseEntity<SupplyReservation> reservationResponseEntity = ResponseEntity.of(osr);

        when(supplyReservationRepository.save(supplyReservation)).thenReturn(supplyReservation);

        assertEquals(supplyReservation, supplyReservationController.newSupplyReservation(
                supplyReservation, uriComponentsBuilder).getBody());
    }

    @Test
    void testReplaceSupplyReservation() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        User u1 = new User("user1@email.com", "student", "fn1", "ln1", new Date(1000), "user1");
        Building b1 = new Building("name1", "s1", "sNo1", "z1", "c1");
        Supply s1 = new Supply(b1, "s1", 7);
        SupplyReservation supplyReservation = new SupplyReservation(
                new Date(1), new Time(10), new Time(11), 11, s1, u1);

        Optional<SupplyReservation> optionalSupplyReservation = Optional.of(sr1);
        ResponseEntity<SupplyReservation> entity = ResponseEntity.of(optionalSupplyReservation);
        Optional<SupplyReservation> newS = Optional.of(supplyReservation);
        ResponseEntity<SupplyReservation> responseEntity = ResponseEntity.of(newS);

        when(supplyReservationRepository.save(supplyReservation)).thenReturn(supplyReservation);
        when(supplyReservationRepository.findById(
                sr1.getId())).thenReturn(optionalSupplyReservation);

        assertEquals(responseEntity.getBody(), supplyReservationController.replaceSupplyReservation(
                supplyReservation,sr1.getId(), uriComponentsBuilder).getBody());

    }

    @Test
    void testDeleteSupplyReservation() {
        List<SupplyReservation> actualList = new ArrayList<SupplyReservation>(List.of(sr1, sr3));
        List<SupplyReservation> expectedList =
                new ArrayList<SupplyReservation>(List.of(sr1,sr2,sr3));

        Optional<SupplyReservation> optionalSupplyReservation = Optional.of(sr2);
        ResponseEntity<SupplyReservation> supplyReservationResponseEntity =
                ResponseEntity.of(optionalSupplyReservation);

        supplyReservationController.delete(sr2.getId());
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(1);
                return null;
            }
        }).when(supplyReservationRepository).deleteById(sr2.getId());
        when(supplyReservationRepository.findAll()).thenReturn(expectedList);
        assertEquals(expectedList, supplyReservationController.getSupplyReservations());
    }
}