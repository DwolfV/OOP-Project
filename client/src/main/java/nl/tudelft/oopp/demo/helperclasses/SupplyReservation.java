package nl.tudelft.oopp.demo.helperclasses;

import java.time.LocalDate;
import java.time.LocalTime;

public class SupplyReservation {

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private long id;
    private int amount;
    private Supply supply;
    private User user;

    public SupplyReservation() {

    }

    /**
     * Creates a new instance of the SupplyReservation entity.
     * @param date - The date that corresponds to the reservation of a supply.
     * @param startTime - Since when the reservation for the supply has been made.
     * @param endTime - Until when the reservation for the supply is made.
     * @param amount - The amount of the supplies that are rented.
     * @param supply - The name of the supply that is being reserved.
     * @param user - The user that made the reservation
     */

    public SupplyReservation(LocalDate date,
                             LocalTime startTime,
                             LocalTime endTime,
                             int amount,
                             Supply supply,
                             User user) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.amount = amount;
        this.supply = supply;
        this.user = user;
    }

    /**
     * Create a new SupplyReservation instance.
     *
     * @param id     Unique identifier as to be used in the database.
     * @param user   Reference to the User class.
     * @param supply Reference to the User class.
     * @param amount The amount of supplies that are being reserved.
     * @param date   The date of the reservation
     */

    public SupplyReservation(long id,
                             LocalDate date,
                             LocalTime startTime,
                             LocalTime endTime,
                             int amount,
                             Supply supply,
                             User user) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.amount = amount;
        this.supply = supply;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Supply getSupply() {
        return supply;
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SupplyReservation that = (SupplyReservation) o;
        return id == that.id;
    }

    @Override
    public String toString() {
        return "SupplyReservation{"
                + "id=" + id
                + ", date=" + date
                + ", amount=" + amount
                + ", supply=" + supply
                + ", user=" + user
                + '}';
    }
}
