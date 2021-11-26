package src.main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import src.main.java.entity.Player;
import src.main.java.enums.Difficulty;
import src.main.java.item.weapon.BaseballBat;
import src.main.java.item.weapon.FryingPan;
import src.main.java.item.weapon.TrashLid;
import src.main.java.item.weapon.Weapon;

import java.io.IOException;

public class InitialConfigScreenController {

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
    private Player player;

    private int amountOfGold = 300;
    private String difficultyLevel = "EASY";
    private String name;
    private String weapon = "Baseball Bat";



    /**
     * Changes difficulty level on click of button.
     * @param event click of radial buttons.
     * @throws IOException exception for when the InitialConfigScreen.fxml is not found.
     */
    @FXML
    public void onClickDifficulty(ActionEvent event) throws IOException {
        RadioButton sourceButton = (RadioButton) event.getSource();
        difficultyLevel = sourceButton.getText().toUpperCase();
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
        name = nameTextField.getText().trim();
        if (name.trim().length() != 0 && name != null) {
            difficultyLevel.toUpperCase();
            player = new Player(Difficulty.valueOf(difficultyLevel), createWeapon(weapon), name);
            System.out.println(name);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/src/main/resources/screens/initialGameScreen.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            ((DungeonScreenController) loader.getController()).initializeStartingRoom(player);
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
        if (name == "Baseball Bat") {
            wpn = new BaseballBat();
        } else if (name == "Frying Pan") {
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

}
