package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Holiday;
import nl.tudelft.oopp.demo.repositories.HolidayRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


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
     * Creates all holidays before each test
     */

    @BeforeEach
    public void save(){


    }


    @Test
    public void controllerLoads() throws Exception {
        assertThat(holidayController).isNotNull();
    }

    @Test
    void getAllHolidays() {
    }

    @Test
    void newHoliday() {
    }

    @Test
    void getHolidayById() {
    }

    @Test
    void replaceHoliday() {
    }

    @Test
    void deleteHoliday() {
    }
}