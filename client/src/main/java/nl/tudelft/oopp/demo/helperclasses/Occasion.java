package nl.tudelft.oopp.demo.helperclasses;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Occasion {

    private Long id;
    private LocalDate date;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Building building;

    /**
     * Create a new Occasion instance.
     *
     * @param date      The date on which the event takes place
     * @param openTime  The time the building affected by the event opens
     * @param closeTime The time the building affected by the event closes
     * @param building  The building affected by the event
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return Objects.equals(id, occasion.id);
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
