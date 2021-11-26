package src.main.java.mazegen;

import src.main.java.entity.monster.Monster;
import src.main.java.enums.Direction;
import src.main.java.mazegen.rooms.ChallengeRoom;
import src.main.java.mazegen.rooms.ExitRoom;
import src.main.java.mazegen.rooms.Room;
import src.main.java.mazegen.rooms.StartingRoom;

import java.util.Random;

public class Maze {
    private StartingRoom startingRoom;
    private ExitRoom exitRoom;
    private int radius;
    private Room[][] maze;
    private Random rand = new Random();

    /**
     * Constructor for generic Maze with rooms at most 7 rooms away.
     */
    public Maze() {
        this(7);
    }

    /**
     * Constructor for Maze that has rooms in at most radius rooms away
     * @param radius furthest length a room is from the starting room
     */
    public Maze(int radius) {
        this.radius = radius;
        maze = new Room[radius * 2 + 1][radius * 2 + 1];
        setStartingRoom();
        for (int i = 0; i < 3; i++) {
            generatePath(Direction.EAST);
            generatePath(Direction.SOUTH);
            generatePath(Direction.NORTH);
            generatePath(Direction.WEST);
        }
        generatePath();

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                Room r = maze[i][j];
                if (r != null && r.getClass() != ExitRoom.class && r.getNumAdjRooms() == 1) {
                    int[] coords = r.getCoords();
                    setCoords(coords, new ChallengeRoom(coords));
                    printMaze();
                }
            }
        }
        //setStartingRoom();
        //printMaze();
    }

    /**
     * Generates path to the exit room
     */
    public void generatePath() {
        int[] out = new int[2];
        out[0] = (rand.nextInt(radius) + 1) * ((rand.nextBoolean()) ? -1 : 1);
        out[1] = (6 - Math.abs(out[0])) * ((rand.nextBoolean()) ? -1 : 1);
        makeLinearPath(out, 0);
        setExitRoom(out);
    }

    /**
     * Generates path from starting room in a certain direction
     * @param dir Direction path is going in
     */
    public void generatePath(Direction dir) {
        int[] x = generateDeadEnd(dir);
        makeLinearPath(x);
    }

    /**
     * Generates coordinates for place where path ends in a direction from the starting room
     * @param dir Direction
     * @return coordinates for dead end
     */
    public int[] generateDeadEnd(Direction dir) {
        int[] out = new int[2];
        out[0] = rand.nextInt(radius) + 1;
        out[1] = rand.nextInt(out[0]) * (rand.nextBoolean() ? -1 : 1);
        if (dir.ordinal() >= 2) {
            out[0] *= -1;
        }
        if (dir.ordinal() % 2 == 1) {
            int temp = out[0];
            out[0] = out[1];
            out[1] = temp;
        }
        //System.out.println(out[0] + " " + out[1]);
        return out;
    }

    /**
     * Setter for coordinates of room, adds to maze array
     * @param coords coords to set
     * @param room room to set coords to
     */
    private void setCoords(int[] coords, Room room) {
        int x = coords[0];
        int y = coords[1];
        if (Math.abs(x) > radius || Math.abs(y) > radius) {
            return;
        } else {
            maze[x + radius][y + radius] = room;
            room.setCoords(coords);
        }
        connectRooms(room);
    }

    /**
     * sets a regular Room at the coordinates in the maze array
     * @param coords coordinates to set Room to
     */
    private void setRoom(int[] coords) {
        setCoords(coords, new Room(coords, Monster.randomMonster()));
    }

    /**
     * Sets the starting room at coordinates 0, 0.
     */
    private void setStartingRoom() {
        this.startingRoom = new StartingRoom();
        setCoords(new int[]{0, 0}, this.startingRoom);
        setCoords(new int[]{0, 1}, new Room());
        setCoords(new int[]{0, -1}, new Room());
        setCoords(new int[]{1, 0}, new Room());
        setCoords(new int[]{-1, 0}, new Room());
    }

    /**
     * Sets the exit room at the given coordinates
     * @param coords coordinates to set the exit room to
     */
    private void setExitRoom(int[] coords) {
        this.exitRoom = new ExitRoom(coords);
        setCoords(coords, this.exitRoom);
    }

    /**
     * Prints out the maze; 0 are null spaces, _ are regular rooms, V is the starting room
     * E is the exit room
     */
    public void printMaze() {
        String out = "";
        for (Room[] i: maze) {
            String line = "";
            for (Room j: i) {
                if (j == null) {
                    line = line + "0 ";
                } else if (j == startingRoom) {
                    line = line + startingRoom + " ";
                } else if (j == exitRoom) {
                    line = line + exitRoom + " ";
                } else {
                    line = line + j + " ";
                }
            }
            line += "\n";
            out = line + out;
        }
        System.out.println(out);
    }

    /**
     * generates a linear path to the coordinates at the end point.
     * Goes straight in one direction and straight in the other
     * @param end coordinates that the path ends on
     */
    private void makeLinearPath(int[] end) {
        int[] pointer = {0, 0};
        int axis = rand.nextBoolean() ? 0 : 1;
        while (pointer[axis] != end[axis]) {
            pointer[axis] = pointer[axis] + (int) Math.signum(end[axis]);
            setRoom(pointer);
        }
        axis = (axis + 1) % 2;
        while (pointer[axis] != end[axis]) {
            pointer[axis] = pointer[axis] + (int) Math.signum(end[axis]);
            setRoom(pointer);
        }
    }

    /**
     * Generates a linear path that will go in one direction first then another direction
     * @param end coordinates that the path ends on
     * @param firstD direction that the path will start to go
     */
    private void makeLinearPath(int[] end, int firstD) {
        int[] pointer = {0, 0};
        while (pointer[firstD] != end[firstD]) {
            pointer[firstD] = pointer[firstD] + (int) Math.signum(end[firstD]);
            setRoom(pointer);
        }
        firstD = (firstD + 1) % 2;
        while (pointer[firstD] != end[firstD]) {
            pointer[firstD] = pointer[firstD] + (int) Math.signum(end[firstD]);
            setRoom(pointer);
        }
    }

    /**
     * Generates a path that alternates randomly in on direction or other
     * towards the end room
     * @param end coordinates that the path ends on
     */
    private void makeWigglingPath(int[] end) {
        int[] pointer = {0, 0};
        int axis = rand.nextBoolean() ? 0 : 1;
        while (pointer[axis] != end[axis]) {
            axis = (rand.nextBoolean()) ? 0 : 1;
            pointer[axis] = pointer[axis] - (int) Math.signum(end[axis]);
            setRoom(pointer);
        }
        axis = (axis + 1) % 2;
        while (pointer[axis] != end[axis]) {
            pointer[axis] = pointer[axis] + (int) Math.signum(end[axis]);
            setRoom(pointer);
        }
    }

    /**
     * Connects the rooms in the array to each adjacent room to it
     * @param room room that will be connected
     */
    private void connectRooms(Room room) {
        //System.out.println("Room: " + room.getCoords()[0] + " " + room.getCoords()[1]);
        if (room == null) {
            return;
        }
        for (Direction i: Direction.values()) {
            Room nextRoom = getAdjacentRoom(room, i);
            if (nextRoom != null) {
                room.setConnectingRoom(i, nextRoom);
            }
        }
    }

    /**
     * Finds the room next to the given room in the direction given, or returns null
     * if room doesn't exist or is out of bounds
     * @param room room being searched for adjacent rooms
     * @param dir given direction to search for other room
     * @return adjacent room to the room in the given direction
     */
    public Room getAdjacentRoom(Room room, Direction dir) {
        int[] coords = {room.getCoords()[0], room.getCoords()[1]};
        int axis = (dir.ordinal() % 2 == 0) ? 0 : 1;
        int neg = (dir.ordinal() >= 2) ? -1 : 1;
        if (Math.abs(coords[axis] + neg) > radius) {
            return null;
        } else {
            coords[axis] += neg;
            int[] out = {coords[0] + radius, coords[1] + radius};
            Room r = maze[out[0]][out[1]];
            if (r != null) {
                r.setCoords(coords);
                return r;
            }
        }
        return null;
    }

    /**
     * Getter for starting room
     * @return starting room
     */
    public StartingRoom getStartingRoom() {
        return startingRoom;
    }

    /**
     * Setter for starting room
     * @param startingRoom room to set starting room to
     */
    public void setStartingRoom(StartingRoom startingRoom) {
        this.startingRoom = startingRoom;
    }

    /**
     * Getter for exit room
     * @return exit room
     */
    public ExitRoom getExitRoom() {
        return exitRoom;
    }

    /**
     * setter for exit room
     * @param exitRoom room to set exit room to
     */
    public void setExitRoom(ExitRoom exitRoom) {
        this.exitRoom = exitRoom;
    }


}
