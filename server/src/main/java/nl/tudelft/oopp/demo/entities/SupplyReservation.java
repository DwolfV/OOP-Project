package nl.tudelft.oopp.demo.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Supply_Reservation")
public class SupplyReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "date")
    private LocalDate date;


    @Column(name = "amount")
    private int amount;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "supply_id", referencedColumnName = "id")
    private Supply supply;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public SupplyReservation() {

    }

    /**
     * Creates a new instance of the SupplyReservation entity.
     * @param date - The date that corresponds to the reservation of a supply.
     * @param amount - The amount of the supplies that are rented.
     * @param supply - The name of the supply that is being reserved.
     * @param user - The user that made the reservation
     */

    public SupplyReservation(LocalDate date,
                             int amount,
                             Supply supply,
                             User user) {
        this.date = date;
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
