import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representation of a single actor (cop or agent) on the model board
 */
public abstract class Turtle {

    private int location_x;
    private int location_y;
    protected int vision;

    public Turtle(int vision, int x, int y){
        this.vision = vision;
        this.location_x = x;
        this.location_y = y;
    }

    public int getLocation_x() {
        return location_x;
    }

    public int getLocation_y() {
        return location_y;
    }

    public void setLocation(int location_x, int location_y, Turtle[][] map) {
        this.location_x = location_x;
        this.location_y = location_y;
        map[location_x][location_y] = this;
    }

    /**
     * Moves the actor to an empty space in vision range
     */
    public void move(Turtle[][] map){
        List<int[]> empty_patch = getEmptyPatch(map);

        if(empty_patch.size() > 0){
            Random rand = new Random();
            int[] new_coordinate = empty_patch.get(rand.nextInt(empty_patch.size()));
            setLocation(new_coordinate[0], new_coordinate[1], map);
        }
    }

    /**
     *
     * @return A list of coordinate within the vision
     */
    public List<int[]> getVision(Turtle[][] map){

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

                // calculate direct line distance
                double distance = Math.sqrt(Math.pow(currentY - this.location_y, 2) + (Math.pow(currentX - this.location_x, 2)));
                if (distance <= vision) {
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
                    // wrap around if going out of map
                    visionPatches.add(new int[]{currentX, currentY});
                }

            }
        }

        return visionPatches;
    }

    /**
     *
     * @return a list of coordinate within the vision and is unoccupied OR has imprisoned persons
     */
    public List<int[]> getEmptyPatch(Turtle[][] turtleMap){
        List<int[]> vision = getVision(turtleMap);
        List<int[]> empty_patch = new ArrayList<>();

        for(int[] coordinate: vision){
            Turtle a = turtleMap[coordinate[0]][coordinate[1]];
            if (a == null ||
                    (a instanceof Agent &&
                            ((Agent) a).getState().equals(AgentState.IMPRISONED))) {
                empty_patch.add(coordinate);
            }
        }
        return empty_patch;
    }

}
