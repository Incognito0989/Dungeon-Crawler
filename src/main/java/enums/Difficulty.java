package src.main.java.enums;

public enum Difficulty {
    EASY(200, 300), NORMAL(100, 200), HARD(1, 100);
    private final int maxHealth;
    private final int startingGold;

    /**
     * Constructor for the easy, difficult, and hard Difficulties
     * @param maxHealth max health at the difficulty
     * @param startingGold starting amount of gold at the given difficulty
     */
    private Difficulty(int maxHealth, int startingGold) {
        this.maxHealth = maxHealth;
        this.startingGold = startingGold;
    }

    /**
     * Getter for the max health of the Difficulty
     * @return max health
     */
    public int maxHealth() {
        return maxHealth;
    }

    /**
     * Getter for the starting gold of the Difficulty
     * @return starting gold
     */
    public int startingGold() {
        return startingGold;
    }
}
