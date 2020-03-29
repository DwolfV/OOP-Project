package nl.tudelft.oopp.demo.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Supply;
import nl.tudelft.oopp.demo.repositories.SupplyRepository;
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
class SupplyControllerTest {
    private Supply s1;
    private Supply s2;
    private Supply s3;
    private Supply s4;

    private Building b1;
    private Building b2;
    private Building b3;
    private Building b4;

    @Mock
    private SupplyRepository supplyRepository;

    @InjectMocks
    private SupplyController supplyController;

    /**
     * Creates all supplies (it is done before each test).
     */
    @BeforeEach
    public void save() {
        b1 = new Building("name1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s1", "sNo1", "z1", "c1");
        b2 = new Building("name2", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s2", "sNo2", "z2", "c2");
        b3 = new Building("name3", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s3", "sNo3", "z3", "c3");
        b4 = new Building("name4", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s4", "sNo4", "z4", "c4");

        s1 = new Supply(b1, "s1", 7);
        s2 = new Supply(b2, "s2", 11);
        s3 = new Supply(b3, "s3", 52);
        s4 = new Supply(b4, "s4", 77);
    }

    /**
     * Test for correctly loading the controllers and if they are not null.
     * Otherwise, @throws Exception exception
     */
    @Test
    public void testLoadController() throws Exception {
        assertThat(supplyController).isNotNull();
    }

    /**
     * checking whether getAllSuppliers return indeed the whole list.
     */

    @Test
    void testGetAllSupplies() {
        List<Supply> expectedList = new ArrayList<Supply>(List.of(s1,s2,s3,s4));
        when(supplyRepository.findAll()).thenReturn(expectedList);

        List<Supply> actualList = supplyController.getAllSupplies();
        assertEquals(expectedList, actualList);
    }

    @Test
    void getSupplyByBuildingAndName() {
        Supply s5 = new Supply(b1, "s5", 10);
        when(supplyRepository.findByBuildingIdAndName(
                b1.getId(), s5.getName())).thenReturn(Optional.of(s5));

        assertEquals(s5,
                supplyController.getSupplyByBuildingIdAndName(s5.getName(), b1.getId()).getBody());
    }

    @Test
    void getSupplyByBuilding() {
        Supply s5 = new Supply(b1, "s5", 10);
        when(supplyRepository.findByBuildingId(b1.getId())).thenReturn(List.of(s1, s5));

        assertEquals(List.of(s1, s5),
                supplyController.getSupplyByBuildingId(b1.getId()).getBody());
    }

    @Test
    void testGetSupplyById() {
        Optional<Supply> optionalSupply = Optional.of(s1);
        ResponseEntity<Supply> entity = ResponseEntity.of(optionalSupply);

        when(supplyRepository.findById(s1.getId())).thenReturn(optionalSupply);
        assertEquals(entity, supplyController.getSupplyById(s2.getId()));
    }

    @Test
    void testNewSupply() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building("name1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s1", "sNo1", "z1", "c1");
        Supply supply = new Supply(b1, "s1", 7);

        Optional<Supply> optionalSupply = Optional.of(supply);
        ResponseEntity<Supply> entity = ResponseEntity.of(optionalSupply);

        when(supplyRepository.save(supply)).thenReturn(supply);

        assertEquals(supply, supplyController.newSupply(supply, uriComponentsBuilder).getBody());
    }

    @Test
    void testUpdateSupply() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building("name1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s1", "sNo1", "z1", "c1");
        Supply supply = new Supply(b1, "s1", 7);

        Optional<Supply> optionalSupply = Optional.of(s1);
        ResponseEntity<Supply> entity = ResponseEntity.of(optionalSupply);

        Optional<Supply> newS = Optional.of(supply);
        ResponseEntity<Supply> newE = ResponseEntity.of(newS);

        when(supplyRepository.save(supply)).thenReturn(supply);
        when(supplyRepository.findById(s1.getId())).thenReturn(optionalSupply);

        assertEquals(newE.getBody(), supplyController.updateSupply(s1.getId(), s1).getBody());
    }

    @Test
    void testDeleteSupply() {
        List<Supply> actualList = new ArrayList<Supply>(List.of(s1,s2,s4));
        List<Supply> expectedList = new ArrayList<Supply>(List.of(s1,s2,s3,s4));

        Optional<Supply> optionalSupply = Optional.of(s3);
        ResponseEntity<Supply> supplyResponseEntity = ResponseEntity.of(optionalSupply);

        supplyController.deleteSupply(s2.getId());
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(2);
                return null;
            }
        }).when(supplyRepository).deleteById(s2.getId());

        when(supplyRepository.findAll()).thenReturn(expectedList);
        assertEquals(expectedList, supplyController.getAllSupplies());
    }
}