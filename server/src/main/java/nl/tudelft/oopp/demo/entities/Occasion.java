package nl.tudelft.oopp.demo.entities;

import com.sun.istack.NotNull;

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

@Entity
@Table(name = "Occasion")
public class Occasion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @NotNull
    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id", nullable = false)
    private Building building;

    public Occasion() {

    }

    /**
     * Create a new Occasion.
     *
     * @param date      The date on which the occasion takes place
     * @param openTime  The time at which the building affected by the occasion opens
     * @param closeTime The time at which the building affected by the occasion closes
     * @param building  The building which is affected by the occasion
     */
    public Occasion(LocalDate date,
                    LocalTime openTime,
                    LocalTime closeTime,
                    Building building) {
        this.date = date;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.building = building;
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

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Occasion occasion = (Occasion) o;
        return id == occasion.id;
    }

    @Override
    public String toString() {
        return "Occasion{"
                + "id=" + id
                + ", date=" + date
                + ", openTime=" + openTime
                + ", closeTime=" + closeTime
                + ", building=" + building
                + '}';
    }
}


