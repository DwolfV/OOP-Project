package nl.tudelft.oopp.demo.entities;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private Long id;
    private String name;
    private List<Equipment> equipment;

    public Item() {

    }

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, ArrayList<Equipment> equipment) {
        this.name = name;
        this.equipment = equipment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Equipment> getEquipmentList() {
        return equipment;
    }

    public void setEquipmentList(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", equipmentList=" + equipment
                + '}';
    }
}
