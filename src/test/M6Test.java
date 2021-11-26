package src.test;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.testfx.api.FxAssert.verifyThat;

public class M6Test extends ApplicationTest {
    /**
     * starts the application.
     * @param primaryStage the main stage.
     * @throws IOException if stage cant be found.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Main main = new Main();
        main.start(primaryStage);
    }

    /**
     * tears down code.
     * @throws Exception exception.
     */
    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    /**
     * tests gold stats on death screen.
     */
    @Test
    public void testDeathGoldStats() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("Andrew");
        clickOn("#customMaze");
        clickOn("#hardDifficulty");
        clickOn(".button");
        clickOn("#doorEast");
        type(KeyCode.P);
        verifyThat("#goldLabel", LabeledMatchers.hasText("Gold: 100"));
        clickOn("#exitButton");
    }

    /**
     * tests room visits on death screen.
     */
    @Test
    public void testDeathRoomsStats() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("Andrew");
        clickOn("#customMaze");
        clickOn("#hardDifficulty");
        clickOn(".button");
        clickOn("#doorEast");
        type(KeyCode.P);
        verifyThat("#roomsVisitedLabel", LabeledMatchers.hasText("Rooms Visited: 2"));
        clickOn("#exitButton");
    }

    /**
     * tests the kill stats on death screen.
     */
    @Test
    public void testDeathKillsStats() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("Andrew");
        clickOn("#customMaze");
        clickOn("#hardDifficulty");
        clickOn(".button");
        clickOn("#doorEast");
        type(KeyCode.P);
        verifyThat("#killsLabel", LabeledMatchers.hasText("Kills: 0"));
        clickOn("#exitButton");
    }

    /**
     * tests the damage stat on death screen.
     */
    @Test
    public void testDeathDamageStats() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("Andrew");
        clickOn("#customMaze");
        clickOn("#hardDifficulty");
        clickOn(".button");
        clickOn("#doorEast");
        type(KeyCode.P);
        verifyThat("#damageLabel", LabeledMatchers.hasText("Damage Dealt: 10"));
        clickOn("#exitButton");
    }

    /**
     * tests the restart button on the death screen.
     */
    @Test
    public void testDeathRestartButton() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("Andrew");
        clickOn("#customMaze");
        clickOn("#hardDifficulty");
        clickOn(".button");
        clickOn("#doorEast");
        type(KeyCode.P);
        clickOn("#restartButton");
        verifyThat("#startGame", NodeMatchers.isNotNull());
    }

    /**
     * Goes to exit room.
     * Fights monster.
     * Checks if stats are present.
     * Exits.
     */
    @Test
    public void testGoToExitAndFightBoss() {
        setUpGame();
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: 0 0"));
        Label coord = lookup("#exitCoord").query();
        String[] coordText = coord.getText().split(" ");
        int vertical = Integer.parseInt(coordText[1]);
        int horizontal = Integer.parseInt(coordText[2]);
        traverseMazeToExit(vertical, horizontal);
        verifyThat("#monsterEncounter", NodeMatchers.isNotNull());
        fightMonster();
        verifyThat("#exitButton", NodeMatchers.isNotNull());
        clickOn("#exitButton");
        verifyThat("#damageLabel1", NodeMatchers.isNotNull());
        verifyThat("#goldLabel1", NodeMatchers.isNotNull());
        verifyThat("#killsLabel1", NodeMatchers.isNotNull());
        verifyThat("#roomsVisitedLabel1", NodeMatchers.isNotNull());
    }

    /**
     * tests the winner restart button.
     */
    @Test
    public void testWinRestart() {
        setUpGame();
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: 0 0"));
        Label coord = lookup("#exitCoord").query();
        String[] coordText = coord.getText().split(" ");
        int vertical = Integer.parseInt(coordText[1]);
        int horizontal = Integer.parseInt(coordText[2]);
        traverseMazeToExit(vertical, horizontal);
        verifyThat("#monsterEncounter", NodeMatchers.isNotNull());
        fightMonster();
        verifyThat("#exitButton", NodeMatchers.isNotNull());
        clickOn("#exitButton");
        clickOn("#restartButton");
        verifyThat("#startGame", NodeMatchers.isNotNull());
    }

    /**
     * tests if the doors all close when boss is killed.
     */
    @Test
    public void testExitRoomDoors() {
        setUpGame();
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: 0 0"));
        Label coord = lookup("#exitCoord").query();
        String[] coordText = coord.getText().split(" ");
        int vertical = Integer.parseInt(coordText[1]);
        int horizontal = Integer.parseInt(coordText[2]);
        traverseMazeToExit(vertical, horizontal);
        verifyThat("#monsterEncounter", NodeMatchers.isNotNull());
        fightMonster();
        verifyThat("#exitButton", NodeMatchers.isNotNull());
        verifyThat("#doorNorth", NodeMatchers.isDisabled());
        verifyThat("#doorSouth", NodeMatchers.isDisabled());
        verifyThat("#doorEast", NodeMatchers.isDisabled());
        verifyThat("#doorWest", NodeMatchers.isDisabled());
    }

    /**
     * Makes sure that entering a challenge room keeps player items
     * and attributes the same
     */
    @Test
    public void testChallengeRoomEnter() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("AK");
        clickOn("#customMaze");
        clickOn(".button");
        clickOn("#doorNorth");
        fightMonster();
        clickOn("#doorSouth");
        clickOn("#doorWest");
        ProgressBar pb = (ProgressBar) lookup("#playerHP").query();
        clickOn("#challengeButton");
        assertEquals(pb.getProgress(), ((ProgressBar) lookup("#playerHP").query()).getProgress());
    }

    /**
     * Makes sure that entering challenge room subtracts 75 gold
     */
    @Test
    public void testChallengeRoomSubtractsGold() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("AK");
        clickOn("#customMaze");
        clickOn(".button");
        clickOn("#doorWest");
        clickOn("#challengeButton");
        verifyThat("#goldAmount", LabeledMatchers.hasText("Gold: 225"));
    }

    /**
     * Ensures that monsters in challenge room can be defeated
     */
    @Test
    public void testChallengeRoomDefeatMonsters() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("AK");
        clickOn("#customMaze");
        clickOn(".button");
        clickOn("#doorWest");
        clickOn("#challengeButton");
        fightMonstersChallenge();
        verifyThat("#monster1", NodeMatchers.isInvisible());
        verifyThat("#monster2", NodeMatchers.isInvisible());
        verifyThat("#monster3", NodeMatchers.isInvisible());
    }

    /**
     * Ensures that challenge room can be exited,
     * and that exits to room where player entered.
     */
    @Test
    public void testChallengeRoomExit() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("AK");
        clickOn("#customMaze");
        clickOn(".button");
        clickOn("#doorWest");
        clickOn("#challengeButton");
        fightMonstersChallenge();
        clickOn("#drop1");
        clickOn("#drop2");
        clickOn("#drop3");
        clickOn("#returnButton");
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: 0 -1"));
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
    /**
     * Helper method to traverse a maze to its exit
     * @param v vertical distance
     * @param h horizontal distance
     */
    private void traverseMazeToExit(int v, int h) {
        String vDoor = (Math.signum(v) > 0) ? "#doorNorth" : "#doorSouth";
        String hDoor = (Math.signum(h) > 0) ? "#doorEast" : "#doorWest";
        for (int i = 0; i != v; i += Math.signum(v)) {
            clickOn(vDoor);
            fightMonster();
        }
        for (int i = 0; i != h; i += Math.signum(h)) {
            clickOn(hDoor);
            fightMonster();
        }
    }
    /**
     * Helper method to automatically fight and kill a monster
     */
    private void fightMonster() {
        ImageView monsterImage = lookup("#monsterEncounter").query();
        while (monsterImage.isVisible() && !monsterImage.isDisabled()) {
            clickOn(monsterImage);
        }
        try {
            clickOn("#potionDrop");
            clickOn("#itemTwo");
        } catch (Exception e) {

        }
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

    private void fightMonstersChallenge() {
        VBox monsterImage = lookup("#monster2").query();
        while (monsterImage.isVisible() && !monsterImage.isDisabled()) {
            type(KeyCode.P);
        }
        type(KeyCode.D);
        monsterImage = lookup("#monster3").query();
        while (monsterImage.isVisible() && !monsterImage.isDisabled()) {
            type(KeyCode.P);
        }
        type (KeyCode.D);
        monsterImage = lookup("#monster1").query();
        while (monsterImage.isVisible() && !monsterImage.isDisabled()) {
            type(KeyCode.P);
        }
        try {
            clickOn("#potionDrop");
            clickOn("#itemTwo");
        } catch (Exception e) {

        }
    }
}
