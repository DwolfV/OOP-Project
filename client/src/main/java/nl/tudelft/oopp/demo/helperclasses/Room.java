package nl.tudelft.oopp.demo.helperclasses;

public class Room {

    String name;
    int capacity;
    Equipment equipment;

    public Room(String name, int capacity, Equipment equipment) {
        this.name = name;
        this.capacity = capacity;
        this.equipment = equipment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
