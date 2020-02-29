package nl.tudelft.oopp.demo.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "Building",
    uniqueConstraints = {
            @UniqueConstraint(
                    columnNames = {"street_name", "street_number", "zip_code", "city"},
                    name = "unique_address_constraint"
            )
    })
public class Building {

    @Id
    @Column(name = "id")
    private long id;

    @NotBlank(message = "This field cannot be blank")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "This field cannot be blank")
    @Column(name = "street_name")
    private String streetName;

    @NotBlank(message = "This field cannot be blank")
    @Column(name = "street_number")
    private String streetNumber;

    @NotBlank(message = "This field cannot be blank")
    @Column(name = "zip_code")
    private String zipCode;

    @NotBlank(message = "This field cannot be blank")
    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    @Nullable
    private Set<Supply> supplies = new HashSet<>();

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    @Nullable
    private Set<Room> rooms = new HashSet<>();

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    @Nullable
    private Set<OpenTime> openTimes = new HashSet<>();

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    @Nullable
    private Set<Restaurant> restaurants = new HashSet<>();

    public Building() {

    }

    /**
     * Create a new Building instance.
     *
     * @param id Unique identifier as to be used in the database.
     * @param name Actual name of the Building.
     * @param streetName The street on which the Building is located.
     * @param streetNumber The street number on which the Building is located.
     * @param zipCode The zip code of the Building.
     * @param city The city in which the Building is located.
     */

    public Building(long id,
                    String name,
                    String streetName,
                    String streetNumber,
                    String zipCode,
                    String city) {
        this.id = id;
        this.name = name;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Building building = (Building) o;
        return Objects.equals(id, building.getId());
    }

    @Override
    public String toString() {
        return "Building{"
                + "buildingId='" + id + '\''
                + ", buildingName='" + name + '\''
                + ", street='" + streetName + '\''
                + ", streetNumber='" + streetNumber + '\''
                + ", zipCode='" + zipCode + '\''
                + ", city='" + city + '\''
                + '}';
    }
}
