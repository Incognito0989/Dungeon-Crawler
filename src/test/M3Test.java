package src.test;

import javafx.scene.control.Label;
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

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;

public class M3Test extends ApplicationTest {

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
        traverseMazeToExit(vertical, horizontal);
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
        traverseMazeToExit(vertical, horizontal);

        verifyThat("#exitButton", NodeMatchers.isVisible());
        if (horizontal > 0) {
            clickOn("#doorWest");
        } else if (horizontal < 0) {
            clickOn("#doorEast");
        } else if (vertical > 0) {
            clickOn("#doorSouth");
        } else if (vertical < 0) {
            clickOn("#doorNorth");
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
     * Helper method to set up config menu with the chosen difficulty and weapon
     * @param difficulty query string for the difficulty button chosen
     * @param weapon query string for the weapon button chosen
     * @param name Name of the player in the game
     */
    private void setUpGameConfig(String difficulty, String weapon, String name) {
        clickOn("#startGame");
        clickOn(difficulty);
        clickOn(weapon);
        clickOn("#nameTextField");
        write(name);
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

}
