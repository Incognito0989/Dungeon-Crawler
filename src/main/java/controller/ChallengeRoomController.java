package src.main.java.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import src.main.java.entity.Player;
import src.main.java.entity.monster.Monster;
import src.main.java.item.Item;
import src.main.java.item.potion.AttackPotion;
import src.main.java.item.potion.HealthPotion;
import src.main.java.item.potion.Potion;
import src.main.java.item.potion.SpeedPotion;
import src.main.java.item.weapon.Weapon;
import src.main.java.mazegen.rooms.ChallengeRoom;
import src.main.java.mazegen.rooms.ExitRoom;

import java.io.IOException;

public class ChallengeRoomController {
    @FXML private ImageView background;
    @FXML private VBox monster1;
    @FXML private VBox monster2;
    @FXML private VBox monster3;
    @FXML private VBox[] monsters = {monster1, monster2, monster3};


    @FXML private Label goldAmount;
    @FXML private Label name;
    @FXML private Label weapon;
    @FXML private Button menuButton;
    @FXML private Circle characterShape;
    @FXML private Label playerHPLabel;
    @FXML private ProgressBar playerHP;
    @FXML private ImageView itemOne;
    @FXML private ImageView itemTwo;
    @FXML private ImageView itemThree;
    @FXML private ImageView itemFour;
    @FXML private ImageView itemFive;
    @FXML private ImageView itemSix;
    @FXML private ImageView[] items = {itemOne, itemTwo, itemThree, itemFour, itemFive, itemSix};

    @FXML private TextArea itemOneDesc;
    @FXML private TextArea itemTwoDesc;
    @FXML private TextArea itemThreeDesc;
    @FXML private TextArea itemFourDesc;
    @FXML private TextArea itemFiveDesc;
    @FXML private TextArea itemSixDesc;
    @FXML private TextArea[] itemDescs = {itemOneDesc, itemTwoDesc,
        itemThreeDesc, itemFourDesc, itemFiveDesc, itemSixDesc};

    @FXML private ImageView drop1;
    @FXML private ImageView drop2;
    @FXML private ImageView drop3;
    @FXML private ImageView[] drops = {drop1, drop2, drop3};

    @FXML private Button returnButton;

    private ChallengeRoom room;
    private ExitRoom exitRoom;
    private static Player player;
    private int targetedMonster;

