package nl.tudelft.oopp.demo.helperclasses;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private Long id;
    private String name;
    private List<Equipment> equipmentList;

    public Item() {

    }

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, ArrayList<Equipment> equipmentList) {
        this.name = name;
        this.equipmentList = equipmentList;
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
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", equipmentList=" + equipmentList
                + '}';
    }
}
