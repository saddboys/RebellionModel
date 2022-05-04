import java.util.ArrayList;
import java.util.List;

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

    public Model(int width, int height){
        turtles = new ArrayList<>();
        agents = new ArrayList<>();
        cops = new ArrayList<>();

        turtleMap = new Turtle[width][height];
        govt = new Government();
    }

    /**
     * Initialises the model based on provided values
     */
    public void setup(){

    }

    /**
     * Does all the checks for the model and movements for when a turn needs to be passed:
     *  Turtles Move
     *  Agents Rebel
     *  Police Arrest
     */
    private void passTurn(){
        // do movements for all turtles
        for (Turtle t : turtles) {
            t.move();
        }

        // do revolts for all agents
        for (Agent a : agents){
            a.revoltOrNot();
        }

        // do arrests for all police
        for (Police p : cops){
            p.arrest();
        }
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
