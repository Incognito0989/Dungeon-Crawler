package src.main.java.item.weapon;

public class Bow extends Weapon {

    /**
     * Constructor for a Bow Weapon
     * @param name name of Bow
     * @param description description of Bow
     * @param damage damage done by Bow
     * @param defense defense of Bow
     * @param image url of the image of the Bow
     * @param range how far one can attack with the Bow
     */
    public Bow(String name, String description, int damage, int defense, String image, int range) {
        super(name, description, damage, defense, image, range);
    }
}
