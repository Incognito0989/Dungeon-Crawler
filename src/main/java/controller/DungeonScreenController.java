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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import src.main.java.entity.Player;
import src.main.java.entity.monster.Monster;
import src.main.java.enums.Direction;
import src.main.java.item.Item;
import src.main.java.item.potion.AttackPotion;
import src.main.java.item.potion.HealthPotion;
import src.main.java.item.potion.Potion;
import src.main.java.item.potion.SpeedPotion;
import src.main.java.item.weapon.BaseballBat;
import src.main.java.item.weapon.FryingPan;
import src.main.java.item.weapon.TrashLid;
import src.main.java.mazegen.Maze;
import src.main.java.mazegen.rooms.Room;

import java.io.IOException;
import java.util.Random;


public class DungeonScreenController {

    @FXML private ImageView background;
    @FXML private ImageView monsterEncounter;
    @FXML private ImageView potionDrop;
    @FXML private ImageView weaponDrop;
    @FXML private Label goldAmount;
    @FXML private Label name;
    @FXML private Label weapon;
    @FXML private Button menuButton;
    @FXML private Circle characterShape;
    @FXML private Label walterHPLabel;
    @FXML private Label playerHPLabel;
    @FXML private ImageView itemOne;
    @FXML private ImageView itemTwo;
    @FXML private ImageView itemThree;
    @FXML private ImageView itemFour;
    @FXML private ImageView itemFive;
    @FXML private ImageView itemSix;
    @FXML private AnchorPane pane;
    @FXML private Button exitButton;
    @FXML private ProgressBar walterHP;
    @FXML private ProgressBar playerHP;
    @FXML private Rectangle doorEast;
    @FXML private Rectangle doorWest;
    @FXML private Rectangle doorNorth;
    @FXML private Rectangle doorSouth;
    @FXML private Rectangle[] doors;
    @FXML private TextArea itemOneDesc;
    @FXML private TextArea itemTwoDesc;
    @FXML private TextArea itemThreeDesc;
    @FXML private TextArea itemFourDesc;
    @FXML private TextArea itemFiveDesc;
    @FXML private TextArea itemSixDesc;
    @FXML private VBox playerMove;
    @FXML private Label roomCoord;
    @FXML private Label exitCoord;

    private Stage currentStage;
    private static Player player;
    private Room room;
    private Room exitRoom;
    private Maze maze;

