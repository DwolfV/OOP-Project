package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
public class RoomRepositoryTest {

    List<Room> r1;
    List<Room> r2;
    List<Room> r3;
    List<Room> r4;
    List<Room> r5;

    @Autowired
    private RoomRepository roomRep;

    @Autowired
    private BuildingRepository buildRep;

    @BeforeEach
    public void save() {
        r1 = new ArrayList<Room>();
        r2 = new ArrayList<Room>();
        r3 = new ArrayList<Room>();
        r4 = new ArrayList<Room>();
        r5 = new ArrayList<Room>();
        Building b1 = new Building(1, "b1", "s1", "sNo1", "z1", "c1");
        r1.add(new Room(1, "room1", 1, b1));
        r1.add(new Room(2, "room2", 2, b1));
        r1.add(new Room(3, "room3", 3, b1));
        Building b2 = new Building(2, "b2", "s2", "sNo2", "z2", "c2");
        r1.add(new Room(4, "room4", 4, b2));
        r1.add(new Room(5, "room5", 5, b2));
        buildRep.save(b1);
        buildRep.save(b2);
        roomRep.save(r1.get(0));
        roomRep.save(r2.get(0));
        roomRep.save(r3.get(0));
        roomRep.save(r4.get(0));
        roomRep.save(r5.get(0));
    }

    @Test
    public void findRoomsByBuildingName() {
        List<Room> l1 = new ArrayList<Room>();
        l1.addAll(r1);
        l1.addAll(r2);
        l1.addAll(r3);
        List<Room> l2 = new ArrayList<Room>();
        l1.addAll(r4);
        l1.addAll(r5);
        assertEquals(l1, roomRep.findByBuildingId(1));
        assertEquals(l2, roomRep.findByBuildingId(2));
    }

    @Test
    public void findRoomByName() {
        assertEquals(r1, roomRep.findByName("room1"));
        assertEquals(r2, roomRep.findByName("room2"));
        assertEquals(r3, roomRep.findByName("room3"));
        assertEquals(r4, roomRep.findByName("room4"));
        assertEquals(r5, roomRep.findByName("room5"));
        assertEquals(null, roomRep.findByName("non_existent"));
        assertNotEquals(r1, roomRep.findByName("room5"));
        assertNotEquals(r2, roomRep.findByName("room3"));
        assertNotEquals(r2, null);
    }

}
