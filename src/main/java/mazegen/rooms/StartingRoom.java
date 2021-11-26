package src.main.java.mazegen.rooms;

public class StartingRoom extends Room {

    /**
     * Constructs Starting Room, at 0, 0/
     */
    public StartingRoom() {
        super(new int[]{0, 0});
        setVisited(true);
    }

    /**
     * returns "V to represent startingRoom in printMaze method
     * @return "V" for StartingRoom
     */
    @Override
    public String toString() {
        return "V";
    }
}
