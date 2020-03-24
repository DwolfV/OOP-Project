package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @NotNull
    @Column(name = "open_time")
    private LocalTime openTime;

    @NotNull
    @Column(name = "close_time")
    private LocalTime closeTime;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    @Nullable
    private List<Supply> supplies = new ArrayList<>();

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    @Nullable
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    @Nullable
    private Set<Restaurant> restaurants = new HashSet<>();

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    @Nullable
    private List<Occasion> occasions = new ArrayList<>();

    public Building() {

    }

    /**
     * Creates a new instance of the Building entity.
     *
     * @param name The name of the building
     * @param openTime The time at which the building generally opens
     * @param closeTime The time at which the building usually closes
     * @param streetName - The name of the street on which the Building is located
     * @param streetNumber - The number of the street on which the Building is located
     * @param zipCode - The zip code of the Building address
     * @param city - The city in which the Building is located
     */
    public Building(String name,
                    LocalTime openTime,
                    LocalTime closeTime,
                    String streetName,
                    String streetNumber,
                    String zipCode,
                    String city) {
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
    }

    /**
     * Create a new Building instance.
     *
     * @param id Unique identifier as to be used in the database.
     * @param name Actual name of the Building.
     * @param openTime The time at which the building generally opens
     * @param closeTime The time at which the building usually closes
     * @param streetName The street on which the Building is located.
     * @param streetNumber The street number on which the Building is located.
     * @param zipCode The zip code of the Building.
     * @param city The city in which the Building is located.
     */

    public Building(long id,
                    String name,
                    LocalTime openTime,
                    LocalTime closeTime,
                    String streetName,
                    String streetNumber,
                    String zipCode,
                    String city) {
        this.id = id;
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
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

    @JsonIgnore
    @Nullable
    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(@Nullable List<Room> rooms) {
        this.rooms = rooms;
    }

    @JsonIgnore
    @Nullable
    public List<Occasion> getOccasions() {
        return occasions;
    }

    public void setOccasions(@Nullable List<Occasion> occasions) {
        this.occasions = occasions;
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
                + ", openTime='" + openTime + '\''
                + ", closeTime='" + closeTime + '\''
                + ", street='" + streetName + '\''
                + ", streetNumber='" + streetNumber + '\''
                + ", zipCode='" + zipCode + '\''
                + ", city='" + city + '\''
                + '}';
    }
}
