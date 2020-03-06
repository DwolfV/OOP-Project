package nl.tudelft.oopp.demo.controllers;

import java.util.ArrayList;
import java.util.Date;
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

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
class SupplyReservationControllerTest {
    private SupplyReservation sr1;
    private SupplyReservation sr2;
    private SupplyReservation sr3;

    @Mock
    private SupplyReservationRepository supplyReservationRepository;

    @InjectMocks
    private SupplyReservationController supplyReservationController;

    /**
     * Created all suppliers before each test.
     */

    @BeforeEach
    public void save(){
        User u1 = new User(1, "user1@email.com", "student", "fn1", "ln1", new Date(1000));
        User u2 = new User(2, "user2@email.com", "student", "fn2", "ln2", new Date(2000));
        User u3 = new User(3, "user3@email.com", "student", "fn3", "ln3", new Date(3000));

        Building b1 = new Building(1, "name1", "s1", "sNo1", "z1", "c1");
        Building b2 = new Building(2, "name2", "s2", "sNo2", "z2", "c2");
        Building b3 = new Building(3, "name3", "s3", "sNo3", "z3", "c3");

        Supply s1 = new Supply(1, b1, "s1", 7);
        Supply s2 = new Supply(2, b2, "s2", 11);
        Supply s3 = new Supply(3, b3, "s3", 52);

        sr1 = new SupplyReservation(1, new Date(1), 11, s1, u1);
        sr2 = new SupplyReservation(2, new Date(2), 22, s2, u2);
        sr3 = new SupplyReservation(3, new Date(3), 33, s3, u3);
    }

    /**
     * test if the controllers were loaded correctly and if they are not null.
     * Otherwise, @throws Exception
     */

    @Test
    public void controllerLoads() throws Exception {
        assertThat(supplyReservationController).isNotNull();
    }

    @Test
    void getSupplyReservations() {
        List<SupplyReservation> expectedList = new ArrayList<SupplyReservation>(List.of(sr1,sr2,sr3));
        when(supplyReservationRepository.findAll()).thenReturn(expectedList);
        List<SupplyReservation> actualList = supplyReservationController.getSupplyReservations();

        assertEquals(expectedList,actualList);
    }
//
//    @Test
//    void getSupplyReservationsByUser() {
//    }

    @Test
    void getRoomReservationById() {
        Optional<SupplyReservation> optionalSupplyReservation = Optional.of(sr1);
        ResponseEntity<SupplyReservation> entity = ResponseEntity.of(optionalSupplyReservation);

        when(supplyReservationRepository.findById(1L)).thenReturn(optionalSupplyReservation);
        assertEquals(entity, supplyReservationController.getRoomReservationById(1L));
    }

    @Test
    void newSupplyReservation() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        User u1 = new User(1, "user1@email.com", "student", "fn1", "ln1", new Date(1000));
        Building b1 = new Building(1, "name1", "s1", "sNo1", "z1", "c1");
        Supply s1 = new Supply(1, b1, "s1", 7);

        SupplyReservation supplyReservation = new SupplyReservation(1, new Date(1), 11, s1, u1);
        Optional<SupplyReservation> optionalSupplyReservation = Optional.of(supplyReservation);
        ResponseEntity<SupplyReservation> reservationResponseEntity = ResponseEntity.of(optionalSupplyReservation);

        when(supplyReservationRepository.save(supplyReservation)).thenReturn(supplyReservation);

        assertEquals(supplyReservation, supplyReservationController.newSupplyReservation(supplyReservation, uriComponentsBuilder).getBody());
    }

    @Test
    void replaceSupplyReservation() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        User u1 = new User(1, "user1@email.com", "student", "fn1", "ln1", new Date(1000));
        Building b1 = new Building(1, "name1", "s1", "sNo1", "z1", "c1");
        Supply s1 = new Supply(1, b1, "s1", 7);
        SupplyReservation supplyReservation = new SupplyReservation(1, new Date(1), 11, s1, u1);

        Optional<SupplyReservation> optionalSupplyReservation = Optional.of(sr1);
        ResponseEntity<SupplyReservation> entity = ResponseEntity.of(optionalSupplyReservation);
        Optional<SupplyReservation> newS = Optional.of(supplyReservation);
        ResponseEntity<SupplyReservation> responseEntity = ResponseEntity.of(newS);

        when(supplyReservationRepository.save(supplyReservation)).thenReturn(supplyReservation);
        when(supplyReservationRepository.findById(1L)).thenReturn(optionalSupplyReservation);

        assertEquals(responseEntity.getBody(), supplyReservationController.replaceSupplyReservation(supplyReservation,1, uriComponentsBuilder).getBody());

    }

    @Test
    void delete() {
        List<SupplyReservation> actualList = new ArrayList<SupplyReservation>(List.of(sr1, sr3));
        List<SupplyReservation> expectedList = new ArrayList<SupplyReservation>(List.of(sr1,sr2,sr3));

        Optional<SupplyReservation> optionalSupplyReservation = Optional.of(sr2);
        ResponseEntity<SupplyReservation> supplyReservationResponseEntity = ResponseEntity.of(optionalSupplyReservation);

        supplyReservationController.delete(2L);
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(1);
                return null;
            }
        }).when(supplyReservationRepository).deleteById(2L);
        when(supplyReservationRepository.findAll()).thenReturn(expectedList);
        assertEquals(expectedList, supplyReservationController.getSupplyReservations());
        }
}