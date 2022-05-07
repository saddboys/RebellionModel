/**
 * Represents a single agent on the model board
 */
public class Agent extends Turtle{
    // what the agent is currently doing
    private AgentState state = AgentState.PASSIVE;

    // How the agent views the world contributing to their revolt risk.
    private double perceivedHardship;

    // grievance = perceived-hardship * (1 - government-legitimacy)
    private double grievance;

    // The agent's tolerance for risk of revolt
    private double riskAversion;

    // The agent’s estimated arrest probability based on the police in vision range.
    private double arrestProbability;

    //the risk of revolt for the agent based on risk aversion, and the arrest probability of the agent.
    private double netRisk;

    private int jailTerm;

    private double revoltThreshold;

    public Agent(int vision, int x, int y,
                 double riskAversion,
                 double perceivedHardship,
                 double revoltThreshold) {
        super(vision, x, y);
        this.riskAversion = riskAversion;
        this.perceivedHardship = perceivedHardship;
        this.revoltThreshold = revoltThreshold;
    }

    /**
     * DAVID
     * Determines whether or not the agent should revolt at this turn. If so, then the agent will be changed to
     * revolting state. This is calculated based on net risk
     */
    public void revoltOrNot() {
        Turtle[][] map = Main.model.getTurtleMap();
        double numCops = 0;
        double numRebels = 0;

        // calculate min and max range of vision
        int minX = Math.max(location_x - vision, 0);
        int maxX = Math.min(location_x + vision, map.length);
        int minY = Math.max(location_y - vision, 0);
        int maxY = Math.min(location_y + vision, map[0].length);

        for (int i = minX; i < maxX; i++) {
            for (int j = minY; j < maxY; j++) {
                // calculate manhattan distance for vision
                if ((Math.abs(location_x - i) + Math.abs(location_y - j) <= vision)) {
                    Turtle currentTurtle = map[i][j];
                    // find number of police and rebels in vision range
                    if (currentTurtle instanceof Police) {
                        numCops++;
                    } else if (currentTurtle instanceof Agent &&
                            ((Agent) currentTurtle).state.equals(AgentState.REBELLING)) {
                        numRebels++;
                    }
                }
            }
        }

        arrestProbability = 1 - Math.exp(-Model.K_ARREST * (numCops / numRebels));
        netRisk = riskAversion * arrestProbability;
        grievance = perceivedHardship * (1 - Main.model.getGovt().getLegitimacy());
        // rebel if values are over threshold
        if (grievance - netRisk > revoltThreshold){
            this.state = AgentState.REBELLING;
        }
    }

    /**
     * Arrests the agent for a specified term
     * @param term the term that the agent is arrested for
     */
    public void arrest(int term){
        this.jailTerm = term;
        this.state = AgentState.IMPRISONED;
    }

    /**
     * The agent spends time in jail, and is released if their term is up
     */
    public void spendTimeInJail(){
        if (this.state == AgentState.IMPRISONED) {
            if (jailTerm == 0) {
                this.state = AgentState.PASSIVE;
            } else {
                this.jailTerm--;
            }
        }
    }
}
