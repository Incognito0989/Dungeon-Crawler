package src.main.java.item.potion;

public class SpeedPotion extends Potion {
    private int speedIncrease;

    /**
     * Constructor for the Speed Potion
     */
    public SpeedPotion() {
        super("Sonic Juice", "Increases the player speed\nby "
                + 5 + " indefinitely.", "src/main/resources/images/items/speed_potion.png");
        this.speedIncrease = 5;
    }

    /**
     * getter for the speed increase.
     * @return speed increase.
     */
    public int getSpeedIncrease() {
        return speedIncrease;
    }
}
