package nl.tudelft.oopp.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Equipment;
import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RoomRepositoryTest {

    private Room r1;
    private Room r2;
    private Room r3;
    private Room r4;
    private Room r5;

    @Autowired
    private RoomRepository roomRep;

    @Autowired
    private BuildingRepository buildRep;

    /**
     * Creates all rooms and is done before each test.
     */
    @BeforeEach
    public void save() {
        Building b1 = new Building("b1", "s1", "sNo1", "z1", "c1");
        r1 = new Room("room1", 1, b1);
        r2 = new Room("room2", 2, b1);
        r3 = new Room("room3", 3, b1);
        Building b2 = new Building("b2", "s2", "sNo2", "z2", "c2");
        r4 = new Room("room4", 4, b2);
        r5 = new Room("room5", 5, b2);
        buildRep.save(b1);
        buildRep.save(b2);
        roomRep.save(r1);
        roomRep.save(r2);
        roomRep.save(r3);
        roomRep.save(r4);
        roomRep.save(r5);
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
    }

    /**
     * Checks whether findAll() gets a list of all the rooms.
     */
    @Test
    public void testFindAll() {
        List<Room> fullList = new ArrayList<Room>(List.of(r1, r2, r3, r4, r5));
        assertEquals(fullList, roomRep.findAll());
    }

    /**
     * Checks whether findRoomsByBuildingId() in the repository
     * finds all the rooms from a certain building.
     */
    @Test
    public void testFindByBuilding() {
        List<Room> l1 = new ArrayList<Room>(List.of(r1, r2, r3));
        assertEquals(l1, roomRep.findByBuildingId(l1.get(0).getBuilding().getId()));

        List<Room> l2 = new ArrayList<Room>(List.of(r4, r5));
        assertEquals(l2, roomRep.findByBuildingId(l2.get(0).getBuilding().getId()));
    }

    /**
     * Checks whether findRoomsByBuildingId() in the repository
     * finds all the rooms from a certain building.
     */
    @Test
    public void testFindByBuildingId() {
        List<Room> fullList = new ArrayList<Room>(List.of(r1, r2, r3, r4, r5));

        List<Room> b1List = new ArrayList<Room>(List.of(r1, r2, r3));
        assertEquals(b1List,
                roomRep.findByBuildingId(b1List.get(0).getBuilding().getId()));

        List<Room> b2List = new ArrayList<Room>(List.of(r4, r5));
        assertEquals(b2List,
                roomRep.findByBuildingId(b2List.get(0).getBuilding().getId()));

        assertEquals(fullList.size(), b1List.size() + b2List.size());
    }

    /**
     * Checks whether findByName() actually returns the room(s) with that name.
     */
    @Test
    public void testFindByName() {
        List<Room> fullList = roomRep.findAll();
        assertTrue(fullList.size() == 5);          // Make sure fullList has 5 objects
        List<Room> room1 = new ArrayList<Room>(List.of(fullList.get(0)));
        assertEquals(room1, roomRep.findByName("room1"));
        List<Room> room2 = new ArrayList<Room>(List.of(fullList.get(1)));
        assertEquals(room2, roomRep.findByName("room2"));

        List<Room> emptyList = new ArrayList<Room>();
        assertEquals(emptyList, roomRep.findByName("non_existent"));
        assertNotEquals(room1, roomRep.findByName("room5"));
        assertNotEquals(room2, roomRep.findByName("room3"));
        assertNotEquals(room2, emptyList);
    }

}