    /**
     * movement of the character using the wasd and other button events.
     * @param event press wasd or other buttons.
     * @throws IOException if image url for potion/weapon is null
     */
    @FXML
    public void onWasd(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.P) {
            playerAttackHelper();
        }
        if (event.getCode() == KeyCode.A) {
            System.out.println("A");
            changeTargetedMonster(false);
        } else if (event.getCode() == KeyCode.D) {
            System.out.println("D");
            changeTargetedMonster(true);
        }
    }

    @FXML
    public void playerAttacks(MouseEvent event) throws IOException {
        VBox monsterBox = (VBox) ((Node) event.getSource()).getParent();
        for (int i = 0; i < 3; i++) {
            if (monsterBox == monsters[i]) {
                targetedMonster = i;
                break;
            }
        }
        for (VBox monster: monsters) {
            monster.setStyle("");
        }
        monsters[targetedMonster].setStyle("-fx-border-color:red");
        playerAttackHelper();
    }

    /**
     * Goes back to the welcome screen when click on Menu button
     * @param mouseEvent click of button by mouse
     * @throws IOException exception for when welcome.fxml screen is not found
     */
    @FXML
    public void onClickExit(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/"
                + "screens/welcome.fxml"));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene welcomeScreen = new Scene(root, 700, 600);
        stage.setTitle("Dungeon Crawler");
        stage.setScene(welcomeScreen);
    }

    /**
     * shows the item description when the mouse hovers over the item in inventory.
     * @param event hover mouse over item in inventory.
     */
    @FXML
    public void onItemDesc(MouseEvent event) {
        if (event.getSource() == itemOne) {
            itemOneDesc.setText(player.getWeapon().getName() + "\n"
                    + player.getWeapon().getDescription());
            itemOneDesc.setVisible(true);
        } else if (event.getSource() == itemTwo) {
            Potion pp = ((Potion) (player.getInventory().get(1)));
            itemTwoDesc.setText(pp.getPotionName() + "\n" + pp.getPotionDescription());
            itemTwoDesc.setVisible(true);
        } else if (event.getSource() == itemThree) {
            Potion pp = ((Potion) (player.getInventory().get(2)));
            itemThreeDesc.setText(pp.getPotionName() + "\n" + pp.getPotionDescription());
            itemThreeDesc.setVisible(true);
        } else if (event.getSource() == itemFour) {
            Potion pp = ((Potion) (player.getInventory().get(3)));
            itemFourDesc.setText(pp.getPotionName() + "\n" + pp.getPotionDescription());
            itemFourDesc.setVisible(true);
        } else if (event.getSource() == itemFive) {
            Potion pp = ((Potion) (player.getInventory().get(4)));
            itemFiveDesc.setText(pp.getPotionName() + "\n" + pp.getPotionDescription());
            itemFiveDesc.setVisible(true);
        } else {
            Potion pp = ((Potion) (player.getInventory().get(5)));
            itemSixDesc.setText(pp.getPotionName() + "\n" + pp.getPotionDescription());
            itemSixDesc.setVisible(true);
        }
    }

    /**
     * event to make item description go away when not hovering over item.
     * @param event stop hovering over item.
     */
    @FXML
    public void onItemDescOff(MouseEvent event) {
        if (event.getSource() == itemOne) {
            itemOneDesc.setVisible(false);
        } else if (event.getSource() == itemTwo) {
            itemTwoDesc.setVisible(false);
        } else if (event.getSource() == itemThree) {
            itemThreeDesc.setVisible(false);
        } else if (event.getSource() == itemFour) {
            itemFourDesc.setVisible(false);
        } else if (event.getSource() == itemFive) {
            itemFiveDesc.setVisible(false);
        } else {
            itemSixDesc.setVisible(false);
        }
    }

    /**
     * event for using items in the inventory.
     * @param event click on inventory item.
     */
    @FXML
    public void onInventoryClick(MouseEvent event) {
        if (event.getSource() == itemTwo) {
            inventoryHelper(itemTwo, 1);
        } else if (event.getSource() == itemThree) {
            inventoryHelper(itemThree, 2);
        } else if (event.getSource() == itemFour) {
            inventoryHelper(itemFour, 3);
        } else if (event.getSource() == itemFive) {
            inventoryHelper(itemFive, 4);
        } else if (event.getSource() == itemSix) {
            inventoryHelper(itemSix, 5);
        }
    }

    /**
     * helper method to determine what potion is being used and gives player those effects.
     * @param e the ImageView of the inventory slot the item is in.
     * @param index the index the item is in in the player array.
     */
    private void inventoryHelper(ImageView e, int index) {
        Item i = player.getInventory().remove(index);
        if (i == null) {
            return;
        }
        if (i instanceof HealthPotion) {
            if (player.getOriginalHealth() - player.getHealth()
                    > ((HealthPotion) i).getHealthRegen()) {
                player.setHealth(player.getHealth() + ((HealthPotion) i).getHealthRegen());
            } else {
                player.setHealth(player.getOriginalHealth());
            }
            playerHP.setProgress((double) player.getHealth() / player.getOriginalHealth());
            playerHPLabel.setText("Your HP: " + player.getHealth() + " / "
                    + player.getOriginalHealth());
        } else if (i instanceof AttackPotion) {
            player.getWeapon().setDamage(player.getWeapon().getDamage()
                    + ((AttackPotion) i).getDamageBuff());
        } else if (i instanceof SpeedPotion) {
            if (player.getSpeed() <= 10) {
                player.setSpeed(player.getSpeed() + ((SpeedPotion) i).getSpeedIncrease());
            }
        }
        e.setDisable(true);
        e.setImage(new Image("src/main/resources/images/screens/background.png")); //slot base image
    }

    /**
     * Method for when the items dropped are clicked on.
     * @param event item clicked.
     */
    @FXML
    public void onItemDropClick(MouseEvent event) {
        System.out.println("pop");
        int dropNum = 0;
        for (int i = 0; i < 3; i++) {
            if (event.getSource() == drops[i]) {
                dropNum = i;
                break;
            }
        }
        Item item = room.getDrops()[dropNum];
        if (item instanceof Potion) {
            Potion p = (Potion) item;
            int n = player.getInventory().add(item);
            items[n].setImage(new Image(p.getPotionImage()));
            items[n].setDisable(false);
        } else if (item instanceof Weapon) {
            player.getInventory().remove(0);
            player.getInventory().add(item);
            player.setWeapon((Weapon) item);
            itemOne.setImage(new Image(((Weapon) item).getImage()));
            itemOne.setDisable(false);
        }
        room.setDrops(null, dropNum);
        ((ImageView) event.getSource()).setDisable(true);
        ((ImageView) event.getSource()).setVisible(false);
    }

    private void changeTargetedMonster(boolean right) {
        int direction = (right) ? 1 : -1;
        do {
            targetedMonster += direction;
            targetedMonster = Math.floorMod(targetedMonster, 3);
        } while (room.getMonsters()[targetedMonster] == null);
        for (VBox monster: monsters) {
            monster.setStyle("");
        }
        monsters[targetedMonster].setStyle("-fx-border-color:red");
    }


    public void setScene(Player player, ChallengeRoom room, ExitRoom exitRoom) {
        this.player = player;
        this.room = room;
        this.exitRoom = exitRoom;
        targetedMonster = 1;
        goldAmount.setText("Gold: " + player.getGold());
        name.setText("Name: " + player.getName());
        weapon.setText("Weapon: " + player.getWeapon().getName());
        for (int i = 0; i < 3; i++) {
            updateMonster(monsters[i], room.getMonsters()[i]);
        }
        playerHP.setProgress(((double) player.getHealth() / player.getOriginalHealth()));
        playerHPLabel.setText("Your HP: " + player.getHealth() + " / "
                + player.getOriginalHealth());
        itemOne.setImage(new Image(player.getWeapon().getImage()));
        itemOne.setDisable(false);
        itemOneDesc.setPromptText(player.getWeapon().getDescription());
        for (int i = 1; i < 6; i++) {
            Potion p = (Potion) player.getInventory().get(i);
            if (p == null) {
                return;
            }
            items[i].setImage(new Image(p.getPotionImage()));
            items[i].setDisable(false);
            itemDescs[i].setPromptText(p.getPotionDescription());
        }

    }

    private void updateMonster(VBox monsterBox, Monster monster) {
        if (monster == null) {
            monsterBox.setVisible(false);
            monsterBox.setDisable(true);
        } else {
            System.out.println(monsterBox);
            ImageView image = (ImageView) monsterBox.getChildren().get(0);
            image.setImage(new Image(monster.getImageURL()));
            ProgressBar hpBar = (ProgressBar) monsterBox.getChildren().get(1);
            hpBar.setProgress(((double) monster.getHealth() / monster.getMaxHealth()));
            Label hpLabel = (Label) monsterBox.getChildren().get(2);
            hpLabel.setText("Enemy HP: " + monster.getHealth() + " / " + monster.getMaxHealth());
        }
    }

    private void playerAttackHelper() throws IOException {
        Monster monster = room.getMonsters()[targetedMonster];
        if (monster == null) {
            return;
        }
        monster.setHealth(monster.getHealth() - player.getWeapon().getDamage());
        player.setDamageDealt(player.getDamageDealt() + player.getWeapon().getDamage());
        updateMonster(monsters[targetedMonster], monster);
        if (monster.getHealth() <= 0) { //monster dies
            room.setMonster(null, targetedMonster);
            updateMonster(monsters[targetedMonster], null);
            player.setMonstersKilled(player.getMonstersKilled() + 1);
        }
        if (room.allMonstersDead()) {
            returnButton.setDisable(false);
            returnButton.setVisible(true);
            drop();
            return;
        }
        int newHealth = player.getHealth();
        double defense = (1 - player.getWeapon().getDefense() / 100);
        int damage = 0;
        for (Monster m: room.getMonsters()) {
            if (m != null) {
                damage += m.getDamage();
            }
        }
        newHealth -= (int) Math.ceil(damage * defense);
        player.setHealth(newHealth);
        playerHP.setProgress((double) newHealth / player.getOriginalHealth());
        playerHPLabel.setText("Your HP: " + newHealth + " / " + player.getOriginalHealth());
        if (player.getHealth() <= 0) { //player dies
            player.setHealth(0);
            Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/"
                    + "screens/loserScreen.fxml"));
            Stage stage = (Stage) (name).getScene().getWindow();
            Scene loserScreen = new Scene(root, 1200, 700);
            stage.setTitle("Loser");
            stage.setScene(loserScreen);
            System.out.println(player.getDamageDealt() + " : " + player.getMonstersKilled());
            ((Label) root.lookup("#damageLabel")).setText("Damage Dealt: "
                    + player.getDamageDealt());
            ((Label) root.lookup("#killsLabel")).setText("Kills: "
                    + player.getMonstersKilled());
            ((Label) root.lookup("#roomsVisitedLabel")).setText("Rooms Visited: "
                    + player.getRoomsVisited());
            ((Label) root.lookup("#goldLabel")).setText("Gold: " + player.getGold());
        }
    }

    @FXML
    public void initialize() {
        monsters = new VBox[]{monster1, monster2, monster3};
        items = new ImageView[]{itemOne, itemTwo,
            itemThree, itemFour, itemFive, itemSix};
        itemDescs = new TextArea[]
            {itemOneDesc, itemTwoDesc, itemThreeDesc, itemFourDesc, itemFiveDesc, itemSixDesc};
        drops = new ImageView[]{drop1, drop2, drop3};
    }


    private void drop() {
        drops[0].getParent().setDisable(false);
        for (int i = 0; i < 3; i++) {

            drops[i].setVisible(true);
            drops[i].setDisable(false);
            Item item = room.getDrops()[i];
            if (item instanceof Potion) {
                drops[i].setImage(new Image(((Potion) item).getPotionImage()));
            } else if (item instanceof Weapon) {
                drops[i].setImage(new Image(((Weapon) item).getImage()));
            }
        }
    }

    @FXML
    public void onReturnClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/main/resources/"
                + "screens/initialGameScreen.fxml"));
        Parent root = loader.load();
        InitialGameScreenController c = loader.getController();
        c.setRoom(room, (ExitRoom) exitRoom);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }



}
