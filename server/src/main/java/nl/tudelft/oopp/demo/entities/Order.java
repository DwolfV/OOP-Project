package nl.tudelft.oopp.demo.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "Order_Reservation")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    //@NotBlank
    @ManyToOne
    @JoinColumn(name = "room_reservation_id", referencedColumnName = "id", nullable = false)
    private RoomReservation roomReservation;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<DishOrder> dishOrders = new HashSet<>();

    public Order(){

    }

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
