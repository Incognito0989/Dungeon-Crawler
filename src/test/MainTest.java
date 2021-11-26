package src.test;

import javafx.scene.Scene;
import javafx.scene.control.Label;
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
import org.testfx.matcher.control.LabeledMatchers;

import org.testfx.util.WaitForAsyncUtils;
import src.main.java.Main;
import src.main.java.enums.Direction;
import src.main.java.mazegen.Maze;
import src.main.java.mazegen.rooms.Room;

import java.io.IOException;

//import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

class MainTest extends ApplicationTest {


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
     * Tests if #startGame button moves onto next screen (initialconfigscreen.fxml).
     * Makes sure that difficulty, weapon RadioButtons and name textfield show up,
     * while startGame button is not on initial config screen
     */
    @Test
    public void testGameStartAlbertKo() {
        verifyThat("#startGame", LabeledMatchers.hasText("Start Game"));
        clickOn("#startGame");
        verifyThat("#easyDifficulty", NodeMatchers.isNotNull());
        verifyThat("#normalDifficulty", NodeMatchers.isNotNull());
        verifyThat("#hardDifficulty", NodeMatchers.isNotNull());
        verifyThat("#baseballBat", NodeMatchers.isNotNull());
        verifyThat("#fryingPan", NodeMatchers.isNotNull());
        verifyThat("#trashLid", NodeMatchers.isNotNull());
        verifyThat("#nameTextField", NodeMatchers.isNotNull());
        verifyThat("#startGame", NodeMatchers.isNull());
        // assertThrows(EmptyNodeQueryException.class, () -> lookup("#startGame").query());
    }

    /**
     * Tests if entering name in name text field in config screen will make show up in
     * initial game screen
     */
    @Test
    public void testEnterNameAlbertKo() {
        clickOn("#startGame");
        clickOn("#nameTextField");
        write("Sans Undertale");
        clickOn(".button");
        verifyThat("#name", LabeledMatchers.hasText("Name: Sans Undertale"));
    }

    /**
     * Tests if enter name in config screen, but change with backspace will make changed name
     * show up in initial game screen
     */
    @Test
    public void testEnterNameBackspaceAlbertKo() {
        clickOn("#startGame");
        clickOn("#nameTextField");
        write("Sans Undertale");
        type(KeyCode.BACK_SPACE, 10);
        clickOn(".button");
        verifyThat("#name", LabeledMatchers.hasText("Name: Sans"));
    }

    /**
     * Tests if "I'm Ready" button won't move on to initial game screen if name not
     * entered into name text field
     */
    @Test
    public void testNoEnterNameAlbertKo() {
        clickOn("#startGame");
        clickOn("#nameTextField");
        clickOn(".button");
        verifyThat("#nameTextField", NodeMatchers.isNotNull());
        //assertThrows(EmptyNodeQueryException.class, () -> lookup("#name").query());
    }

    /**
     * Verifies that clicking on the start button changes the screen.
     */
    @Test()
    public void testStartButtonWorksJones() throws InterruptedException {
        verifyThat("#startGame", hasText("Start Game"));
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
    }

    /**
     * Tests if entering name in name text field in config screen will make show up in
     * initial game screen
     */
    @Test
    public void nameTestAndrewChang() {
        clickOn("#startGame");
        clickOn("#nameTextField");
        write("Andrew Chang");
        clickOn(".button");
        verifyThat("#name", LabeledMatchers.hasText("Name: Andrew Chang"));
    }

    /**
     * Tests if enter name in config screen, but change with backspace will make changed name
     * show up in initial game screen
     */
    @Test
    public void nameRemoveAndrewChang() {
        clickOn("#startGame");
        clickOn("#nameTextField");
        write("Andrew");
        type(KeyCode.BACK_SPACE, 3);
        clickOn(".button");
        verifyThat("#name", LabeledMatchers.hasText("Name: And"));
    }

    /**
     * Tests if start button exists.
     */
    @Test
    public void hasStartButton() {
        // expect:
        verifyThat(".button", hasText("Start Game"));
    }

    /**
     * Tests if ready button exists
     */
    @Test
    public void hasReadyButton() {
        // expect:
        clickOn("#startGame");
        clickOn("#nameTextField");
        write("Andrew");
        verifyThat(".button", hasText("I'm Ready"));
    }

    /**
     * Tests if "I'm Ready" button won't move on to initial game screen if name not
     * entered into name text field
     */
    @Test
    public void nameNoneAndrew() {
        clickOn("#startGame");
        clickOn("#nameTextField");
        clickOn(".button");
        verifyThat("#nameTextField", NodeMatchers.isNotNull());
        verifyThat("#name", NodeMatchers.isNull());
        //assertThrows(EmptyNodeQueryException.class, () -> lookup("#name").query());
    }

