package nl.tudelft.oopp.demo.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Holiday;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class HolidayRepositoryTest {

    private Holiday h1;
    private Holiday h2;
    private Holiday h3;
    private Holiday h4;
    private Holiday h5;

    @Autowired
    private HolidayRepository holidayRep;

    /**
     * Creates all supplies and is done before each test.
     */
    @BeforeEach
    public void save() {
        h1 = new Holiday("holiday1", new Date(1000), new Time(1000), new Time(1500));
        h2 = new Holiday("holiday2", new Date(2000), new Time(2000), new Time(2500));
        h3 = new Holiday("holiday3", new Date(3000), new Time(3000), new Time(3500));
        h4 = new Holiday("holiday4", new Date(4000), new Time(4000), new Time(4500));
        h5 = new Holiday("holiday5", new Date(5000), new Time(5000), new Time(5500));
        holidayRep.save(h1);
        holidayRep.save(h2);
        holidayRep.save(h3);
        holidayRep.save(h4);
        holidayRep.save(h5);
    }

    /**
     * Test if the repositories loads correctly and isn't null.
     *
     * @throws Exception exception
     */
    @Test
    public void testLoadRepository() throws Exception {
        assertNotNull(holidayRep);
    }

    /**
     * Tests if findAll() method from the repository works.
     */
    @Test
    public void testFindAll() {
        List<Holiday> fullList = new ArrayList<Holiday>(List.of(h1, h2, h3, h4, h5));
        assertEquals(fullList, holidayRep.findAll());
    }

    /**
     * Tests if findByName methods from the repository works
     * and if It gets the list of holidays with the same name.
     */
    @Test
    public void testFindByName() {
        // TODO fix holiday so it returns a list of holidays with the same name, not only one
        List<Holiday> h1List = new ArrayList<Holiday>(List.of(h1));
        assertEquals(h1List, holidayRep.findByName(h1.getName()));

        Holiday h4SameName = new Holiday("holiday4",
                new Date(6000), new Time(7000), new Time(8500));
        List<Holiday> h4List = new ArrayList<Holiday>(List.of(h4, h4SameName));
        assertEquals(h4List, holidayRep.findByName(h4.getName()));
    }

}
