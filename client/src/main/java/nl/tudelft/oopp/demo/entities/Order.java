package nl.tudelft.oopp.demo.entities;

import java.util.Set;

public class Order {

    private long id;
    private RoomReservation roomReservation;
    private Set<DishOrder> dishOrders;

    /**
     * Constructor for the Order object.
     *
     * @param roomReservation - The room reservation to which the order is linked
     */

    public Order(RoomReservation roomReservation) {
        this.roomReservation = roomReservation;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoomReservation getRoomReservation() {
        return roomReservation;
    }

    public void setRoomReservation(RoomReservation roomReservation) {
        this.roomReservation = roomReservation;
    }

    public Set<DishOrder> getDishOrders() {
        return dishOrders;
    }

    public void setDishOrders(Set<DishOrder> dishOrders) {
        this.dishOrders = dishOrders;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return id == order.id;
    }
}
