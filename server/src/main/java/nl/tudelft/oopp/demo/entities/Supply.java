package nl.tudelft.oopp.demo.entities;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Supply",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"name", "building_id"},
                        name = "unique_supply_per_building_constraint"
                )
        })
public class Supply {

    @Id
    @Column(name = "id")
    private long id;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private Building building;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @OneToMany(mappedBy = "supply", cascade = CascadeType.ALL)
    private Set<SupplyReservation> reservations;

    @NotBlank
    @Column(name = "stock")
    private int stock;

    public Supply(){

    }

    /**
     * Create a new Supply instance.
     *
     * @param id Unique identifier as to be used in the database.
     * @param building Reference to the Building class.
     * @param name The actual name of the supply.
     * @param stock The amount of supplies of a certain type that are available at the moment.
     */

    public Supply(long id,
                  Building building,
                  String name,
                  int stock) {
        this.id = id;
        this.building = building;
        this.name = name;
        this.stock = stock;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Building getBuildingId() {
        return building;
    }

    public void setBuildingId(Building buildingId) {
        this.building = buildingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Set<SupplyReservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<SupplyReservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Supply that = (Supply) o;
        return id == that.id;
    }

    @Override
    public String toString() {
        return "BuildingSupply{"
                + "id=" + id
                + ", buildingId=" + building
                + ", name=" + name
                + ", stock=" + stock
                + '}';
    }
}