    /**
     * movement of the character using the wasd and other button events.
     * @param event press wasd or other buttons.
     * @throws IOException if can't locate image files for backgrounds or weapons
     */
    @FXML
    public void onDirectionKeyEnter(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.B && potionDrop != null) { //pick up item closest to player
            double xPlayer = playerMove.getTranslateX();
            double yPlayer = playerMove.getTranslateY();
            double xPotion = potionDrop.getTranslateX();
            double yPotion = potionDrop.getTranslateY();
            //if (Math.abs(xPlayer - xPotion) <= 50 && Math.abs(yPlayer - yPotion) <= 50) {
            //}
        }
        if (event.getCode() == KeyCode.P && room.getMonster() != null) { //an extra button that
            // can be used to attack monster
            double xPlayer = playerMove.getTranslateX();
            double yPlayer = playerMove.getTranslateY();
            double xWalter = monsterEncounter.getTranslateX();
            double yWalter = monsterEncounter.getTranslateY();
            int range = player.getWeapon().getRange();
            if (Math.abs(xPlayer - xWalter) <= range && Math.abs(yPlayer - yWalter) <= range) {
                playerAttackHelper(event);
            }
        }
        if (event.getCode() == KeyCode.W) {
            System.out.println("W");
            playerMove.setTranslateY(playerMove.getTranslateY() - player.getSpeed());
        } else if (event.getCode() == KeyCode.A) {
            System.out.println("A");
            playerMove.setTranslateX(playerMove.getTranslateX() - player.getSpeed());
        } else if (event.getCode() == KeyCode.D) {
            System.out.println("D");
            playerMove.setTranslateX(playerMove.getTranslateX() + player.getSpeed());
        } else if (event.getCode() == KeyCode.S) {
            System.out.println("S");
            playerMove.setTranslateY(playerMove.getTranslateY() + player.getSpeed());
        }
        double x = playerMove.getTranslateX();
        double y = playerMove.getTranslateY();
        if (!doorWest.isDisabled() && y <= 45 && x <= -315 && y >= -100) {
            room = room.moveConnectingRoom(Direction.valueOf("WEST"));
            loadNewRoom();
            playerMove.setTranslateX(-playerMove.getTranslateX() - 10);
        } else if (!doorEast.isDisabled() && y <= 45 && x >= 315 && y >= -100) {
            room = room.moveConnectingRoom(Direction.valueOf("EAST"));
            loadNewRoom();
            playerMove.setTranslateX(-playerMove.getTranslateX() + 10);
        } else if (!doorNorth.isDisabled() && x >= -65 && x <= 70 && y <= -290) {
            room = room.moveConnectingRoom(Direction.valueOf("NORTH"));
            loadNewRoom();
            playerMove.setTranslateY(-playerMove.getTranslateY() - 70);
        } else if (!doorSouth.isDisabled() && x >= -65 && x <= 70 && y >= 245) {
            room = room.moveConnectingRoom(Direction.valueOf("SOUTH"));
            loadNewRoom();
            playerMove.setTranslateY(-playerMove.getTranslateY() + 10);
        }
        System.out.println("X: " + x + "\nY: " + y);
    }
    /**
     * Sets maze to random maze with center as starting room.
     */
    public void setRoom() {
        this.maze = new Maze();
        itemOne.setImage(new Image(player.getWeapon().getImage()));
        itemOne.setDisable(false);
        itemOneDesc.setPromptText(player.getWeapon().getDescription());
        room = maze.getStartingRoom();
        maze.printMaze();
        roomCoord.setText("Room: " + room.getCoords()[0] + " " + room.getCoords()[1]);
        exitRoom = maze.getExitRoom();
        exitCoord.setText("Exit: " + exitRoom.getCoords()[0] + " " + exitRoom.getCoords()[1]);
    }



    /**
     * Changes the initial gold amount.
     * @param amountOfGold amount of gold the player has.
     */
    @FXML
    public void changeInitialGold(int amountOfGold) {
        goldAmount.setText("Gold: " + amountOfGold);
    }

    /**
     * Sets the current stage for ease of getting stage.
     * @param stage current stage.
     */
    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }
    /**
     * Changes the name displayed
     * @param name name of the player
     */
    @FXML
    public void setName(String name) {
        this.name.setText("Name: " + name);
    }

    /**
     * Sets the player.
     * @param player new player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Changes the weapon displayed in the screen
     * @param weaponName name of the weapon the player chose
     */
    @FXML
    public void setWeapon(String weaponName) {
        weapon.setText("Weapon: " + weaponName);
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
     * Goes to new room when a door button is clicked
     * @param event click on a door button
     */
    @FXML
    public void onDoorClick(MouseEvent event) {
        System.out.println("Yo!");
        String dir = ((Rectangle) event.getTarget()).getId().substring(4).toUpperCase();
        room = room.moveConnectingRoom(Direction.valueOf(dir));
        loadNewRoom();
    }

    /**
     * event for when the character exits the dungeon.
     * @param event click on exit door.
     * @throws IOException thrown when next screen file not found.
     */
    @FXML
    public void onExitDungeon(MouseEvent event) throws IOException {
        System.out.print("clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/main/resources/"
                + "screens/finalScreen.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    /**
     * event for clicking the win button at the end that closes application.
     * @param event click on win button.
     */
    @FXML
    public void onWinButtonClicked(MouseEvent event) {
        System.out.print("clicked");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Sets doors, monsters, and health bars of new room when entering
     */
    private void loadNewRoom() {
        player.getWeapon().setDamage(player.getWeapon().getDamageOriginal());
        setAvailableMovementButtons();
        background.setImage(new Image(room.getBackground()));
        if (room.getMonster() != null) {
            enableMonsterEncounter();
            disableMovementButtons(true);
        } else {
            disableMovementButtons(false);
            disableMonsterEncounter();
        }

    }

    /**
     * Changes which doors are available based on which room we are in
     */
    private void setAvailableMovementButtons() {
        roomCoord.setText("Room: " + room.getCoords()[0] + " " + room.getCoords()[1]);
        if (room.getPotion() != null) {
            potionDrop.setVisible(true);
            potionDrop.setDisable(false);
            potionDrop.setImage(new Image(room.getPotion().getPotionImage()));
        } else {
            potionDrop.setVisible(false);
            potionDrop.setDisable(true);
        }
        if (room.getWeapon() != null) {
            weaponDrop.setVisible(true);
            weaponDrop.setDisable(false);
            weaponDrop.setImage(new Image(room.getWeapon().getImage()));
        } else {
            weaponDrop.setVisible(false);
            weaponDrop.setDisable(true);
        }
        if (room.getCoords().equals(exitRoom.getCoords())) {
            exitButton.setVisible(true);
            exitButton.setDisable(false);
        } else {
            exitButton.setVisible(false);
            exitButton.setDisable(true);
        }
        for (Direction d: Direction.values()) {
            Room adjRoom = room.getConnectingRoom(d);
            if (adjRoom == null) {
                doors[d.ordinal()].setVisible(false);
                doors[d.ordinal()].setDisable(true);
            } else {
                doors[d.ordinal()].setVisible(true);
                doors[d.ordinal()].setDisable(false);
            }
        }
        System.out.println(room.getCoords()[0] + " " + room.getCoords()[1]);
    }

    /**
     * disables doors if the monster is alive, else does opposite.
     * @param monsterIsAlive true if monster is alive in room.
     */
    private void disableMovementButtons(boolean monsterIsAlive) {
        if (monsterIsAlive) {
            for (Direction dir: Direction.values()) {
                Rectangle r = doors[dir.ordinal()];
                if (r.isVisible() && !room.getConnectingRoom(dir).getVisited()) {
                    r.setDisable(true);
                    r.setOpacity(0.5);
                }
            }
        } else {
            for (Rectangle r: doors) {
                if (r.isVisible() && r.getOpacity() == 0.5) {
                    r.setDisable(false);
                    r.setOpacity(1);
                }
            }
        }
    }

    /**
     * helper method for loading monster when monster is in room.
     * @param imageURL file location of monster image.
     */
    private void enableMonsterEncounter(String imageURL) {
        this.room.getMonster().getHealth();
        walterHP.setVisible(true);
        walterHP.setDisable(false);
        walterHP.setProgress(this.room.getMonster().getHealth());
        monsterEncounter.setImage(new Image(imageURL));
        monsterEncounter.setVisible(true);
        monsterEncounter.setDisable(false);
    }

    /**
     * Enables, makes visible, and sets ImageView of monster
     */
    private void enableMonsterEncounter() {
        walterHP.setVisible(true);
        walterHP.setDisable(false);
        Monster monster = this.room.getMonster();
        int health = monster.getHealth();
        int maxHealth = monster.getMaxHealth();
        walterHPLabel.setVisible(true);
        walterHPLabel.setDisable(false);
        walterHP.setProgress((double) health / maxHealth);
        walterHPLabel.setText(String.format("Enemy HP: %d / %d", health, maxHealth));
        monsterEncounter.setImage(new Image(monster.getImageURL()));
        monsterEncounter.setVisible(true);
        monsterEncounter.setDisable(false);
    }

    /**
     * Disables and makes invisible monsterEncounter, it's HP Bar and HP text
     */
    private void disableMonsterEncounter() {
        monsterEncounter.setVisible(false);
        monsterEncounter.setDisable(true);
        walterHPLabel.setDisable(true);
        walterHPLabel.setVisible(false);
        walterHP.setVisible(false);
        walterHP.setDisable(true);
    }

    /**
     * initializes the first room.
     * Changes of the HP bar and text based on the player's health and maxHealth
     * @param player Player whose HP bar is being changed
     */
    public void changeHPBar(Player player) {
        int health = player.getHealth();
        int maxHealth = player.getOriginalHealth();
        playerHP.setProgress((double) health / maxHealth);
        playerHPLabel.setText(String.format("Your HP: %d / %d", health, maxHealth));
    }

    /**
     * Changes the HP bar and text based on monster's health and maxHealth.
     * @param monster monster whose HP bar is being changed
     */
    private void changeHPBar(Monster monster) {
        int health = monster.getHealth();
        int maxHealth = monster.getMaxHealth();
        walterHP.setProgress((double) health / maxHealth);
        walterHPLabel.setText(String.format("Enemy HP: %d / %d", health, maxHealth));
    }

    /**
     * sets doors as an array of rectangles of each door on initialization
     */
    @FXML
    public void initialize() {
        doors = new Rectangle[]{doorNorth, doorEast, doorSouth, doorWest};
    }

    /**
     * basic method to show that maze has been initialized.
     * @param event click on the enemy ImageView
     */
    @FXML
    public void onClickEnemy(MouseEvent event) {
        this.room.setMonster(null);
        disableMonsterEncounter();
        disableMovementButtons(false);
    }

    /**
     * implementation for when player attacks monster and is within range..
     * @param event click on monster ImageView.
     * @throws IOException if screen file not found.
     */
    @FXML
    public void playerAttacks(MouseEvent event) throws IOException {
        if (room.getMonster() != null) {
            double xPlayer = playerMove.getTranslateX();
            double yPlayer = playerMove.getTranslateY();
            double xWalter = monsterEncounter.getTranslateX();
            double yWalter = monsterEncounter.getTranslateY();
            if (Math.abs(xPlayer - xWalter) <= 80 && Math.abs(yPlayer - yWalter) <= 80) {
                playerAttackHelper(event);
            }
        }
    }

    /**
     * helper method to implement player attacking monster.
     * @param event an event.
     * @throws IOException if the loser screen is not found.
     */
    private void playerAttackHelper(Event event) throws IOException {
        Monster monster = room.getMonster();
        monster.setHealth(monster.getHealth() - player.getWeapon().getDamage());
        changeHPBar(monster);
        if (monster.getHealth() <= 0) { //monster dies
            player.setGold(room.getMonster().getGoldDrop() + player.getGold());
            goldAmount.setText("Gold: " + player.getGold());
            drop();
            room.setMonster(null);
            walterHP.setDisable(true);
            disableMonsterEncounter();
            disableMovementButtons(false);
            return;
        }
        int newHealth = player.getHealth();
        double defense = (1 - player.getWeapon().getDefense() / 100);
        newHealth -= (int) Math.ceil(monster.getDamage() * defense);
        player.setHealth(newHealth);
        changeHPBar(player);
        if (player.getHealth() <= 0) { //player dies
            player.setHealth(0);
            Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/"
                    + "screens/loserScreen.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene loserScreen = new Scene(root, 1200, 700);
            stage.setTitle("Loser");
            stage.setScene(loserScreen);
        }
    }

    /**
     * Initializes starting Room
     * @param player Player in the room
     */
    public void initializeStartingRoom(Player player) {
        this.player = player;
        name.setText("Name: " + player.getName());
        weapon.setText("Weapon: " + player.getWeapon().getName());
        goldAmount.setText("Gold: " + player.getGold());
        playerHP.setProgress(1.0);
        playerHPLabel.setText(String.format("Your HP: %d/%d", player.getHealth(),
                player.getOriginalHealth()));
        setRoom();
    }

    /**
     * helper method to deal with what item is dropped randomly.
     */
    private void drop() {
        Random rand = new Random();
        int dum = rand.nextInt((int) (10 * (1 - room.getMonster().getPotionDrop())));
        int dum2 = rand.nextInt((int) (10 * (1 - room.getMonster().getPotionDrop())));
        if (dum == dum2) { //drop potion
            potionDrop.setTranslateX(rand.nextInt(200 + 200) + -200);
            potionDrop.setTranslateY(rand.nextInt(155 + 215) - 215);
            potionDrop.setVisible(true);
            potionDrop.setDisable(false);
            int randp = rand.nextInt(3); //number of potions is the bound
            String str1 = new String();
            switch (randp) {
            case(0):
                HealthPotion heal = new HealthPotion(20);
                str1 = heal.getPotionImage();
                room.setPotion(heal);
                break;
            case(1):
                AttackPotion attack = new AttackPotion(15);
                str1 = attack.getPotionImage();
                room.setPotion(attack);
                break;
            case(2):
                SpeedPotion sped = new SpeedPotion();
                str1 = sped.getPotionImage();
                room.setPotion(sped);
                break;
            default: break;
            }
            potionDrop.setImage(new Image(str1));
        }
        dum = rand.nextInt((int) (10 * (1 - room.getMonster().getWeaponDrop())));
        dum2 = rand.nextInt((int) (10 * (1 - room.getMonster().getWeaponDrop())));
        if (dum == dum2) { //drop weapon
            weaponDrop.setTranslateX(rand.nextInt(200 + 200) + -200);
            weaponDrop.setTranslateY(rand.nextInt(155 + 215) - 215);
            weaponDrop.setVisible(true);
            weaponDrop.setDisable(false);
            int randp = rand.nextInt(3);
            String str1 = new String();
            switch (randp) {
            case(0):
                FryingPan pan = new FryingPan();
                str1 = pan.getImage();
                room.setWeapon(pan);
                break;
            case(1):
                BaseballBat bat = new BaseballBat();
                str1 = bat.getImage();
                room.setWeapon(bat);
                break;
            case(2):
                TrashLid lid = new TrashLid();
                str1 = lid.getImage();
                room.setWeapon(lid);
                break;
            default: break;
            }
            weaponDrop.setImage(new Image(str1));
        }
    }

    /**
     * Method for when the items dropped are clicked on.
     * @param event item clicked.
     */
    @FXML
    public void onItemDropClick(MouseEvent event) {
        if (event.getSource().equals(potionDrop)) {
            int n = player.getInventory().add(this.room.getPotion());
            switch (n) {
            case(1):
                itemTwo.setImage(new Image(room.getPotion().getPotionImage()));
                itemTwo.setDisable(false);
                potionDrop.setVisible(false);
                potionDrop.setDisable(true);
                break;
            case(2):
                itemThree.setImage(new Image(room.getPotion().getPotionImage()));
                itemThree.setDisable(false);
                potionDrop.setVisible(false);
                potionDrop.setDisable(true);
                break;
            case(3):
                itemFour.setImage(new Image(room.getPotion().getPotionImage()));
                itemFour.setDisable(false);
                potionDrop.setVisible(false);
                potionDrop.setDisable(true);
                break;
            case(4):
                itemFive.setImage(new Image(room.getPotion().getPotionImage()));
                itemFive.setDisable(false);
                potionDrop.setVisible(false);
                potionDrop.setDisable(true);
                break;
            case(5):
                itemSix.setImage(new Image(room.getPotion().getPotionImage()));
                itemSix.setDisable(false);
                potionDrop.setVisible(false);
                potionDrop.setDisable(true);
                break;
            default: break;
            }
            room.setPotion(null);
        }
        if (event.getSource().equals(weaponDrop)) {
            player.getInventory().remove(0);
            player.getInventory().add(room.getWeapon());
            itemOne.setImage(new Image(room.getWeapon().getImage()));
            weaponDrop.setVisible(false);
            weaponDrop.setDisable(true);
            player.setWeapon(room.getWeapon());
            weapon.setText("Weapon: " + player.getWeapon().getName());
            itemOne.setDisable(false);
            room.setWeapon(null);
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
                playerHP.setProgress(player.getHealth() / player.getOriginalHealth());
                changeHPBar(player);
            } else {
                player.setHealth(player.getOriginalHealth());
            }
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
}

