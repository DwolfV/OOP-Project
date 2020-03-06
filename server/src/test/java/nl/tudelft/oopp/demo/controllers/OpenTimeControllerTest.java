package nl.tudelft.oopp.demo.controllers;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
    public void save(){

        Building b1 = new Building(1, "name1", "s1", "sNo1", "z1", "c1");
        Building b2 = new Building(2, "name2", "s1", "sNo2", "z2", "c1");
        Building b3 = new Building(3, "name3", "s1", "sNo3", "z3", "c1");

        t1 = new OpenTime(1, "d", new Time(1), new Time(2), b1, 1);
        t2 = new OpenTime(2, "Monday", new Time(1), new Time(3), b2,2);
        t3 = new OpenTime(3, "Friday", new Time(4), new Time(5), b3, 3);

    }

    /**
     * test if the controllers were loaded correctly and if they are not null.
     * Otherwise, @throws Exception
     */

    @Test
    public void controller() throws Exception {
        assertThat(openTimeController).isNotNull();
    }

    @Test
    public void getOpenTime() {
        List<OpenTime> expectedList = new ArrayList<OpenTime>(List.of(t1,t2,t3));
        when(openTimeRepository.findAll()).thenReturn(expectedList);
        List<OpenTime> actualList = openTimeController.getOpenTimes();

        assertEquals(actualList, expectedList);
    }

    @Test
    public void getById() {
        Optional<OpenTime> optionalOpenTime = Optional.of(t1);
        ResponseEntity<OpenTime> entity = ResponseEntity.of(optionalOpenTime);

        when(openTimeRepository.findById(1L)).thenReturn(optionalOpenTime);
        assertEquals(entity, openTimeController.getOpenTimeById(1L));

    }

    @Test
    public void newOpenTime() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building(1, "name1", "s1", "sNo1", "z1", "c1");
        OpenTime time = new OpenTime(1, "d", new Time(1), new Time(2), b1, 1);
        Optional<OpenTime> optionalOpenTime = Optional.of(time);
        ResponseEntity<OpenTime> responseEntity = ResponseEntity.of(optionalOpenTime);

        when(openTimeRepository.save(time)).thenReturn(time);

        assertEquals(time, openTimeController.newOpenTime(time, uriComponentsBuilder).getBody());
    }

    @Test
    public void update() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Building b1 = new Building(1, "name1", "s1", "sNo1", "z1", "c1");
        OpenTime time = new OpenTime(1, "d", new Time(1), new Time(2), b1, 1);
        Optional<OpenTime> optionalOpenTime = Optional.of(t1);

        ResponseEntity<OpenTime> entity = ResponseEntity.of(optionalOpenTime);
        Optional<OpenTime> newO = Optional.of(time);
        ResponseEntity<OpenTime> responseEntity = ResponseEntity.of(newO);

        when(openTimeRepository.save(time)).thenReturn(time);
        when(openTimeRepository.findById(1L)).thenReturn(optionalOpenTime);

        assertEquals(responseEntity.getBody(), openTimeController.replaceOpenTime(time, 1, uriComponentsBuilder).getBody());
    }

    @Test
    public void delete() {
        List<OpenTime> actualList = new ArrayList<OpenTime>(List.of(t1,t3));
        List<OpenTime> expectedList = new ArrayList<OpenTime>(List.of(t1,t2,t3));

        Optional<OpenTime> optionalOpenTime = Optional.of(t2);
        ResponseEntity<OpenTime> openTimeResponseEntity = ResponseEntity.of(optionalOpenTime);

        openTimeController.deleteOpenTime(2L);

         Mockito.doAnswer(new Answer<Void>() {
             @Override
             public Void answer(InvocationOnMock invocation) {
                 actualList.remove(1);
                 return null;
             }
         }).when(openTimeRepository).deleteById(2L);
         when(openTimeRepository.findAll()).thenReturn(expectedList);

         assertEquals(expectedList, openTimeController.getOpenTimes());
    }
}