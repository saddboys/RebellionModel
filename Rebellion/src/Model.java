import java.util.*;

/**
 * Sets up the model and allows for execution of the model
 */
public class Model {

    public static final double K_ARREST = 2.3;

    // The density of Agents in comparison to empty space
    private double initialDensityAgent = Parameter.getInitialDensityAgent();
    // The density of Cops in relation to empty space
    private double initialDensityCops = Parameter.getInitialDensityCops();

    // The minimum and maximum risk aversion for revolt for agents
    private double minRiskAversion = 0.0;
    private double maxRiskAversion = 1.0;

    // The revolt threshold for agents
    private double revoltThreshold = 0.1;

    // The times someone can be arrested for
    private int minJailTerm = 5;
    private int maxJailTerm = Parameter.getMaxJailTerm();

    // Vision for each turtle (tiles)
    private int vision = Parameter.getVision();

    // Initial Government legitimacy
    private double legitimacy = Parameter.getLegitimacy();

    public List<Agent> newRebels = new ArrayList<>();

    // List of all turtles
    private List<Turtle> turtles;
    private List<Agent> agents;
    private List<Police> cops;

    // map of the world
    private Turtle[][] turtleMap;
    // government for the world
    private Government govt;

    //value range for perceived hardship of agents
    private double minPerceivedHardship = 0.0;
    private double maxPerceivedHardship = 1.0;

    // width and height axis
    private int width = 50;
    private int height = 50;

    public int jailCount = 0;

    /**
     * initialises a model map with default values with provided size
     * @param width
     * @param height
     */
    public Model(int width, int height){
        turtles = new ArrayList<>();
        agents = new ArrayList<>();
        cops = new ArrayList<>();

        this.width = width;
        this.height = height;

        turtleMap = new Turtle[width][height];
    }

    /**
     * DAVID
     * Initialises the model based on provided values
     */
    public void setup(){
        if (vision > width || vision > height){
            throw new IllegalArgumentException();
        }

        govt = new Government(legitimacy);

        if (initialDensityAgent + initialDensityCops > 1.0) {
            throw new IllegalArgumentException();
        }

        // Generate width height array for random selection(
        List<int[]> placements = new ArrayList<>();
        for (int x = 0; x < this.width; x++){
            for (int y = 0; y < this.height; y++){
                placements.add(new int[]{x, y});
            }
        }
        Collections.shuffle(placements);

        // calculate number of turtles
        int locations = width * height;
        int numAgent = (int) Math.floor(locations * initialDensityAgent);
        int numCops = (int) Math.floor(locations * initialDensityCops);

        Random r = new Random();

        // generate and add agents
        for (int i = 0; i < numAgent; i++){
            int[] coord = placements.remove(0);
            double hardship = minPerceivedHardship +
                    (maxPerceivedHardship - minPerceivedHardship)
                            * r.nextDouble();
            double riskAversion = minRiskAversion +
                    (maxRiskAversion - minRiskAversion) * r.nextDouble();
            Agent a = new Agent(vision, coord[0], coord[1],
                    riskAversion, hardship, revoltThreshold);
            turtles.add(a);
            agents.add(a);
            turtleMap[coord[0]][coord[1]] = a;
        }

        // generate and add police
        for (int i = 0; i < numCops; i++){
            int[] coord = placements.remove(0);
            Police p = new Police(vision, coord[0], coord[1]);
            turtles.add(p);
            cops.add(p);
            turtleMap[coord[0]][coord[1]] = p;
        }
        System.out.println("Agents: " + agents.size());
        System.out.println("Cops: " + cops.size());
    }

    /**
     * Does all the checks for the model and movements for when a turn needs
     * to be passed:
     *  Turtles Move
     *  Agents Rebel
     *  Police Arrest
     */
    public void passTurn(){
        // initialise next state
        Turtle[][] nextMapState = new Turtle[width][height];

        // do movements
        for (Turtle t : turtles) {
            if ((t instanceof Agent && !((Agent) t).getState()
                    .equals(AgentState.IMPRISONED)) || t instanceof Police) {
                t.move(nextMapState);
            }
        }
        this.turtleMap = nextMapState;


        // calculate next state revolts, and spend some time in jail
        for (Agent agent: agents){
            if (agent.getState() == AgentState.PASSIVE) {
                agent.revoltOrNot();
            }
        }


        // update to rebelling agents for next state and clear
        for (Agent agent : newRebels){
            agent.setState(AgentState.REBELLING);
        }
        newRebels.clear();

        // arrest rebels in current state
        for (Police police: cops){
            police.arrest();
        }

        // spend time in jail
        for (Agent agent: agents){
            if (agent.getState() == AgentState.IMPRISONED) {
                agent.spendTimeInJail();
            }
        }


    }

    /**
     * returns the turtle map
     * @return
     */
    public Turtle[][] getTurtleMap() {
        return turtleMap;
    }

    /**
     * adds new rebels to be updated in the next turn
     * @param a
     */
    public void addNewRebels(Agent a){
        this.newRebels.add(a);
    }

    /**
     * gets the government of world
     * @return
     */

    public Government getGovt() {
        return govt;
    }

    /**
     * gets the max jail term
     * @return
     */
    public int getMaxJailTerm(){
        return this.maxJailTerm;
    }

    /**
     * calculate how many agents is now in the map
     * @return how many passive, rebelling, and imprisoned agents in map
     */
    public int[] checkSum(){
        int[] res = new int[] {0, 0, 0};

        for (Agent a : this.agents){
            if(a.getState() == AgentState.PASSIVE){
                res[0] += 1;
            }
            else if(a.getState() == AgentState.REBELLING){
                res[1] += 1;
            }
            else if(a.getState() == AgentState.IMPRISONED){
                res[2] += 1;
            }
        }
        return res;
    }

    public void setLegitimacy(double legitimacy) {
        this.legitimacy = legitimacy;
    }

    public void setInitialDensityAgent(double initialDensityAgent) {
        this.initialDensityAgent = initialDensityAgent;
    }

    public void setInitialDensityCops(double initialDensityCops) {
        this.initialDensityCops = initialDensityCops;
    }

    public void setMaxJailTerm(int maxJailTerm) {
        this.maxJailTerm = maxJailTerm;
    }

    public void setVision(int vision) {
        this.vision = vision;
    }
}
