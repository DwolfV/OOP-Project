package nl.tudelft.oopp.demo.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RoomRepositoryTest {

    private List<Room> r1;
    private List<Room> r2;
    private List<Room> r3;
    private List<Room> r4;
    private List<Room> r5;

    @Autowired
    private RoomRepository roomRep;

    @Autowired
    private BuildingRepository buildRep;

    /**
     * Creates all rooms and is done before each test.
     */
    @BeforeEach
    public void save() {
        Building b1 = new Building(1, "b1", "s1", "sNo1", "z1", "c1");
        r1 = new ArrayList<Room>(List.of(new Room(1, "room1", 1, b1)));
        r2 = new ArrayList<Room>(List.of(new Room(2, "room2", 2, b1)));
        r3 = new ArrayList<Room>(List.of(new Room(3, "room3", 3, b1)));
        Building b2 = new Building(2, "b2", "s2", "sNo2", "z2", "c2");
        r4 = new ArrayList<Room>(List.of(new Room(4, "room4", 4, b2)));
        r5 = new ArrayList<Room>(List.of(new Room(5, "room5", 5, b2)));
        buildRep.save(b1);
        buildRep.save(b2);
        roomRep.saveAll(r1);
        roomRep.saveAll(r2);
        roomRep.saveAll(r3);
        roomRep.saveAll(r4);
        roomRep.saveAll(r5);
    }

    /**
     * Checks whether findAll() gets a list of all the rooms.
     */
    @Test
    public void getAllRooms() {
        List<Room> fullList = new ArrayList<Room>();
        fullList.addAll(r1);
        fullList.addAll(r2);
        fullList.addAll(r3);
        fullList.addAll(r4);
        fullList.addAll(r5);
        assertEquals(fullList, roomRep.findAll());
    }

    /**
     * Checks whether findRoomsByBuildingId() in the repository
     * finds all the rooms from a certain building.
     */
    @Test
    public void getRoomsByBuildingId() {
        List<Room> l1 = new ArrayList<Room>();
        l1.addAll(r1);
        l1.addAll(r2);
        l1.addAll(r3);
        List<Room> el1 = roomRep.findByBuildingId(1);
        List<Room> l2 = new ArrayList<Room>();
        l2.addAll(r4);
        l2.addAll(r5);
        List<Room> el2 = roomRep.findByBuildingId(2);
        assertEquals(l1.size(), el1.size());
        assertEquals(l2.size(), el2.size());
    }

    /**
     * Checks whether findRoomsByBuildingId() in the repository
     * finds all the rooms from a certain building.
     */
    @Test
    public void getRoomsByBuildingIdCheckAll() {
        List<Room> totalList = roomRep.findAll();
        List<Room> b1List = roomRep.findByBuildingId(1);
        List<Room> b2List = roomRep.findByBuildingId(2);
        for (int i = 0; i < totalList.size(); i++) {
            Room tempRoom = totalList.get(1);
            if (tempRoom.getBuilding().getId() == 1) {
                assertTrue(b1List.contains(tempRoom));
            } else if (tempRoom.getBuilding().getId() == 2) {
                assertTrue(b2List.contains(tempRoom));
            } else {
                fail("Building should not exist");
            }
        }
    }

    /**
     * Checks whether findByName() actually returns the room(s) with that name.
     */
    @Test
    public void getRoomByName() {
        List<Room> fullList = roomRep.findAll();
        assertTrue(fullList.size() == 5);          // Make sure fullList has 5 objects
        List<Room> room1 = new ArrayList<Room>(List.of(fullList.get(0)));
        assertEquals(room1, roomRep.findByName("room1"));
        List<Room> room2 = new ArrayList<Room>(List.of(fullList.get(1)));
        assertEquals(room2, roomRep.findByName("room2"));
        List<Room> room3 = new ArrayList<Room>(List.of(fullList.get(2)));
        assertEquals(room3, roomRep.findByName("room3"));
        List<Room> room4 = new ArrayList<Room>(List.of(fullList.get(3)));
        assertEquals(room4, roomRep.findByName("room4"));
        List<Room> room5 = new ArrayList<Room>(List.of(fullList.get(4)));
        assertEquals(room5, roomRep.findByName("room5"));

        List<Room> emptyList = new ArrayList<Room>();
        assertEquals(emptyList, roomRep.findByName("non_existent"));
        assertNotEquals(room1, roomRep.findByName("room5"));
        assertNotEquals(room2, roomRep.findByName("room3"));
        assertNotEquals(room2, emptyList);
    }

}
