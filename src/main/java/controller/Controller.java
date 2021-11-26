package src.main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import src.main.java.entity.Player;
import src.main.java.entity.monster.Monster;
import src.main.java.enums.Direction;
import src.main.java.item.Item;
import src.main.java.item.potion.AttackPotion;
import src.main.java.item.potion.HealthPotion;
import src.main.java.item.potion.SpeedPotion;
import src.main.java.item.weapon.BaseballBat;
import src.main.java.item.weapon.FryingPan;
import src.main.java.item.weapon.SuperSayanBow;
import src.main.java.item.weapon.TrashLid;
import src.main.java.item.weapon.Weapon;
import src.main.java.mazegen.Maze;
import src.main.java.mazegen.rooms.ChallengeRoom;
import src.main.java.mazegen.rooms.Room;
import src.main.java.mazegen.rooms.StartingRoom;

import java.io.IOException;

/**
 * Controller for the InitialConfigScreen and Welcome screen.
 */
public class Controller {


    @FXML private Button startGame;
    @FXML private ToggleGroup difficultyGroup;
    @FXML private RadioButton easyDifficulty;
    @FXML private RadioButton normalDifficulty;
    @FXML private RadioButton hardDifficulty;
    @FXML private ToggleGroup weaponGroup;
    @FXML private RadioButton fryingPan;
    @FXML private RadioButton trashLid;
    @FXML private RadioButton baseballBat;
    @FXML private TextField nameTextField;
    @FXML private Label goldAmount;
    @FXML private CheckBox customMaze;
    private Player player;

    private int amountOfGold = 300;
    private String difficultyLevel = "Easy";
    private String name;
    private String weapon = "Baseball Bat";

    /**
     * Event for the Welcome screen start button to go to next screen.
     * @param actionEvent event when startButton is clicked.
     * @throws IOException file exception if the screen .fxml file is not found.
     */
    public void clickStart(ActionEvent actionEvent) throws IOException {
        System.out.print("clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/src/main/"
                + "resources/screens/initialConfigScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    /**
     * Changes difficulty level on click of button.
     * @param event click of radial buttons.
     * @throws IOException exception for when the InitialConfigScreen.fxml is not found.
     */
    @FXML
    public void onClickDifficulty(ActionEvent event) throws IOException {
        RadioButton sourceButton = (RadioButton) event.getSource();
        difficultyLevel = sourceButton.getText();
        System.out.println(difficultyLevel);
    }

    /**
     * Selects the weapon on click of the buttons.
     * @param event click of button.
     * @throws IOException exception for when InitialConfigScreen.fxml is not found.
     */
    @FXML
    public void onClickWeapon(ActionEvent event) throws IOException {
        RadioButton sourceButton = (RadioButton) event.getSource();
        weapon = sourceButton.getText();
        System.out.println(weapon);
    }

    /**
     * Continues to the GameScreen.
     * @param actionEvent click of "I'm Ready" button.
     * @throws IOException exception for when InitialGameScreen.fxml is not found.
     */
    @FXML
    public void continueToGameScreen(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/main/"
                + "resources/screens/initialGameScreen.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        name = nameTextField.getText().trim();
        if (name.trim().length() != 0 && name != null) {          //need to add for whitespaces
            player = new Player(0, createWeapon(weapon), name);
            if (difficultyLevel.equals("Easy")) {
                player.setHealth(200);
                amountOfGold = 300;
            } else if (difficultyLevel.equals("Normal")) {
                amountOfGold = 200;
                player.setHealth(150);
            } else {
                player.setHealth(1);
                amountOfGold = 100;
            }
            player.setOriginalHealth(player.getHealth());
            player.setGold(amountOfGold);
            System.out.println(name);
            stage.getScene().setRoot(root);
            InitialGameScreenController gController = loader.getController();
            gController.changeInitialGold(amountOfGold);
            gController.setName(name);
            gController.setWeapon(weapon);
            player.getInventory().print();
            gController.setCurrentStage(stage);
            gController.setPlayer(player);
            player.getInventory().print();
            System.out.println(amountOfGold);
            gController.changeHPBar(player);
            if (customMaze.isSelected()) {
                gController.setRoom(customMaze());
            } else {
                gController.setRoom();
            }


            System.out.println("set room");
        }
    }

    /**
     * helper method to give player first weapon.
     * @param name name of the weapon.
     * @return the starter weapon.
     */
    private Weapon createWeapon(String name) {
        Weapon wpn;
        if (name.equals("Baseball Bat")) {
            wpn = new BaseballBat();
        } else if (name.equals("Frying Pan")) {
            wpn = new FryingPan();
        } else {
            wpn = new TrashLid();
        }
        return wpn;
    }

    /**
     * Verification that each screen initializes correctly by printing in terminal.
     */
    @FXML
    public void initialize() {
        System.out.println("Done");
    }

    /**
     * Returns the player.
     * @return returns player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets player.
     * @param player player to be set to.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Creates a custom maze with 4 rooms, with monsters with a
     * guaranteed drop of each item for testing
     * @return custom Maze for testing purposes
     */
    private Maze customMaze() {
        StartingRoom s = new StartingRoom();
        Monster nMonster = new Monster(new Item[]{new HealthPotion(20), new SuperSayanBow()});
        Monster sMonster = new Monster(new Item[]{new SpeedPotion(), new TrashLid()});
        Monster eMonster = new Monster(new Item[]{new AttackPotion(15), new BaseballBat()});
        Monster wMonster = new Monster(new Item[]{new HealthPotion(20), new FryingPan()});

        s.setConnectingRoom(Direction.NORTH, new Room(new int[]{1, 0}, nMonster));
        s.setConnectingRoom(Direction.SOUTH, new Room(new int[]{-1, 0}, sMonster));
        s.setConnectingRoom(Direction.EAST, new Room(new int[]{0, 1}, eMonster));
        s.setConnectingRoom(Direction.WEST, new ChallengeRoom(new int[]{0, -1}));
        if (s.getConnectingRoom(Direction.NORTH) == null) {
            System.out.println("NO");
        }
        Maze m = new Maze();
        m.setStartingRoom(s);
        return m;
    }
}
