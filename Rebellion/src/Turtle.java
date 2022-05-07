/**
 * Representation of a single actor (cop or agent) on the model board
 */
public abstract class Turtle {

    protected int location_x;
    protected int location_y;
    protected int vision;

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
