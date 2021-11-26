package src.test;


import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;
import src.main.java.Main;

import java.io.IOException;

import static org.testfx.api.FxAssert.verifyThat;

public class M5Test extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Main main = new Main();
        main.start(primaryStage);
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void testEnemyDropsWeaponsAK() {
        setUpGame(true);
        clickOn("#doorNorth");
        fightMonster();
        verifyThat("#potionDrop", NodeMatchers.isVisible());
        verifyThat("#weaponDrop", NodeMatchers.isVisible());
    }

    @Test
    public void testPickUpPotionAK() {
        setUpGame(true);
        clickOn("#doorNorth");
        fightMonster();
        clickOn("#potionDrop");
        moveTo("#itemTwo");
        verifyThat("#itemTwoDesc", NodeMatchers.isVisible());
    }

    @Test
    public void testRight() {
        setUpGame("User");
        press(KeyCode.D).release(KeyCode.D);
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: 0 0"));
    }

    @Test
    public void testLeft() {
        setUpGame("User");
        press(KeyCode.A).release(KeyCode.A);
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: 0 0"));
    }

    @Test
    public void testUp() {
        setUpGame("User");
        press(KeyCode.W).release(KeyCode.W);
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: 0 0"));
    }

    @Test
    public void testDown() {
        setUpGame("User");
        press(KeyCode.S).release(KeyCode.S);
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: 0 0"));
    }

    @Test
    public void testRightRoom() {
        setUpGame("User");
        for (int i = 0; i < 65; i++) {
            press(KeyCode.D).release(KeyCode.D);
        }
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: 0 1"));
    }

    @Test
    public void testLeftRoom() {
        setUpGame("User");
        for (int i = 0; i < 65; i++) {
            press(KeyCode.A).release(KeyCode.A);
        }
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: 0 -1"));
    }

    @Test
    public void testUpRoom() {
        setUpGame("User");
        for (int i = 0; i < 65; i++) {
            press(KeyCode.W).release(KeyCode.W);
        }
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: 1 0"));
    }

    @Test
    public void testDownRoom() {
        setUpGame("User");
        for (int i = 0; i < 50; i++) {
            press(KeyCode.S).release(KeyCode.S);
        }
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: -1 0"));
    }

    @Test
    public void testPickUpWeapon() {
        setUpGame(true);
        clickOn("#doorNorth");
        fightMonster();
        clickOn("#weaponDrop");
        moveTo("#itemOne");
        verifyThat("#itemTwoDesc", NodeMatchers.isInvisible());
    }

    @Test
    public void testSuperSayanBow() {
        setUpGame(true);
        clickOn("#doorNorth");
        fightMonster();
        clickOn("#weaponDrop");
        clickOn("#doorSouth");
        clickOn("#doorEast");
        type(KeyCode.P);
        verifyThat("#walterHPLabel", LabeledMatchers.hasText("Enemy HP: 5 / 20"));
    }

    @Test
    public void testHealthPotion() {
        setUpGame(true);
        clickOn("#doorNorth");
        fightMonster();
        verifyThat("#playerHPLabel", LabeledMatchers.hasText("Your HP: 190 / 200"));
        clickOn("#potionDrop");
        clickOn("#itemTwo");
        verifyThat("#playerHPLabel", LabeledMatchers.hasText("Your HP: 200 / 200"));
    }

    @Test
    public void testSpeedPotion() {
        setUpGame(true);
        clickOn("#doorSouth");
        fightMonster();
        clickOn("#potionDrop");
        clickOn("#itemTwo");
        for (int i = 0; i < 45; i++) {
            press(KeyCode.W).release(KeyCode.W);
        }
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: 0 0"));
    }

    @Test
    public void testAttackPotion() {
        setUpGame(true);
        clickOn("#doorEast");
        fightMonster();
        clickOn("#potionDrop");
        clickOn("#doorWest");
        clickOn("#doorWest");
        clickOn("#itemTwo");
        type(KeyCode.P);
        verifyThat("#monsterEncounter", NodeMatchers.isInvisible());
    }

    @Test
    public void testGoldFromKillingMonsters() {
        setUpGame(true);
        verifyThat("#goldAmount", LabeledMatchers.hasText("Gold: 300"));
        clickOn("#doorEast");
        fightMonster();
        verifyThat("#goldAmount", LabeledMatchers.hasText("Gold: 310"));
    }




    /**
     * Helper method to set up config menu with the chosen difficulty and weapon
     * @param difficulty query string for the difficulty button chosen
     * @param weapon query string for the weapon button chosen
     * @param checkBox whether or not the checkbox is ticked
     */
    private void setUpGameConfig(String difficulty, String weapon, boolean checkBox) {
        clickOn("#startGame");
        clickOn(difficulty);
        clickOn(weapon);
        clickOn("#nameTextField");
        write("AK");
        if (checkBox) {
            clickOn("#customMaze");
        }
        clickOn(".button");
    }

    /**
     * Helper method to set up config menu with default difficulty and weapon
     */
    private void setUpGame() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("AK");
        clickOn(".button");
    }

    /**
     * Helper method to set up config menu, along with ticking test checkbox
     * @param checkBox true if checkbox is ticked, false otherwise
     */
    private void setUpGame(boolean checkBox) {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("AK");
        if (checkBox) {
            clickOn("#customMaze");
        }
        clickOn(".button");
    }

    /**
     * Helper method to automatically fight a monster
     */
    private void fightMonster() {
        ImageView monsterImage = lookup("#monsterEncounter").query();
        while (monsterImage.isVisible() && !monsterImage.isDisabled()) {
            type(KeyCode.P);
        }
    }

    /**
     * Helper method to set up config menu with custom name
     * @param name name of player.
     */
    private void setUpGame(String name) {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write(name);
        clickOn(".button");
    }

}
