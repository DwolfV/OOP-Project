package nl.tudelft.oopp.demo.helperclasses;

import java.sql.Time;
import java.util.List;

public class Building {

    String name, location;
    List<Room> roomList;
    List<Restaurant> restaurantList;
    List<Accessory> accessoryList;
    Time openT, closeT;
    boolean isOpen;

    public Building(String name, String location, List<Room> roomList, List<Restaurant> restaurantList, List<Accessory> accessoryList, Time openT, Time closeT, boolean isOpen) {
        this.name = name;
        this.location = location;
        this.roomList = roomList;
        this.restaurantList = restaurantList;
        this.accessoryList = accessoryList;
        this.openT = openT;
        this.closeT = closeT;
        this.isOpen = isOpen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public List<Accessory> getAccessoryList() {
        return accessoryList;
    }

    public void setAccessoryList(List<Accessory> accessoryList) {
        this.accessoryList = accessoryList;
    }

    public Time getOpenT() {
        return openT;
    }

    public void setOpenT(Time openT) {
        this.openT = openT;
    }

    public Time getCloseT() {
        return closeT;
    }

    public void setCloseT(Time closeT) {
        this.closeT = closeT;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
