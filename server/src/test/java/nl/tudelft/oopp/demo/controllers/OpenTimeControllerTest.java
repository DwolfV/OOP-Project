package nl.tudelft.oopp.demo.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.OpenTime;
import nl.tudelft.oopp.demo.repositories.OpenTimeRepository;
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
class OpenTimeControllerTest {
    private OpenTime t1;
    private OpenTime t2;
    private OpenTime t3;

    @Mock
    private OpenTimeRepository openTimeRepository;

    @InjectMocks
    private OpenTimeController openTimeController;

    /**
     * Creates all openTimes before each test.
     */

    @BeforeEach
    public void save() {

        Building b1 = new Building("name1", "s1", "sNo1", "z1", "c1");
        Building b2 = new Building("name2", "s1", "sNo2", "z2", "c1");
        Building b3 = new Building("name3", "s1", "sNo3", "z3", "c1");

        t1 = new OpenTime("d", new Time(1), new Time(2), b1);
        t2 = new OpenTime("Monday", new Time(1), new Time(3), b2);
        t3 = new OpenTime("Friday", new Time(4), new Time(5), b3);

    }

    /**
     * test if the controllers were loaded correctly and if they are not null.
     * Otherwise, @throws Exception
     */
    @Test
    public void testLoadController() throws Exception {
        assertThat(openTimeController).isNotNull();
    }

    @Test
    public void testGetAll() {
        List<OpenTime> expectedList = new ArrayList<OpenTime>(List.of(t1,t2,t3));
        when(openTimeRepository.findAll()).thenReturn(expectedList);
        List<OpenTime> actualList = openTimeController.getOpenTimes();

        assertEquals(actualList, expectedList);
    }

    @Test
    public void testGetById() {
        Optional<OpenTime> optionalOpenTime = Optional.of(t1);
        ResponseEntity<OpenTime> entity = ResponseEntity.of(optionalOpenTime);

        when(openTimeRepository.findById(t1.getId())).thenReturn(optionalOpenTime);
        assertEquals(entity, openTimeController.getOpenTimeById(t1.getId()));

    }

    @Test
    public void testNewOpenTime() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building("name1", "s1", "sNo1", "z1", "c1");
        OpenTime time = new OpenTime("d", new Time(1), new Time(2), b1);
        Optional<OpenTime> optionalOpenTime = Optional.of(time);
        ResponseEntity<OpenTime> responseEntity = ResponseEntity.of(optionalOpenTime);

        when(openTimeRepository.save(time)).thenReturn(time);

        assertEquals(time, openTimeController.newOpenTime(time, uriComponentsBuilder).getBody());
    }

    @Test
    public void testUpdateOpenTime() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building("name1", "s1", "sNo1", "z1", "c1");
        OpenTime time = new OpenTime("d", new Time(1), new Time(2), b1);
        Optional<OpenTime> optionalOpenTime = Optional.of(t1);

        ResponseEntity<OpenTime> entity = ResponseEntity.of(optionalOpenTime);
        Optional<OpenTime> newO = Optional.of(time);
        ResponseEntity<OpenTime> responseEntity = ResponseEntity.of(newO);

        when(openTimeRepository.save(time)).thenReturn(time);
        when(openTimeRepository.findById(t1.getId())).thenReturn(optionalOpenTime);

        assertEquals(responseEntity.getBody(), openTimeController.replaceOpenTime(
                time, t1.getId(), uriComponentsBuilder).getBody());
    }

    @Test
    public void testDeleteOpenTime() {
        List<OpenTime> actualList = new ArrayList<OpenTime>(List.of(t1,t3));
        List<OpenTime> expectedList = new ArrayList<OpenTime>(List.of(t1,t2,t3));

        Optional<OpenTime> optionalOpenTime = Optional.of(t2);
        ResponseEntity<OpenTime> openTimeResponseEntity = ResponseEntity.of(optionalOpenTime);

        openTimeController.deleteOpenTime(t2.getId());

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(1);
                return null;
            }
        }).when(openTimeRepository).deleteById(t2.getId());
        when(openTimeRepository.findAll()).thenReturn(expectedList);

        assertEquals(expectedList, openTimeController.getOpenTimes());
    }
}