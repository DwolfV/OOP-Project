package nl.tudelft.oopp.demo.entities;

public class Equipment {

    private Room room;
    private Item item;
    private int amount;

    /**
     * Creates an instance of a new equipment object.
     *
     * @param room   The room to which the equipment is connected.
     * @param item   The item to which the equipment is connected.
     * @param amount The amount of the item in the room.
     */
    public Equipment(Room room, Item item, int amount) {
        this.room = room;
        this.item = item;
        this.amount = amount;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Equipment{"
            + "room=" + room
            + ", item=" + item
            + ", amount=" + amount
            + '}';
    }
}
