package src.test;

import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.util.WaitForAsyncUtils;
import src.main.java.Main;
import src.main.java.enums.Direction;
import src.main.java.mazegen.Maze;
import src.main.java.mazegen.rooms.Room;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;

public class M4Test extends ApplicationTest {

    /**
     * Starts the dungeon crawler application at the beginning of every test
     * @param primaryStage the stage everything is set on
     * @throws IOException file exception if welcome screen, welcome.fxml not found
     */
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

    /**
     * Tests if room has monsters
     */
    @Test
    public void testRoomHasMonstersAlbertKo() {
        setUpGame();
        clickOn("#doorWest");
        verifyThat("#monsterEncounter", NodeMatchers.isVisible());
        verifyThat("#monsterEncounter", NodeMatchers.isEnabled());
    }

    /**
     * Tests if death screen enables upon death
     */
    @Test
    public void testDeathScreenAlbertKo() {
        setUpGameConfig("#hardDifficulty", "#baseballBat");
        clickOn("#doorWest");
        verifyThat("#monsterEncounter", NodeMatchers.isEnabled());
        verifyThat("#monsterEncounter", NodeMatchers.isVisible());
        clickOn("#monsterEncounter");
        verifyThat("#exitButton", NodeMatchers.isNotNull());
        verifyThat("#restartButton", NodeMatchers.isNotNull());
    }

    /**
     * tests if exit button on test screen closes out of application
     */
    @Test
    public void testDeathScreenExitsAlbertKo() {
        setUpGameConfig("#hardDifficulty", "#baseballBat");
        clickOn("#doorWest");
        verifyThat("#monsterEncounter", NodeMatchers.isEnabled());
        verifyThat("#monsterEncounter", NodeMatchers.isVisible());
        clickOn("#monsterEncounter");
        Scene t = lookup("#exitButton").query().getScene();
        clickOn("#exitButton");
        verifyThat(window(t), WindowMatchers.isNotShowing());
    }

    /**
     * Tests if restart button on test screen goes back to menu screen
     */
    @Test
    public void testDeathScreenRestartsAlbertKo() {
        setUpGameConfig("#hardDifficulty", "#baseballBat");
        clickOn("#doorWest");
        verifyThat("#monsterEncounter", NodeMatchers.isEnabled());
        verifyThat("#monsterEncounter", NodeMatchers.isVisible());
        clickOn("#monsterEncounter");
        clickOn("#restartButton");
        verifyThat("#startGame", NodeMatchers.isNotNull());
    }

    /**
     * Tests that StartingRoom of Maze is always connected to four Rooms
     */
    @Test
    public void testMazeGenerationAlbertKo() {
        for (int i = 0; i < 10; i++) {
            Maze m = new Maze();
            Room r = m.getStartingRoom();
            //m.printMaze();
            for (Direction dir: Direction.values()) {
                if (r.getConnectingRoom(dir) == null) {
                    m.printMaze();
                    System.out.println(i);
                    assertTrue(false);
                }
            }
        }
    }


    /**
     * tests if the walter health bar decreases with attacks.
     */
    @Test
    public void testWalterHealthBarJones() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("AK");
        clickOn(".button");
        clickOn("#doorNorth");
        clickOn("#monsterEncounter");
        ProgressBar pp = lookup("#walterHP").query();
        assertNotEquals(1, pp.getProgress());
    }

    /**
     * simple test to see if player health bar goes down.
     */
    @Test
    public void testPlayerHealthBarJones() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("AK");
        clickOn("#fryingPan");
        clickOn(".button");
        clickOn("#doorNorth");
        clickOn("#monsterEncounter");
        ProgressBar pp = lookup("#playerHP").query();
        assertNotEquals(1, pp.getProgress());
    }

    /**
     * Test to see if the progress bar starts at full
     */
    @Test
    public void checkProgressBarAtFullVemuri() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("kesha");
        clickOn(".button");
        ProgressBar bar = lookup("#playerHP").query();
        assertEquals(1.0, bar.getProgress());
    }

    /**
     * Test to see if you can return to previous room
     */
    @Test
    public void testRetreatToRoomVemuri() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("obama");
        clickOn(".button");
        clickOn("#doorNorth");
        verifyThat("#doorSouth", NodeMatchers.isVisible());
    }
    /**
     * Verifies that the top door works.
     */
    @Test
    public void moveToTopRoomChang() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("Andrew Chang");
        clickOn(".button");
        clickOn("#doorNorth");
        verifyThat("#goldAmount", NodeMatchers.isNotNull());
    }

    /**
     * Verifies that the bottom door works.
     */
    @Test
    public void moveToBottomRoomChang() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("Andrew Chang");
        clickOn(".button");
        clickOn("#doorSouth");
        verifyThat("#goldAmount", NodeMatchers.isNotNull());
    }

    /**
     * Verifies gold for easy mode.
     */
    @Test
    public void easyMoneyChang() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("Andrew Chang");
        clickOn(".button");
        verifyThat("#goldAmount", NodeMatchers.hasChild("Gold: 300"));
    }

    /**
     * Verifies gold for normal mode.
     */
    @Test
    public void normalMoneyChang() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("Andrew Chang");
        clickOn("#normalDifficulty");
        clickOn(".button");
        verifyThat("#goldAmount", NodeMatchers.hasChild("Gold: 200"));
    }

    /**
     * Verifies gold for hard mode.
     */
    @Test
    public void hardMoneyChang() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("Andrew Chang");
        clickOn("#hardDifficulty");
        clickOn(".button");
        verifyThat("#goldAmount", NodeMatchers.hasChild("Gold: 100"));
    }

    /**
     * Helper method to automatically fight and kill a monster
     */
    private void fightMonster() {
        ImageView monsterImage = lookup("#monsterEncounter").query();
        while (monsterImage.isVisible() && !monsterImage.isDisabled()) {
            clickOn(monsterImage);
        }
    }

    /**
     * Helper method to set up config menu with the chosen difficulty and weapon
     * @param difficulty query string for the difficulty button chosen
     * @param weapon query string for the weapon button chosen
     */
    private void setUpGameConfig(String difficulty, String weapon) {
        clickOn("#startGame");
        clickOn(difficulty);
        clickOn(weapon);
        clickOn("#nameTextField");
        write("A");
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

}
