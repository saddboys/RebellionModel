import java.util.*;

/**
 * Sets up the model and allows for execution of the model
 */
public class Model {

    // The density of Agents in comparison to empty space
    private double initialDensityAgent = 0.50;
    // The density of Cops in relation to empty space
    private double initialDensityCops = 0.05;

    // The minimum and maximum risk aversion for revolt for agents
    private double minRiskAversion = 0.05;
    private double maxRiskAversion = 0.25;

    // The revolt threshold for agents
    private double revoltThreshold = 0.05;

    // The times someone can be arrested for
    private int minJailTerm = 5;
    private int maxJailTerm = 25;

    // Vision for each turtle (tiles)
    private int vision = 3;

    // Initial Government legitimacy
    private double legitimacy = 0.80;

    // List of all turtles
    private List<Turtle> turtles;
    private List<Agent> agents;
    private List<Police> cops;

    private Turtle[][] turtleMap;
    private Government govt;

    //value range for perceived hardship of agents
    private double minPerceivedHardship = 0;
    private double maxPerceivedHardship = 1;

    // width and height axis
    private int width = 50;
    private int height = 50;

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
            Agent a = new Agent(vision, coord[0], coord[1], hardship);
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
    private void passTurn(){
        // do movements for all turtles
        Turtle[][] nextState =  new Turtle[this.width][this.height];

        // TODO
        this.turtleMap = nextState;
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
}
