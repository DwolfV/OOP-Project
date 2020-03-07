package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "Dish_Order")
public class DishOrder {
    @Id
    @Column(name = "id")
    private long id;

    @NotBlank
    @Column(name = "amount", nullable = false)
    private int amount;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "dish_id", referencedColumnName = "id", nullable = false)
    private RestaurantDish dish;

    public DishOrder(){

    }

    /**
     * Constructor for the DishOrder class
     *
     * @param amount - Amount of a certain dish
     * @param order - The id of the order that the dish is part of
     * @param dish - The id of the dish that is included in the order
     */
    public DishOrder(int amount,
                     Order order,
                     RestaurantDish dish){
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
