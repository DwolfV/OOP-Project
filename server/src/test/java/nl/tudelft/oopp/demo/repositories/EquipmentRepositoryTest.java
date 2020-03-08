package nl.tudelft.oopp.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Equipment;
import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class EquipmentRepositoryTest {

    private Equipment e1;
    private Equipment e2;
    private Equipment e3;
    private Equipment e4;
    private Equipment e5;

    private Room r1;
    private Room r2;

    private Building b1;
    private Building b2;

    @Autowired
    private EquipmentRepository equipRep;

    @Autowired
    private RoomRepository roomRep;

    @Autowired
    private BuildingRepository buildRep;

    /**
     * Creates all rooms, buildings and equipment
     * and is done before each test.
     */
    @BeforeEach
    public void save() {
        b1 = new Building("build1", "s1", "sNo1", "z1", "c1");
        b2 = new Building("build2", "s2", "sNo2", "z2", "c2");
        buildRep.save(b1);
        buildRep.save(b2);

        r1 = new Room("room1", 5, b1);
        r2 = new Room("room2", 6, b2);
        roomRep.save(r1);
        roomRep.save(r2);

        e1 = new Equipment(r1, "equip1", 1);
        e2 = new Equipment(r1, "equip2", 2);
        e3 = new Equipment(r2, "equip3", 3);
        e4 = new Equipment(r2, "equip4", 4);
        e5 = new Equipment(r2, "equip5", 5);
        equipRep.save(e1);
        equipRep.save(e2);
        equipRep.save(e3);
        equipRep.save(e4);
        equipRep.save(e5);
    }

    /**
     * Test if the repositories loads correctly and isn't null.
     *
     * @throws Exception exception
     */
    @Test
    public void testLoadRepository() {
        assertThat(buildRep).isNotNull();
        assertThat(roomRep).isNotNull();
        assertThat(equipRep).isNotNull();
    }

    /**
     * Checks whether findAll() gets a list of all the equipment.
     */
    @Test
    public void testFindAll() {
        List<Equipment> expectedList = new ArrayList<Equipment>(List.of(e1, e2, e3, e4, e5));
        List<Equipment> actualList = equipRep.findAll();
        assertEquals(expectedList, actualList);
    }

    /**
     * Checks whether findById() gets a list of all the equipment with that id.
     */
    @Test
    public void testFindById() {
        long id = e1.getId();
        Optional<Equipment> optionalE1 = Optional.of(e1);
        assertEquals(optionalE1, equipRep.findById(id));
    }

    /**
     * Checks whether findByName() gets a list of all the equipment with that name.
     */
    @Test
    public void testFindByName() {
        String name = e1.getName();
        List<Equipment> listE1 = new ArrayList<Equipment>(List.of(e1));
        assertEquals(listE1, equipRep.findByName(name));
    }

    /**
     * Checks whether findByRoomId() gets a list of all the equipment with that room id.
     */
    @Test
    public void testFindByRoomId() {
        List<Equipment> fullList = new ArrayList<Equipment>(List.of(e1, e2, e3, e4, e5));
        List<Equipment> equipmentList1 = new ArrayList<Equipment>(List.of(e1, e2));
        List<Equipment> equipmentList2 = new ArrayList<Equipment>(List.of(e3, e4, e5));
        assertEquals(equipmentList1,
                equipRep.findByRoomId(equipmentList1.get(0).getRoom().getId()));
        assertEquals(equipmentList2,
                equipRep.findByRoomId(equipmentList2.get(0).getRoom().getId()));
        assertEquals(fullList.size(), equipmentList1.size() + equipmentList2.size());
    }
}
