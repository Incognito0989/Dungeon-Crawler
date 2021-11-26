package src.main.java.entity.monster;

import src.main.java.item.Item;

import java.util.Random;

public class Monster {
    private int health;
    private int maxHealth;
    private int damage;
    private int counter; //value to determine monsters probability of countering.
    private String imageURL;
    private double potionDrop; //probability 0-1
    private double weaponDrop; //probability 0-1
    private int goldDrop;
    private Item[] items;


    /**
     * method to create random monsters for each room.
     * @return the monster for the room.
     */
    public static Monster randomMonster() {
        Random rand = new Random();
        int choice = rand.nextInt(4);
        switch (choice) { case 0: return new BasicWalter();
        case 1: return new AngryWalter();
        case 2: return new JacketWalter();
        case 3: return new WatermelonWalter();
        default: return new AngryWalter();
        }
    }

    /**
     * Constructor for a generic Monster with given pairs of Items
     * @param items array of Items monster will drop
     */
    public Monster(Item[] items) {
        this();
        this.items = items;
    }

    /**
     * constructor to make a monster with provided values.
     * @param health monster's health.
     * @param damage monster's attack damage.
     * @param counter monster's probability to counter.
     * @param imageURL the image file location of monster.
     * @param potionDrop the probability a potion will be dropped.
     * @param weaponDrop the probability a weapon will be dropped.
     * @param goldDrop the amount of gold dropped.
     */
    public Monster(int health, int damage, int counter, String imageURL,
                   double potionDrop, double weaponDrop, int goldDrop) {
        this.maxHealth = health;
        this.health = health;
        this.damage = damage;
        this.counter = counter;
        this.imageURL = imageURL;
        this.potionDrop = potionDrop;
        this.weaponDrop = weaponDrop;
        this.goldDrop = goldDrop;
    }

    /**
     * basic constructor to make a prebuilt monster.
     */
    public Monster() {
        this(20, 10, 0, "/src/main/resources/images/monsters/basic_walter.png", 0, 0, 10);
    }

    /**
     * getter for monster's health.
     * @return monster's health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * setter for monsters health.
     * @param health health to set to.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * getter for monster's damage.
     * @return damage.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * setter for monster damage.
     * @param damage set value for damage.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * getter for monster's counter probability.
     * @return probability of countering.
     */
    public int getCounter() {
        return counter;
    }

    /**
     * setter for counter.
     * @param counter value to make counter.
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }

    /**
     * getter for the max health.
     * @return max health
     */
    public int getMaxHealth() {
        return maxHealth;
    }
  
    /**
     * getter for the image file location.
     * @return location.
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * getter for the potion drop probability.
     * @return potion drop probability.
     */
    public double getPotionDrop() {
        return potionDrop;
    }

    /**
     * setter for the potion drop probability.
     * @param potionDrop potion drop probability.
     */
    public void setPotionDrop(int potionDrop) {
        this.potionDrop = potionDrop;
    }

    /**
     * getter for the weapon drop probability.
     * @return weapon drop probability.
     */
    public double getWeaponDrop() {
        return weaponDrop;
    }

    /**
     * setter for the weapon drop probability.
     * @param weaponDrop weapon drop probability.
     */
    public void setWeaponDrop(int weaponDrop) {
        this.weaponDrop = weaponDrop;
    }

    /**
     * amount of gold to drop.
     * @return gold amount dropped.
     */
    public int getGoldDrop() {
        return goldDrop;
    }

    /**
     * setter for the gold drop amount.
     * @param goldDrop gold drop amount.
     */
    public void setGoldDrop(int goldDrop) {
        this.goldDrop = goldDrop;
    }

    /**
     * Setter for the array of Items
     * @param items items that the monster will drop
     */
    public void setItems(Item[] items) {
        this.items = items;
    }

    /**
     * Getter for the array of Items
     * @return items the Monster will drop
     */
    public Item[] getItems() {
        return this.items;
    }
}
