package src.main.java.item.potion;

public class HealthPotion extends Potion {

    private int healthRegen;

    /**
     * creates a health potion.
     * @param healthRegen how much health the potion regenerates.
     */
    public HealthPotion(int healthRegen) {
        super("Obama Milk",
                "Potion regenerates player health by " + healthRegen + " HP.",
                "src/main/resources/images/items/health_potion.png");
        this.healthRegen = healthRegen;
    }

    /**
     * getter for how much the health potion regenerates the player health.
     * @return health regen amount.
     */
    public int getHealthRegen() {
        return healthRegen;
    }
    
}
