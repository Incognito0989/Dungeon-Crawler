package src.main.java.item.potion;

import src.main.java.item.Item;

public class Potion extends Item {

    private String potionName;
    private String potionDescription;
    private String potionImage;


    /**
     * Constructor to create a potion object.
     * @param potionName name of the potion
     * @param potionDescription description of the potion
     * @param potionImage image of the potion
     */
    public Potion(String potionName, String potionDescription, String potionImage) {
        this.potionName = potionName;
        this.potionDescription = potionDescription;
        this.potionImage = potionImage;
    }

    /**
     * getter for potion name.
     * @return potion name.
     */
    public String getPotionName() {
        return potionName;
    }

    /**
     * getter for the potion description.
     * @return description.
     */
    public String getPotionDescription() {
        return potionDescription;
    }

    /**
     * getter for the potion image location.
     * @return file location.
     */
    public String getPotionImage() {
        return potionImage;
    }
}
