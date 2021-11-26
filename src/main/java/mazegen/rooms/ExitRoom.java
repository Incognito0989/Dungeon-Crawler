package src.main.java.mazegen.rooms;

import src.main.java.entity.monster.FinalBoss;

public class ExitRoom extends Room {

    /**
     * Constructor for the ExitRoom with given coordinates
     * @param coords coordinates for ExitRoom
     */
    public ExitRoom(int[] coords) {
        super(coords, new FinalBoss());
    }

    /**
     * Represents Exit Room as "E" for printMaze() method
     * @return "E" for ExitRoom
     */
    @Override
    public String toString() {
        return "E";
    }
}
