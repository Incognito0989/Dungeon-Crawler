package src.main.java.entity;

import src.main.java.enums.Difficulty;
import src.main.java.item.Inventory;
import src.main.java.item.weapon.Weapon;

public class Player<T> {

    private int gold;
    private String name;
    private Weapon weapon;
    private int health;
    private int originalHealth;
    private Inventory<T> inventory;
    private int speed;
    private int monstersKilled;
    private int damageDealt;
    private int roomsVisited;

    /**
     * Makes a new player object.
     * @param gold the amount of gold the player has.
     * @param weapon the weapon the player has.
     * @param name the name of the player.
     */
    public Player(int gold, Weapon weapon, String name) {
        this.gold = gold;
        this.weapon = weapon;
        this.name = name;
        this.health = 100;
        this.inventory = new Inventory<>(6);
        this.inventory.add(weapon);
        this.speed = 5;
        this.monstersKilled = 0;
        this.damageDealt = 0;
        this.roomsVisited = 1;
    }

    /**
     * constructor for the player.
     * @param difficulty level of how hard game will be.
     * @param weapon starter weapon user chooses.
     * @param name name of the player.
     */
    public Player(Difficulty difficulty, Weapon weapon, String name) {
        this.weapon = weapon;
        this.name = name;
        this.monstersKilled = 0;
        this.damageDealt = 0;
        this.roomsVisited = 1;
        switch (difficulty) {
        case EASY:
            gold = 300;
            health = 300;
            break;
        case NORMAL:
            gold = 200;
            health = 200;
            break;
        case HARD: gold = 100;
            health = 100;
            break;
        default:
            gold = 100;
            health = 100;
            break;
        }
    }

    /**
     * Returns the amount of gold the player has.
     * @return returns gold.
     */
    public int getGold() {
        return gold;
    }

    /**
     * Sets the amount of gold the player has.
     * @param gold the amount to set the gold to.
     */
    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * Returns the name of the player.
     * @return returns String name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player.
     * @param name the String name given to player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the weapon that the player is using.
     * @return returns Weapon weapon the player has.
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * getter for health value.
     * @return returns the health value.
     */
    public int getHealth() {
        return health;
    }

    /**
     * setter for health value of player.
     * @param health value to set health to.
     */
    public void setHealth(int health) {
        this.health = health;
    }


    /**
     * Sets the equipped weapon the player has.
     * @param weapon weapon to set player weapon to.
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Adds an amount of gold to the total gold player has.
     * @param amount amount to add.
     */
    public void addGold(int amount) {
        gold += amount;
    }

    /**
     * method for getting damaged. not being used rn.
     * @param damage damage
     */
    public void damage(int damage) {
        health = health - damage;
        if (health <= 0) {
            health = 0;
            faint();
        }
    }

    /**
     * getter for the original health.
     * @return original health.
     */
    public int getOriginalHealth() {
        return originalHealth;
    }

    /**
     * setter for original health.
     * @param originalHealth health.
     */
    public void setOriginalHealth(int originalHealth) {
        this.originalHealth = originalHealth;
    }

    /**
     * player faints
     */
    public void faint() {

    }

    /**
     * getter for the inventory.
     * @return inventory.
     */
    public Inventory<T> getInventory() {
        return inventory;
    }

    /**
     * setter for the inventory.
     * @param inventory inventory of items.
     */
    public void setInventory(Inventory<T> inventory) {
        this.inventory = inventory;
    }

    /**
     * getter for player speed.
     * @return speed.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * setter fo for the player speed.
     * @param speed speed.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * getter for the number of monsters killed.
     * @return monsters killed.
     */
    public int getMonstersKilled() {
        return monstersKilled;
    }

    /**
     * setter for the monsters killed.
     * @param monstersKilled number of monsters killed.
     */
    public void setMonstersKilled(int monstersKilled) {
        this.monstersKilled = monstersKilled;
    }

    /**
     * getter for the damage dealt.
     * @return damage dealt.
     */
    public int getDamageDealt() {
        return damageDealt;
    }

    /**
     * setter for the damage dealt.
     * @param damageDealt damage dealt.
     */
    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }

    /**
     * getter for the number of rooms visited.
     * @return no of rooms visited.
     */
    public int getRoomsVisited() {
        return roomsVisited;
    }

    /**
     * setter for the number of rooms visited.
     * @param roomsVisited rooms visited.
     */
    public void setRoomsVisited(int roomsVisited) {
        this.roomsVisited = roomsVisited;
    }
}
