package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Dish_Order")
public class DishOrder {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "amount", nullable = false)
    private int amount;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "dish_id", referencedColumnName = "id", nullable = false)
    private RestaurantDish dish;

    public DishOrder(){

    }

    /**
     * Constructor for the DishOrder class.
     *
     * @param amount - Amount of a certain dish
     * @param order - The id of the order that the dish is part of
     * @param dish - The id of the dish that is included in the order
     */
    public DishOrder(int amount,
                     Order order,
                     RestaurantDish dish) {
        this.amount = amount;
        this.order = order;
        this.dish = dish;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public RestaurantDish getDish() {
        return dish;
    }

    public void setDish(RestaurantDish dish) {
        this.dish = dish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DishOrder dishOrder = (DishOrder) o;
        return id == dishOrder.id;
    }
}
