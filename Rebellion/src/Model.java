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
    private int vision = 3;

    // Initial Government legitimacy
    private double legitimacy = Parameter.getLegitimacy();

    // List of all turtles
    private List<Turtle> turtles;
    private List<Agent> agents;
    private List<Police> cops;

    private Turtle[][] turtleMap;
    private Government govt;

    //value range for perceived hardship of agents
    private double minPerceivedHardship = 0.0;
    private double maxPerceivedHardship = 1.0;

    // width and height axis
    private int width = 50;
    private int height = 50;

    public int jailCount = 0;

    public boolean isRebel = false;

    public int rebel_count;



    public Model(int width, int height){
        turtles = new ArrayList<>();
        agents = new ArrayList<>();
        cops = new ArrayList<>();

        this.width = width;
        this.height = height;

        rebel_count = 0;

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
                    (maxPerceivedHardship - minPerceivedHardship) * r.nextDouble();
            double riskAversion = minRiskAversion +
                    (maxRiskAversion - minRiskAversion) * r.nextDouble();
            Agent a = new Agent(vision, coord[0], coord[1], riskAversion, hardship, revoltThreshold);
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
    }

    /**
     * Does all the checks for the model and movements for when a turn needs to be passed:
     *  Turtles Move
     *  Agents Rebel
     *  Police Arrest
     */
    public void passTurn(){
        // initialise next state
        Turtle[][] nextMapState = new Turtle[width][height];

        // do movements
        for (Turtle t : turtles) {
            t.move(nextMapState);
        }

        this.turtleMap = nextMapState;

        for (Agent agent: agents){
            if (agent.getState() == AgentState.PASSIVE) {
                agent.revoltOrNot();
            }else if(agent.getState() == AgentState.IMPRISONED){
                agent.spendTimeInJail();
            }
        }
        for (Police police: cops){
            police.arrest();
        }

        rebel_count();
        this.isRebel = false;
    }

    public Turtle[][] getTurtleMap() {
        return turtleMap;
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

    public void setMinJailTerm(int minJailTerm) {
        this.minJailTerm = minJailTerm;
    }

    public void setMaxRiskAversion(double maxRiskAversion) {
        this.maxRiskAversion = maxRiskAversion;
    }

    public void setMinRiskAversion(double minRiskAversion) {
        this.minRiskAversion = minRiskAversion;
    }

    public void setRevoltThreshold(double revoltThreshold) {
        this.revoltThreshold = revoltThreshold;
    }

    public void setVision(int vision) {
        this.vision = vision;
    }

    public void setTurtleMap(Turtle[][] turtleMap) {
        this.turtleMap = turtleMap;
    }

    public Government getGovt() {
        return govt;
    }

    public int getMaxJailTerm(){
        return this.maxJailTerm;
    }

    /**
     * randomly find a empty patch
     * @return a coordinate
     */
    public int[] getRandomEmptyPatch(){
        int width = this.turtleMap[0].length;
        int height = this.turtleMap.length;
        Random rand = new Random();

        List<int[]> empty_patch = new ArrayList<>();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(this.turtleMap[i][j] == null){
                    empty_patch.add(new int[] {i, j});
                }
            }
        }
        return empty_patch.get(rand.nextInt(empty_patch.size()));
    }

    /**
     * calculate how many agents is now in the map
     * @return how many agents is now in the map
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


    public int rebel_count(){
        if (isRebel) rebel_count++;
        else rebel_count = 0;
        System.out.println(rebel_count);
        return rebel_count;
    }
}
