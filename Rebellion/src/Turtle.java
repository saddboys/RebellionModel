import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        List<int[]> empty_patch = getEmptyPatch();

        if(empty_patch.size() > 0){
            Random rand = new Random();
            int[] new_coordinate = empty_patch.get(rand.nextInt(empty_patch.size()));
            map[this.location_x][this.location_y] = null;
            this.location_x = new_coordinate[0];
            this.location_y = new_coordinate[1];
            map[this.location_x][this.location_y] = this;
        }
    }

    /**
     *
     * @return A list of coordinate within the vision
     */
    public List<int[]> getVision(){

        Turtle[][] map = Main.model.getTurtleMap();
        int width = map[0].length;
        int height = map.length;

        int x_range_low = this.location_x - this.vision;
        int x_range_high = this.location_x + this.vision;
        int y_range_low = this.location_y - this.vision;
        int y_range_high = this.location_y + this.vision;

        List<int[]> visionPatches = new ArrayList<>();

        for(int i = x_range_low; i <= x_range_high; i++){
            for(int j = y_range_low; j <= y_range_high; j++){
                int currentX = i;
                int currentY = j;
                // Manhattan distance
                if ((Math.abs(this.location_x - i) + Math.abs(this.location_y - j) <= this.vision)) {
                    // wrap around if going out of map
                    if (i < 0) {
                        currentX = width + i;
                    } else if (i >= width) {
                        currentX = i - width ;
                    }
                    if (j < 0){
                        currentY = height + j;
                    } else if (j >= height){
                        currentY = j - height ;
                    }

                    visionPatches.add(new int[] {currentX, currentY});
                }
            }
        }

        return visionPatches;
    }

    /**
     *
     * @return a list of coordinate within the vision and is unoccupied
     */
    public List<int[]> getEmptyPatch(){
        Turtle[][] turtleMap = Main.model.getTurtleMap();
        List<int[]> vision = getVision();
        List<int[]> empty_patch = new ArrayList<>();

        for(int[] coordinate: vision){
            if (turtleMap[coordinate[0]][coordinate[1]] == null){
                empty_patch.add(coordinate);
            }
        }
        return empty_patch;
    }

}
