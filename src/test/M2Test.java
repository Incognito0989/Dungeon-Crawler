package src.test;

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
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class M2Test extends ApplicationTest {

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
}
