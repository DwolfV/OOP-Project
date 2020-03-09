package nl.tudelft.oopp.demo.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Holiday;
import nl.tudelft.oopp.demo.repositories.HolidayRepository;
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
class HolidayControllerTest {
    private Holiday h1;
    private Holiday h2;
    private Holiday h3;

    @Mock
    private HolidayRepository holidayRepository;

    @InjectMocks
    private HolidayController holidayController;

    /**
     * Creates all holidays before each test.
     */
    @BeforeEach
    public void save() {
        h1 = new Holiday("h1", new Date(1), new Time(1), new Time(2));
        h2 = new Holiday("h2", new Date(2), new Time(3), new Time(2));
        h3 = new Holiday("h3", new Date(3), new Time(1), new Time(4));
    }

    /**
     * test if the controllers were loaded correctly and if they are not null.
     * Otherwise, @throws Exception
     */
    @Test
    public void testLoadController() throws Exception {
        assertThat(holidayController).isNotNull();
    }

    @Test
    void testGetAll() {
        List<Holiday> expectedList = new ArrayList<Holiday>(List.of(h1, h2, h3));
        when(holidayRepository.findAll()).thenReturn(expectedList);
        List<Holiday> actualList = holidayController.getAllHolidays();

        assertEquals(expectedList, actualList);
    }

    @Test
    void testNewHoliday() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Holiday holiday = new Holiday("h1", new Date(1), new Time(1), new Time(2));
        Optional<Holiday> optionalHoliday = Optional.of(holiday);
        ResponseEntity<Holiday> responseEntity = ResponseEntity.of(optionalHoliday);

        when((holidayRepository.save(holiday))).thenReturn(holiday);

        assertEquals(holiday,
                holidayController.newHoliday(holiday, uriComponentsBuilder).getBody());
    }

    @Test
    void testGetHolidayById() {
        Optional<Holiday> optionalHoliday = Optional.of(h1);
        ResponseEntity<Holiday> entity = ResponseEntity.of(optionalHoliday);

        when(holidayRepository.findById(h1.getId())).thenReturn(optionalHoliday);
        assertEquals(entity, holidayController.getHolidayById(h1.getId()));
    }

    @Test
    void testReplaceHoliday() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Holiday holiday = new Holiday("h1", new Date(1), new Time(1), new Time(2));
        Optional<Holiday> optionalHoliday = Optional.of(h1);

        ResponseEntity<Holiday> entity = ResponseEntity.of(optionalHoliday);
        Optional<Holiday> newH = Optional.of(holiday);
        ResponseEntity<Holiday> responseEntity = ResponseEntity.of(newH);

        when(holidayRepository.save(holiday)).thenReturn(holiday);
        when(holidayRepository.findById(h1.getId())).thenReturn(optionalHoliday);

        assertEquals(responseEntity.getBody(), holidayController.replaceHoliday(
                holiday, h1.getId(), uriComponentsBuilder).getBody());
    }

    @Test
    void testDeleteHoliday() {
        List<Holiday> actualList = new ArrayList<Holiday>(List.of(h1, h3));
        List<Holiday> expectedList = new ArrayList<Holiday>(List.of(h1, h2, h3));

        Optional<Holiday> optionalHoliday = Optional.of(h2);
        ResponseEntity<Holiday> holidayResponseEntity = ResponseEntity.of(optionalHoliday);

        holidayController.deleteHoliday(h2.getId());
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(1);
                return null;
            }
        }).when(holidayRepository).deleteById(h2.getId());
        when(holidayRepository.findAll()).thenReturn(expectedList);
        assertEquals(expectedList, holidayController.getAllHolidays());
    }
}