    /**
     * Verifies that you cannot continue to next screen with a invalid name.
     */
    @Test
    public void testValidNameJones() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write(" ");
        press(KeyCode.ENTER);
        clickOn(".button");
        verifyThat("#goldAmount", NodeMatchers.isNull());
        //assertThrows(EmptyNodeQueryException.class, () -> lookup("#name").query());
    }

    /**
     * verifies that the left door works.
     */
    @Test
    public void moveToLeftRoomJones() {
        setUpGame();
        clickOn("#doorWest");
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: 0 -1"));
    }

    /**
     * verifies that the right door works.
     */
    @Test
    public void moveToRightRoomJones() {
        setUpGame();
        clickOn("#doorEast");
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: 0 1"));
        clickOn("#menuButton");
    }

    /**
     * Verifies that top door works
     */
    @Test
    public void moveToTopRoomKo() {
        setUpGame();
        clickOn("#doorNorth");
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: 1 0"));
    }

    /**
     * Verifies that bottom door works
     */
    @Test
    public void moveToBottomRoomKo() {
        setUpGame();
        clickOn("#doorSouth");
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: -1 0"));
    }

    /**
     * checks validity of name if backspace is used
     */
    @Test
    public void testBackspaceNameVemuri() {
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("Hope");
        type(KeyCode.BACK_SPACE, 4);
        write(" ");
        press(KeyCode.ENTER);
        verifyThat("#name", NodeMatchers.isNull());
        //assertThrows(EmptyNodeQueryException.class, () -> lookup("#name").query());
    }

    /**
     * checks if nothing is entered.
     */
    @Test
    public void testNothingVemuri() {
        clickOn("#startGame");
        clickOn("#nameTextField");
        write("");
        clickOn(".button");
        verifyThat("#name", NodeMatchers.isNull());
        //assertThrows(EmptyNodeQueryException.class, () -> lookup("#name").query());
    }

    /**
     * Verifies that the exit room is where it should be
     */
    @Test
    public void testGoToExitKo() {
        setUpGame();
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: 0 0"));
        Label coord = lookup("#exitCoord").query();
        String[] coordText = coord.getText().split(" ");
        int vertical = Integer.parseInt(coordText[1]);
        int horizontal = Integer.parseInt(coordText[2]);
        if (vertical > 0) {
            for (int i = 0; i < vertical; i++) {
                clickOn("#doorNorth");
                fightMonster();
            }
        } else {
            for (int i = 0; i > vertical; i--) {
                clickOn("#doorSouth");
                fightMonster();
            }
        }

        if (horizontal > 0) {
            for (int i = 0; i < horizontal; i++) {
                clickOn("#doorEast");
                fightMonster();
            }
        } else {
            for (int i = 0; i > horizontal; i--) {
                clickOn("#doorWest");
                fightMonster();
            }
        }

        verifyThat("#exitButton", NodeMatchers.isVisible());
        verifyThat("#exitButton", NodeMatchers.isEnabled());
        clickOn("#exitButton");
        verifyThat("#youWonButton", NodeMatchers.isNotNull());
        clickOn("#youWonButton");
    }

    /**
     * Tests Exit Button doesn't enable until reaching the exit room
     */
    @Test
    public void testExitButtonNullKo() {
        setUpGame();
        verifyThat("#exitButton", NodeMatchers.isInvisible());
        verifyThat("#exitButton", NodeMatchers.isDisabled());
    }

    /**
     * Verifies Exit Buttons disables self after when leaving Exit Room
     * but not through four directions and not exit button
     */
    @Test
    public void testExitButtonDisablesKo() {
        setUpGame();
        verifyThat("#roomCoord", LabeledMatchers.hasText("Room: 0 0"));
        Label coord = lookup("#exitCoord").query();
        String[] coordText = coord.getText().split(" ");
        int vertical = Integer.parseInt(coordText[1]);
        int horizontal = Integer.parseInt(coordText[2]);
        if (vertical > 0) {
            for (int i = 0; i < vertical; i++) {
                clickOn("#doorNorth");
                fightMonster();
            }
        } else {
            for (int i = 0; i > vertical; i--) {
                clickOn("#doorSouth");
                fightMonster();
            }
        }

        if (horizontal > 0) {
            for (int i = 0; i < horizontal; i++) {
                clickOn("#doorEast");
                fightMonster();
            }
        } else {
            for (int i = 0; i > horizontal; i--) {
                clickOn("#doorWest");
                fightMonster();
            }
        }

        verifyThat("#exitButton", NodeMatchers.isVisible());
        if (horizontal > 0) {
            clickOn("#doorWest");
        } else if (horizontal < 0) {
            clickOn("#doorEast");
        } else if (vertical > 0) {
            clickOn("#doorNorth");
        } else if (vertical < 0) {
            clickOn("#doorSouth");
        }
        verifyThat("#exitButton", NodeMatchers.isInvisible());
    }

    /**
     * Checks the exit room coords to make sure they are different every time
     */
    @Test
    public void testRoomGeneratesRandomly() {
        setUpGame();
        Label button = lookup("#exitCoord").query();
        String coord1 = button.getText();
        clickOn("#menuButton");
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameTextField");
        write("AK");
        clickOn(".button");
        button = lookup("#exitCoord").query();
        String coord2 = button.getText();
        assertNotEquals(coord1, coord2);
    }

    /**
     * Tests if exit room is six rooms away
     */
    @Test
    public void testExitRoomSixRoomsAwayKo() {
        setUpGame();
        Label button = lookup("#exitCoord").query();
        String[] coord = button.getText().split(" ");
        int length = Math.abs(Integer.parseInt(coord[1]));
        length += Math.abs(Integer.parseInt(coord[2]));
        assertTrue(length >= 6);
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
        for (int i = 0; i < 100000; i++) {
            Maze m = new Maze();
            Room r = m.getStartingRoom();
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
}