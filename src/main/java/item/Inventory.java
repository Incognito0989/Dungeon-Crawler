package src.main.java.item;

public class Inventory<T> {
    private Item[] inventory;

    /**
     * Constructor for an Inventory
     * @param size size of inventory
     */
    public Inventory(int size) {
        this.inventory = new Item[6];
    }

    /**
     * adds an element to the inventory.
     * @param element element being added.
     * @return index added to.
     */
    public int add(Item element) {
        for (int n = 0; n < inventory.length; n++) {
            if (inventory[n] == null) {
                inventory[n] = element;
                return n;
            }
        }
        return -1;
    }

    /**
     * Remove an Item from the Inventory
     * @param index index of Item to remove
     * @return removed Item
     */
    public Item remove(int index) {
        Item dum = inventory[index];
        this.inventory[index] = null;
        return dum;
    }

    /**
     * Prints each item in the Inventory
     */
    public void print() {
        for (Item e : inventory) {
            System.out.print(" " + e);
        }
    }

    /**
     * Finds a given item in an Inventory
     * @param index index of item in the Inventory
     * @return Item in the Inventory
     */
    public Item get(int index) {
        return inventory[index];
    }
}
