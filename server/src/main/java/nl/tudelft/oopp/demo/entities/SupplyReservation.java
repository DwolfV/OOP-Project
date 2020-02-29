package nl.tudelft.oopp.demo.entities;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Supply_Reservation")
public class SupplyReservation {

    @Id
    @Column(name = "id")
    private long id;

    @NotBlank
    @Column(name = "date")
    private Date date;

    @NotBlank
    @Column(name = "amount")
    private int amount;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "supply_id", referencedColumnName = "id")
    private Supply supply;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public SupplyReservation() {

    }

    /**
     * Create a new SupplyReservation instance.
     *
     * @param id Unique identifier as to be used in the database.
     * @param user Reference to the User class.
     * @param supply Reference to the User class.
     * @param amount The amount of supplies that are being reserved.
     * @param date The date of the reservation
     */

    public SupplyReservation(long id,
                             Date date,
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
        return user;
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
