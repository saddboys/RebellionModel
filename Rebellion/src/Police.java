import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a single police individual on the model board
 */
public class Police extends Turtle{

    public Police(int vision, int x, int y) {
        super(vision, x, y);
    }

    /**
     * Arrests a random rebelling agent in vision range
     */
    public void arrest(){
        Turtle[][] map = Main.model.getTurtleMap();
        List<int[]> visions = getVision(map);
        List<Agent> rebelling_agents = new ArrayList<>();
        for (int[] coordinate: visions){
            Turtle turtle = map[coordinate[0]][coordinate[1]];
            if(turtle instanceof Agent &&
                    ((Agent) turtle).getState() == AgentState.REBELLING){
                rebelling_agents.add(((Agent) turtle));
            }
        }


        if (rebelling_agents.size() > 0){
            Random rand = new Random();
            Agent agent = rebelling_agents.get(rand.nextInt(rebelling_agents.size()));
            this.setLocation(agent.getLocation_x(), agent.getLocation_y(), map);
            agent.arrest(rand.nextInt(Main.model.getMaxJailTerm()));
            Main.model.jailCount += 1;
        }
    }
}
