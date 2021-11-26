package src.main.java.item.potion;

public class AttackPotion extends Potion {
    private int damageBuff;

    /**
     * constructor for a attack potion with a given damage buff.
     * @param damageBuff increase in damage.
     */
    public AttackPotion(int damageBuff) {
        super("Orange Juice",
                "Potion increases player damage by " + damageBuff + " for one room",
                "src/main/resources/images/items/attack_potion.png");
        this.damageBuff = damageBuff;
    }

    /**
     * Getter for damage buff of potion
     * @return amount of damage increased by potion
     */
    public int getDamageBuff() {
        return damageBuff;
    }
}
