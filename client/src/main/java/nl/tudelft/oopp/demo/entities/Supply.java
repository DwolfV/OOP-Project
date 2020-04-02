package nl.tudelft.oopp.demo.entities;

public class Supply {

    private long id;
    private Building building;
    private String name;
    private int stock;

    public Supply() {

    }

    /**
     * Creates a new instance of the Supply entity.
     * @param building - The building that is linked to a specific supply
     * @param name - The name of the supply
     * @param stock - The stock of a specific supply
     */

    public Supply(Building building, String name, int stock) {
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

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
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
