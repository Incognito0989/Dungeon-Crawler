package src.main.java.item.weapon;

import src.main.java.item.Item;

public class Weapon extends Item {


    private String name;
    private String description;
    private int damage;
    private int defense;
    private String image;
    private int damageOriginal;
    private int range;

    /**
     * creates a weapon with a given name and description
     * @param name the name of weapon.
     * @param description description of weapon.
     * @param damage damage the weapon will have.
     * @param defense how much the weapon protects player from monster attacks.
     * @param image image url of monster
     * @param range range of weapon's attack
     */
    public Weapon(String name, String description, int damage,
                  int defense, String image, int range) {
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.image = image;
        this.damageOriginal = damage;
        this.range = range;
    }

    /**
     * Returns the name of the weapon.
     * @return name of weapon.
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the name of the weapon.
     * @param name what the name of the weapon will be changed to.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the description of the weapon
     * @return returns description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Changes the description of a weapon.
     * @param description of weapon.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getter for the weapon damage.
     * @return returns damage.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * setter for the damage.
     * @param damage level of damge to set weapon.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * getter for the defense level for weapon.
     * @return returns defense level.
     */
    public int getDefense() {
        return defense;
    }

    /**
     * setter for defense level.
     * @param defense level of defense.
     */
    public void setDefense(int defense) {
        this.defense = defense;
    }

    /**
     * getter for the image location of weapon.
     * @return location string.
     */
    public String getImage() {
        return image;
    }

    /**
     * setter for the image location of weapon.
     * @param image location string.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Finds the original damage of the Weapon
     * @return original damage of weapon
     */
    public int getDamageOriginal() {
        return damageOriginal;
    }

    /**
     * getter for the range of weapon.
     * @return range.
     */
    public int getRange() {
        return range;
    }

}