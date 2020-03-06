package nl.tudelft.oopp.demo.controllers;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@DataJpaTest
class SupplyControllerTest {
    private Supply s1;
    private Supply s2;
    private Supply s3;
    private Supply s4;

    @Mock
    private SupplyRepository supplyRepository;

    @InjectMocks
    private SupplyController supplyController;

    /**
     * Creates all supplies (it is done before each test).
     */

    @BeforeEach
    public void save(){
        //long id,
        //                  Building building,
        //                  String name,
        //                  int stock
        Building b1 = new Building(1, "name1", "s1", "sNo1", "z1", "c1");
        Building b2 = new Building(2, "name2", "s2", "sNo2", "z2", "c2");
        Building b3 = new Building(3, "name3", "s3", "sNo3", "z3", "c3");
        Building b4 = new Building(4, "name4", "s4", "sNo4", "z4", "c4");
        s1 = new Supply(1, b1, "s1", 7);
        s2 = new Supply(2, b2, "s2", 11);
        s3 = new Supply(3, b3, "s3", 52);
        s4 = new Supply(4, b4, "s4", 77);
    }

    /**
     * Test for correctly loading the controllers and if they are not null.
     * Otherwise, @throws Exception exception
     */

    @Test
    public void controllerLoad() throws Exception {
        assertThat(supplyController).isNotNull();
    }

    /**
     * checking whether getAllSuppliers return indeed the whole list.
     */

    @Test
    void getAllSupplies() {
        List<Supply> expectedList = new ArrayList<Supply>(List.of(s1,s2,s3,s4));
        when(supplyRepository.findAll()).thenReturn(expectedList);

        List<Supply> actualList = supplyController.getAllSupplies();
        assertEquals(expectedList, actualList);
    }

//    @Test
//    void getSupplyByBuildingAndName() {
//    }
//
//    @Test
//    void getSupplyByBuilding() {
//    }

    @Test
    void getSupplyById() {
        Optional<Supply> optionalSupply = Optional.of(s1);
        ResponseEntity<Supply> entity = ResponseEntity.of(optionalSupply);

        when(supplyRepository.findById(1L)).thenReturn(optionalSupply);
        assertEquals(entity, supplyController.getSupplyById(1L));
    }

    @Test
    void createNewActivity() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building(1, "name1", "s1", "sNo1", "z1", "c1");
        Supply supply = new Supply(1, b1, "s1", 7);

        Optional<Supply> optionalSupply = Optional.of(supply);
        ResponseEntity<Supply> entity = ResponseEntity.of(optionalSupply);

        when(supplyRepository.save(supply)).thenReturn(supply);

        assertEquals(supply, supplyController.newSupply(supply, uriComponentsBuilder).getBody());
    }

    @Test
    void updateSupply() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building(1, "name1", "s1", "sNo1", "z1", "c1");
        Supply supply = new Supply(1, b1, "s1", 7);

        Optional<Supply> optionalSupply = Optional.of(s1);
        ResponseEntity<Supply> entity = ResponseEntity.of(optionalSupply);

        Optional<Supply> newS = Optional.of(supply);
        ResponseEntity<Supply> newE = ResponseEntity.of(newS);

        when(supplyRepository.save(supply)).thenReturn(supply);
        when(supplyRepository.findById(1L)).thenReturn(optionalSupply);

        assertEquals(newE.getBody(), supplyController.updateSupply(1, supply).getBody());
    }

    @Test
    void deleteSupply() {
        List<Supply> actualList = new ArrayList<Supply>(List.of(s1,s2,s4));
        List<Supply> expectedList = new ArrayList<Supply>(List.of(s1,s2,s3,s4));

        Optional<Supply> optionalSupply = Optional.of(s3);
        ResponseEntity<Supply> supplyResponseEntity = ResponseEntity.of(optionalSupply);

        supplyController.deleteSupply(2L);
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(2);
                return null;
            }
        }).when(supplyRepository).deleteById(2L);

        when(supplyRepository.findAll()).thenReturn(expectedList);
        assertEquals(expectedList, supplyController.getAllSupplies());
    }
}