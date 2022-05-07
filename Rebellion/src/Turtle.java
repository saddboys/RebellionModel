/**
 * Representation of a single actor (cop or agent) on the model board
 */
public abstract class Turtle {

    private int location_x;
    private int location_y;
    private int vision;

    public Turtle(int vision, int x, int y){
        this.vision = vision;
        this.location_x = x;
        this.location_y = y;
    }

    /**
     * Moves the actor to an empty space in vision range
     */
    public void move(Turtle[][] map){
        // TODO
    }
}
