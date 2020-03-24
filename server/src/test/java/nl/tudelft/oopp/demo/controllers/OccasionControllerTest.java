package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Occasion;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.OccasionRepository;
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

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
public class OccasionControllerTest {
    private Occasion o1;
    private Occasion o2;
    private Occasion o3;
    private Occasion o4;

    @InjectMocks
    private OccasionController occasionController;

    @Mock
    private OccasionRepository occasionRepository;

    @BeforeEach
    public void save() {
        Building b1 = new Building("name1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s1", "sNo1", "z1", "c1");
        Building b2 = new Building("name2", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s2", "sNo2", "z2", "c2");

        o1 = new Occasion(LocalDate.parse("2020-03-24"), LocalTime.parse("10:00"), LocalTime.parse("18:00"), b1);
        o2 = new Occasion(LocalDate.parse("2020-02-24"), LocalTime.parse("09:00"), LocalTime.parse("13:00"), b1);
        o3 = new Occasion(LocalDate.parse("2020-01-12"), LocalTime.parse("12:00"), LocalTime.parse("22:00"), b2);
        o4 = new Occasion(LocalDate.parse("2020-07-02"), LocalTime.parse("17:00"), LocalTime.parse("23:00"), b2);
    }

    @Test
    public void testLoadController() {
        assertThat(occasionController).isNotNull();
    }

    /**
     * Test for getting all the occasions
     */
    @Test
    public void testGetAll() {
        List<Occasion> expectedList = new ArrayList<>(List.of(o1, o2, o3, o4));
        when(occasionRepository.findAll()).thenReturn(expectedList);
        List<Occasion> actualList = occasionController.getAllOccasions();

        assertEquals(actualList, expectedList);
    }

    /**
     * Test for getting all the occasions that affect a building
     */
    @Test
    public void testGetAllByBuilding() {
        List<Occasion> expectedList = new ArrayList<>(List.of(o1, o2));
        when(occasionRepository.findByBuildingId(o1.getBuilding().getId())).thenReturn(expectedList);
        List<Occasion> actualList = occasionController.getOccasionsByBuilding(o1.getBuilding().getId());

        assertEquals(actualList, expectedList);
    }

    /**
     * Test for getting an occasion by id.
     */
    @Test
    public void testGetById() {
        Optional<Occasion> optionalOccasion = Optional.of(o1);
        ResponseEntity<Occasion> entity = ResponseEntity.of(optionalOccasion);

        when(occasionRepository.findById(o1.getId())).thenReturn(optionalOccasion);
        assertEquals(entity, occasionController.getOccasionById(o1.getId()));
    }

    /**
     * Test for adding an occasion.
     */
    @Test
    public void testAddOccasion() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building("name1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s1", "sNo1", "z1", "c1");
        Occasion o5 = new Occasion(LocalDate.parse("2020-07-02"), LocalTime.parse("17:00"), LocalTime.parse("23:00"), b1);

        when(occasionRepository.save(o5)).thenReturn(o5);

        assertEquals(o5, occasionController.addOccasion(o5, uriComponentsBuilder).getBody());
    }

    /**
     * Test for updating the occasion
     */
    @Test
    public void testUpdateOccasion() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building("name1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s1", "sNo1", "z1", "c1");

        Optional<Occasion> old = Optional.of(o1);
        ResponseEntity<Occasion> oldEntity = ResponseEntity.of(old);

        Occasion o5 = new Occasion(LocalDate.parse("2020-07-02"), LocalTime.parse("17:00"), LocalTime.parse("23:00"), b1);
        Optional<Occasion> newOccasion = Optional.of(o5);
        ResponseEntity<Occasion> newEntity = ResponseEntity.of(newOccasion);

        when(occasionRepository.save(o5)).thenReturn(o5);
        when(occasionRepository.findById(o1.getId())).thenReturn(old);

        assertEquals(newEntity.getBody(), occasionController.updateOccasion(o1.getId(), o5).getBody());
    }

    /**
     * Test to delete an occasion.
     */
    @Test
    public void testDeleteOccasion() {
        List<Occasion> actualList = new ArrayList<>(List.of(o1, o2, o4));
        List<Occasion> expectedList = new ArrayList<>(List.of(o1, o2, o3, o4));

        Optional<Occasion> optionalOccasion = Optional.of(o3);
        ResponseEntity<Occasion> occasionResponseEntity = ResponseEntity.of(optionalOccasion);

        occasionController.deleteOccasion(o3.getId());

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(2);
                return null;
            }
        }).when(occasionRepository).deleteById(o3.getId());

        when(occasionRepository.findAll()).thenReturn(expectedList);
        System.out.println(occasionRepository.findAll().size());
        assertEquals(expectedList, occasionController.getAllOccasions());
    }
}
