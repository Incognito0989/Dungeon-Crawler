package src.main.java.mazegen.rooms;

import src.main.java.entity.monster.Monster;
import src.main.java.enums.Direction;
import src.main.java.item.potion.Potion;
import src.main.java.item.weapon.Weapon;

import java.util.Random;

public class Room {

    private Room[] rooms = new Room[4];
    private Monster monster;
    private int[] coords;
    private boolean visited = false;
    private Random rand;
    private Potion potion = null;
    private Weapon weapon = null;
    private String background;

    /**
     * Constructor for generic room at coordinates 0,0.
     */
    public Room() {
        this(new int[]{0, 0});
        monster = Monster.randomMonster();
    }

    /**
     * Constructor for making a room object with specific coordinates and f.
     * @param coords location of room in maze.
     * @param monster random monster in room.
     */
    public Room(int[] coords, Monster monster) {
        this.coords = coords;
        this.monster = monster;
        Random rand = new Random();
        int n = rand.nextInt(4); //bound is the number of backgrounds
        /*
        switch (n) {
        case(0):
            this.background = "images/background.png";
            break;
        case(1):
            this.background = "images/b2.png";
            break;
        case(2):
            this.background = "images/b3.png";
            break;
        case(3):
            this.background = "images/b4.png";
            break;
        default: break;
        }
         */
        this.background = "src/main/resources/images/screens/background.png";
    }
    /**
     * Constructor for room with given coordinates
     * @param coords coordinates for room
     */
    public Room(int[] coords) {
        this.coords = coords;
    }

    /**
     * Getter for coordinates
     * @return coordinates of room
     */
    public int[] getCoords() {
        return this.coords;
    }

    /**
     * Setter for coordinates
     * @param coords coordinates to set the Room's coordinates to
     */
    public void setCoords(int[] coords) {
        this.coords = coords;
    }

    /**
     * Getter for connecting rooms to this room
     * @return array of 4 connecting rooms
     */
    public Room[] getConnectingRooms() {
        return rooms;
    }

    /**
     * Setter for connecting rooms to this room
     * @param rooms array of 4 rooms you want to connect this room to
     */
    public void setConnectingRooms(Room[] rooms) {
        if (rooms.length == 4) {
            this.rooms = rooms;
        }
    }

    /**
     * Finds room connected to this room in a given direction
     * @param dir Direction to find the connecting room
     * @return room connected to this room in the given direction
     */
    public Room getConnectingRoom(Direction dir) {
        return rooms[dir.ordinal()];
    }

    /**
     * Sets room in a given direction to the room given below
     * @param dir direction given room will be in
     * @param room room that will connect to this room
     */
    public void setConnectingRoom(Direction dir, Room room) {
        rooms[dir.ordinal()] = room;
        room.rooms[(dir.ordinal() + 2) % 4] = this;
    }

    /**
     * Sets if the room has been visited before.
     * @param visited if room has been visited.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * getter for if the room has been visited by player.
     * @return true if visited
     */
    public boolean getVisited() {
        return this.visited;
    }

    /**
     * setter for the monster of the room.
     * @param monster monster
     */
    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    /**
     * getter for the monster in the room.
     * @return the monster in the room.
     */
    public Monster getMonster() {
        return this.monster;
    }

    /**
     * Move to a room in a certain direction.
     * @param dir direction of connecting room.
     * @return adjacent room.
     */
    public Room moveConnectingRoom(Direction dir) {
        Room adjRoom = getConnectingRoom(dir);
        if (adjRoom != null) {
            adjRoom.setVisited(true);
        }
        return adjRoom;
    }

    /**
     * returns "_" as for printMaze method
     * @return "_" for regular Room
     */
    @Override
    public String toString() {
        return "_";
    }

    /**
     * getter for the potion in the room;
     * @return rooms potion dropped.
     */
    public Potion getPotion() {
        return potion;
    }

    /**
     * setter for the potion in the room.
     * @param potion potion dropped.
     */
    public void setPotion(Potion potion) {
        this.potion = potion;
    }

    /**
     * getter for the weapon dropped in the room.
     * @return weapon dropped.
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * setter for the weapon dropped in the room.
     * @param weapon weapon dropped.
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * getter for background image.
     * @return string location.
     */
    public String getBackground() {
        return background;
    }

    public int getNumAdjRooms() {
        int out = 0;
        for  (Room r: rooms) {
            if (r != null) {
                out++;
            }
        }
        return out;
    }

}
