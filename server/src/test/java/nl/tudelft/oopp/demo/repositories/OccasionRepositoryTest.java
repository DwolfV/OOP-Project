package nl.tudelft.oopp.demo.repositories;


import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Occasion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class OccasionRepositoryTest {

    private Occasion o1;
    private Occasion o2;
    private Occasion o3;
    private Occasion o4;

    private Building b1;
    private Building b2;

    @Autowired
    private OccasionRepository occasionRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @BeforeEach
    public void save() {
        b1 = new Building("name1", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s1", "sNo1", "z1", "c1");
        b2 = new Building("name2", LocalTime.parse("08:00"), LocalTime.parse("20:00"),"s2", "sNo2", "z2", "c2");
        buildingRepository.save(b1);
        buildingRepository.save(b2);

        o1 = new Occasion(LocalDate.parse("2020-03-24"), LocalTime.parse("10:00"), LocalTime.parse("18:00"), b1);
        o2 = new Occasion(LocalDate.parse("2020-02-24"), LocalTime.parse("09:00"), LocalTime.parse("13:00"), b1);
        o3 = new Occasion(LocalDate.parse("2020-01-12"), LocalTime.parse("12:00"), LocalTime.parse("22:00"), b2);
        o4 = new Occasion(LocalDate.parse("2020-07-02"), LocalTime.parse("17:00"), LocalTime.parse("23:00"), b2);
        occasionRepository.save(o1);
        occasionRepository.save(o2);
        occasionRepository.save(o3);
        occasionRepository.save(o4);
    }

    @Test
    public void testLoadRepository() {
        assertThat(buildingRepository).isNotNull();
        assertThat(occasionRepository).isNotNull();
    }

    @Test
    public void testFindById() {
        assertEquals(Optional.of(o1), occasionRepository.findById(o1.getId()));
    }

    @Test
    public void testFindByBuildingId() {
        List<Occasion> b1List = new ArrayList<>(List.of(o1,o2));
        assertEquals(b1List, occasionRepository.findByBuildingId(b1.getId()));

        List<Occasion> b2List = new ArrayList<>(List.of(o3, o4));
        assertEquals(b2List, occasionRepository.findByBuildingId(b2.getId()));
    }
}
