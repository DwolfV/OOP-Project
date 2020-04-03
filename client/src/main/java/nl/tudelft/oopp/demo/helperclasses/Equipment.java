package nl.tudelft.oopp.demo.helperclasses;

public class Equipment {

    private Room room;
    private Item item;
    private int amount;

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
        return "Equipment{" +
                "room=" + room +
                ", item=" + item +
                ", amount=" + amount +
                '}';
    }
}
