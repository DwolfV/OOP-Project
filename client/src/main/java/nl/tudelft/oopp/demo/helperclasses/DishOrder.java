package nl.tudelft.oopp.demo.helperclasses;

public class DishOrder {

    private Long id;
    private int amount;
    private Order order;
    private RestaurantDish restaurantDish;

    /**
     * Constructor for the DishOrder class.
     *
     * @param amount - Amount of a certain dish
     * @param order - The id of the order that the dish is part of
     * @param restaurantDish - The id of the dish that is included in the order
     */
    public DishOrder(int amount,
                     Order order,
                     RestaurantDish restaurantDish) {
        this.amount = amount;
        this.order = order;
        this.restaurantDish = restaurantDish;
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
        return restaurantDish;
    }

    public void setDish(RestaurantDish dish) {
        this.restaurantDish = restaurantDish;
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

    @Override
    public String toString() {
        return "DishOrder{"
            + "id=" + id
            + ", amount=" + amount
            + ", order=" + order
            + ", restaurantDish=" + restaurantDish
            + '}';
    }
}
