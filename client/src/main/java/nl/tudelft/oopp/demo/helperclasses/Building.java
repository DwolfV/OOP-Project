package nl.tudelft.oopp.demo.helperclasses;

import java.sql.Time;
import java.util.List;

public class Building {

    String name;
    String location;
    List<Room> roomList;
    List<Restaurant> restaurantList;
    List<Accessory> accessoryList;
    Time openT;
    Time closeT;

    /**
     * Create a new Building instance.
     *
     * @param name Name of the Building.
     * @param location The address of the building.
     * @param roomList A List of all Rooms in that Building.
     * @param restaurantList A List of all Restaurants in that Building.
     * @param accessoryList A List of all Accessories that the Building has.
     * @param openT Time at which the Building opens.
     * @param closeT Time at which the Building closes.
     */

    public Building(String name,
                    String location,
                    List<Room> roomList,
                    List<Restaurant> restaurantList,
                    List<Accessory> accessoryList,
                    Time openT,
                    Time closeT) {
        this.name = name;
        this.location = location;
        this.roomList = roomList;
        this.restaurantList = restaurantList;
        this.accessoryList = accessoryList;
        this.openT = openT;
        this.closeT = closeT;
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
        // TODO calculate if the Building is open at that time.
        return false;
    }

}
